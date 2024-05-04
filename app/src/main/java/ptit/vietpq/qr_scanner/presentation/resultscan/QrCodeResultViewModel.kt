package ptit.vietpq.qr_scanner.presentation.resultscan

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarCodeResult
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BaseBarcodeModel
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ptit.vietpq.qr_scanner.core.AppCoroutineDispatchers
import ptit.vietpq.qr_scanner.data.mapper.toBarCodeModel
import ptit.vietpq.qr_scanner.domain.usecase.GenerateQrBitmapUseCase
import ptit.vietpq.qr_scanner.domain.usecase.GetImageFromCacheUseCase
import ptit.vietpq.qr_scanner.presentation.resultscan.navigation.QrCodeResultDestination.savedStateRoute
import ptit.vietpq.qr_scanner.utils.AppConstant
import ptit.vietpq.qr_scanner.utils.WifiConnector
import javax.inject.Inject

@HiltViewModel
class QrCodeResultViewModel @Inject constructor(
  private val appCoroutineDispatchers: AppCoroutineDispatchers,
  private val savedStateHandle: SavedStateHandle,
  private val moshi: Moshi,
  private val getImageFromCacheUseCase: GetImageFromCacheUseCase,
  private val generateQrBitmapUseCase: GenerateQrBitmapUseCase,
  private val wifiConnector: WifiConnector,
) : ViewModel() {

  private val barCodeResult: BarCodeResult =
    moshi.adapter(BarCodeResult::class.java).fromJson(savedStateRoute(savedStateHandle)) ?: BarCodeResult.INITIAL

  private val _uiStateResult = MutableStateFlow(ResultUiState.initialResult(barCodeResult))
  val uiStateResult = _uiStateResult.asStateFlow()

  init {
    val barCodeModel: BaseBarcodeModel? = barCodeResult.toBarCodeModel()

    _uiStateResult.update {
      it.copy(
        baseCodeResult = barCodeModel,
        barCodeTypeSchema = barCodeResult.typeSchema,
        textRaw = barCodeResult.text,
      )
    }

    getInfoBarCode()
  }

  private fun getInfoBarCode() {
    viewModelScope.launch {
      val bitmapIfNeed = withContext(appCoroutineDispatchers.io) {
        if (barCodeResult.cropImage) {
          getImageFromCacheUseCase(AppConstant.IMAGE_NAME_CACHE)
        } else {
          generateQrBitmapUseCase(barCodeResult.text, barCodeResult.format)
        }
      }
      _uiStateResult.update {
        it.copy(imageCaptureQr = bitmapIfNeed.getOrNull())
      }
    }
  }

  fun wifiConnect(
    context: Context,
    authType: String,
    name: String,
    password: String,
    isHidden: Boolean,
    anonymousIdentity: String,
    identity: String,
    eapMethod: String,
    phase2Method: String,
  ) {
    viewModelScope.launch {
      wifiConnector.connect(
        context = context,
        authType = authType,
        name = name,
        password = password,
        isHidden = isHidden,
        anonymousIdentity = anonymousIdentity,
        identity = identity,
        eapMethod = eapMethod,
        phase2Method = phase2Method,
      )
    }
  }

  companion object {
    private const val TAG = "QrCodeResultViewModel"
  }
}
