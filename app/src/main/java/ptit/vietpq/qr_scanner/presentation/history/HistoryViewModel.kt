package ptit.vietpq.qr_scanner.presentation.history

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarCodeResult
import ptit.vietpq.qr_scanner.presentation.base.HasEventFlow
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ptit.vietpq.qr_scanner.domain.usecase.DeleteAllHistoryUseCase
import ptit.vietpq.qr_scanner.domain.usecase.DeleteHistoryUseCase
import ptit.vietpq.qr_scanner.domain.usecase.GetHistoryUseCase
import ptit.vietpq.qr_scanner.presentation.base.EventChannel
import ptit.vietpq.qr_scanner.utils.asResultFlow
import timber.log.Timber

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class HistoryViewModel @Inject constructor(
  getHistoryUseCase: GetHistoryUseCase,
  private val deleteAllHistoryUseCase: DeleteAllHistoryUseCase,
  private val deleteHistoryUseCase: DeleteHistoryUseCase,
  private val moshi: Moshi,
  private val eventChannel: EventChannel<HistoryEvent>,
  @ApplicationContext private val context: Context,
) : ViewModel(),
  HasEventFlow<HistoryEvent> by eventChannel {

  private val _uiState = MutableStateFlow(HistoryUiState())
  val uiState = _uiState.asStateFlow()



  val uiFlow = getHistoryUseCase()
    .map { result ->
      result.onSuccess { list ->
        val scannedList = list.filter { it.isGenerated.not() }.toImmutableList()
        val createdList = list.filter { it.isGenerated }.toImmutableList()
        _uiState.update {
          it.copy(scannedHistoryList = scannedList, createdHistoryList = createdList)
        }
      }.onFailure { _ ->
      }
    }
    .asResultFlow()
    .onEach {
      Timber.tag("HistoryViewModel").d("getHistory: %s", it)
    }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(),
      initialValue = HistoryUiState(),
    )

  fun getNavigateValue(barcodeResult: BarCodeResult): String =
    moshi.adapter(BarCodeResult::class.java).toJson(barcodeResult)

  fun deleteAllHistory(isCreated: Boolean) {
    viewModelScope.launch {
      deleteAllHistoryUseCase(isCreated)
    }
  }

  fun deleteHistory(barcodeResult: BarCodeResult) {
    viewModelScope.launch {
      deleteHistoryUseCase(barcodeResult)
    }
  }
}
