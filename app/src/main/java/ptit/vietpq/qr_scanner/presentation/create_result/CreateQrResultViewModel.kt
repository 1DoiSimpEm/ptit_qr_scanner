package ptit.vietpq.qr_scanner.presentation.create_result

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarCodeResult
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.customize_qr.ShapeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ptit.vietpq.qr_scanner.domain.usecase.InsertHistoryUseCase
import ptit.vietpq.qr_scanner.domain.usecase.SaveQrCodeToImageUseCase
import ptit.vietpq.qr_scanner.presentation.base.EventChannel
import ptit.vietpq.qr_scanner.presentation.base.HasEventFlow
import ptit.vietpq.qr_scanner.presentation.create.CreateEvent
import javax.inject.Inject

@HiltViewModel
class CreateQrResultViewModel @Inject constructor(
  private val saveQrCodeToImageUseCase: SaveQrCodeToImageUseCase,
  private val insertHistoryUseCase: InsertHistoryUseCase,
  private val eventChannel: EventChannel<CreateEvent>,
) : ViewModel(),
  HasEventFlow<CreateEvent> by eventChannel {

  private val _uiState = MutableStateFlow(CreateQrResultUiState.initial)
  val uiState = _uiState.asStateFlow()

  fun onTypeChanged(result: BarCodeResult) {
    _uiState.update {
      it.copy(result = result)
    }
  }

  fun insertHistory(result: BarCodeResult) {
    viewModelScope.launch {
      insertHistoryUseCase(result)
    }
  }

  fun saveQrCode(bitmap: ImageBitmap) {
    viewModelScope.launch {
      saveQrCodeToImageUseCase(bitmap)
    }
  }

  fun saveCustomQrCode(shape: ShapeModel, foregroundColor: Color, backgroundColor: Color, logo: Int? = null) {
    _uiState.update {
      it.copy(
        qrShape = shape,
        qrForegroundColor = foregroundColor,
        qrBackgroundColor = backgroundColor,
        logo = logo,
      )
    }
  }
}
