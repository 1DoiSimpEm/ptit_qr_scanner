package ptit.vietpq.qr_scanner.presentation.create_result

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarCodeResult
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.customize_qr.ShapeModel

@Immutable
data class CreateQrResultUiState(
  val result: BarCodeResult,
  val qrShape: ShapeModel,
  val qrForegroundColor: Color,
  val qrBackgroundColor: Color,
  val logo: Int? = null,
  val isLoading: Boolean = false,
  val isLoadingAds: Boolean = false,
) {
  companion object {
    val initial = CreateQrResultUiState(
      result = BarCodeResult.INITIAL,
      qrShape = ShapeModel.default,
      qrForegroundColor = Color.Black,
      qrBackgroundColor = Color.White,
    )
  }
}
