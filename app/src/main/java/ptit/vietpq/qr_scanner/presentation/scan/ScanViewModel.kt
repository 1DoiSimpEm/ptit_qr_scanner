package ptit.vietpq.qr_scanner.presentation.scan

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.barcode.common.Barcode
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarCodeResult
import ptit.vietpq.qr_scanner.presentation.base.EventChannel
import ptit.vietpq.qr_scanner.presentation.base.HasEventFlow
import ptit.vietpq.qr_scanner.presentation.scan.component.BeepManager
import ptit.vietpq.qr_scanner.presentation.scan.component.TabMode
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ptit.vietpq.qr_scanner.core.AppCoroutineDispatchers
import ptit.vietpq.qr_scanner.data.datalocal.sharepref.SharePreferenceProvider
import ptit.vietpq.qr_scanner.data.mapper.toBarCodeResult
import ptit.vietpq.qr_scanner.domain.usecase.CacheImageExternalUseCase
import ptit.vietpq.qr_scanner.domain.usecase.InsertHistoryUseCase
import ptit.vietpq.qr_scanner.domain.usecase.ProcessBarCodeImageUriUseCase
import ptit.vietpq.qr_scanner.utils.BitmapUtils
import timber.log.Timber

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class ScanViewModel @Inject constructor(
  private val processBarCodeImageBitmapUseCase: ProcessBarCodeImageUriUseCase,
  private val insertHistoryUseCase: InsertHistoryUseCase,
  private val beepManager: BeepManager,
  private val eventChannel: EventChannel<ScanQrEvent>,
  private val sharePreferenceProvider: SharePreferenceProvider,
  private val cacheImageExternalUseCase: CacheImageExternalUseCase,
  private val appCoroutineDispatchers: AppCoroutineDispatchers,
  private val moshi: Moshi,
  private val bitmapUtils: BitmapUtils,
  @ApplicationContext private val context: Context,
) : ViewModel(eventChannel),
  HasEventFlow<ScanQrEvent> by eventChannel {

  private val _scanUiState = MutableStateFlow(ScanUiState.initial)
  val scanUiState = _scanUiState.asStateFlow()

  private val showGuideLine = sharePreferenceProvider.get<Boolean>(SharePreferenceProvider.SHOW_GUIDE_LINE_KEY) ?: false


  fun showBottomBar(show: Boolean) {
    viewModelScope.launch {
      eventChannel.send(ScanQrEvent.ShowBottomBarEvent(show))
    }
  }

  // Scan SingleMode
  fun resultScanModeSingle(barCode: Barcode, imageRaw: Bitmap) {
    viewModelScope.launch {
      // Update Loading state, init State
      _scanUiState.update {
        it.copy(isLoading = true, retry = false, showGuideLine = true)
      }

      // Crop Image, Check it null , retry crop
      val rectCrop = barCode.boundingBox

      if (rectCrop == null) {
        _scanUiState.update {
          it.copy(isLoading = false, retry = true)
        }
        return@launch
      }

      val bitmap = bitmapUtils.createBitmapForRect(imageRaw, rectCrop).getOrNull()

      if (bitmap == null) {
        _scanUiState.update {
          it.copy(isLoading = false, retry = true)
        }
        return@launch
      }

      // Save First GuideLine
      if (!showGuideLine) {
        saveShowGuideLine(true)
      }

      val resultCrop = withContext(appCoroutineDispatchers.io) {
        cacheImageExternalUseCase(bitmap)
      }

      resultCrop.fold(
        onSuccess = {
          insertHistoryUseCase(barCode.toBarCodeResult(false))
          playBeepAndVibrate()
          parseAndSendSuccessBarCode(barCode = barCode, isCrop = true)
        },
        onFailure = {
          _scanUiState.update {
            it.copy(isLoading = false, isFlashOn = false)
          }
        },
      )
    }
  }

  // Scan MultiMode
  fun resultScanMultiMode(barCode: Barcode) {
    viewModelScope.launch {
      val parseModelResult: BarCodeResult = barCode.toBarCodeResult(cropMode = false)
      _scanUiState.update { state ->
        if (!state.barCodePresent.any { it.identifyBarCodeResult == parseModelResult.identifyBarCodeResult }) {
          playBeepAndVibrate()
          insertHistoryUseCase(parseModelResult)
          val newList = persistentListOf(parseModelResult) + state.barCodePresent
          state.copy(barCodePresent = newList.toPersistentList())
        } else {
          state
        }
      }
    }
  }

  // Select Tab Mode
  fun selectTabMode(tabMode: TabMode) {
    _scanUiState.update {
      it.copy(
        tabSelected = tabMode,
      )
    }
  }

  // Dismiss Guide Line
  fun selectedBarCode(barCodeResult: BarCodeResult) {
    _scanUiState.update {
      it.copy(
        barCodePresent = it.barCodePresent.map { item ->
          if (item.text == barCodeResult.text) {
            item.copy(isSelected = !item.isSelected)
          } else {
            item
          }
        }.toPersistentList(),
      )
    }
  }

  // Remove Selected BarCode
  fun removeSelectedBarCode() {
    viewModelScope.launch {
      _scanUiState.update {
        it.copy(
          barCodePresent = it.barCodePresent
            .filter { item -> !item.isSelected }
            .toPersistentList(),
        )
      }
      eventChannel.send(ScanQrEvent.DeleteSuccess(_scanUiState.value.barCodePresent.size))
    }
  }

  // Select All BarCode
  fun selectAllBarCode() {
    _scanUiState.update {
      it.copy(
        isSelectAllMode = !it.isSelectAllMode,
        barCodePresent = it.barCodePresent.map { item ->
          item.copy(isSelected = !it.isSelectAllMode)
        }.toPersistentList(),
      )
    }
  }

  // UnSelected All BarCode
  fun unSelectedAllBarCode() {
    _scanUiState.update {
      it.copy(
        isSelectAllMode = false,
        barCodePresent = it.barCodePresent.map { item ->
          item.copy(isSelected = false)
        }.toPersistentList(),
      )
    }
  }

  // Pick Image
  fun pickImage(uri: Uri) {
    processBarCodeImageBitmapUseCase(uri = uri)
      .onEach { barCode ->
        if (barCode == null) {
          eventChannel.send(ScanQrEvent.OnQrScannedError)
        } else {
          insertHistoryUseCase(barCode.toBarCodeResult(false))
          parseAndSendSuccessBarCode(barCode = barCode, isCrop = false)
        }
      }
      .catch { eventChannel.send(ScanQrEvent.OnQrScannedError) }
      .launchIn(viewModelScope)
  }

  // Parse and Send Success BarCode
  private fun parseAndSendSuccessBarCode(barCode: Barcode, isCrop: Boolean = false) {
    _scanUiState.update {
      it.copy(isLoading = false, isFlashOn = false)
    }
    val parseModelResult: BarCodeResult = barCode.toBarCodeResult(cropMode = isCrop)
    showResultEvent(parseModelResult)
  }

  // Show ResultCode Event
  fun showResultEvent(barCodeResult: BarCodeResult) {
    viewModelScope.launch {
      val jsonStringResultCode = moshi.adapter(BarCodeResult::class.java).toJson(barCodeResult)
      eventChannel.send(ScanQrEvent.OnQrScanned(jsonStringResultCode))
    }
  }

  // Save Show Guide Line
  fun saveShowGuideLine(isShow: Boolean) {
    sharePreferenceProvider.save<Boolean>(SharePreferenceProvider.SHOW_GUIDE_LINE_KEY, isShow)
    _scanUiState.update {
      it.copy(showGuideLine = isShow)
    }
  }

  // Toggle Flash Light
  fun toggleFlashLight() {
    _scanUiState.update {
      it.copy(isFlashOn = !it.isFlashOn)
    }
  }

  private fun playBeepAndVibrate() {
    val beepEnable = sharePreferenceProvider.get<Boolean>(SharePreferenceProvider.IS_BEEP_ENABLE) ?: false
    val vibrateEnable = sharePreferenceProvider.get<Boolean>(SharePreferenceProvider.IS_VIBRATE_ENABLE) ?: false
    beepManager.setPlayBeep(beepEnable)
    beepManager.setVibrate(vibrateEnable)
    beepManager.playBeepSoundAndVibrate()
  }


  override fun onCleared() {
    super.onCleared()
    Timber.tag("ScanViewModel").d("onCleared")
  }
}
