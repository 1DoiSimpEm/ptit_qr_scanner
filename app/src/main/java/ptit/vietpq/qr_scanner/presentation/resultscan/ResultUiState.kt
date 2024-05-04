package ptit.vietpq.qr_scanner.presentation.resultscan

import android.graphics.Bitmap
import androidx.compose.runtime.Immutable
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarCodeResult
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarcodeSchema
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BaseBarcodeModel

@Immutable
data class ResultUiState(
  val result: BarCodeResult,
  val baseCodeResult: BaseBarcodeModel? = null,
  val barCodeTypeSchema: BarcodeSchema,
  val textRaw: String? = "",
  val isLoading: Boolean,
  val error: Throwable? = null,
  val imageCaptureQr: Bitmap? = null,
) {
  companion object {
    fun initialResult(value: BarCodeResult) = ResultUiState(
      result = value,
      isLoading = false,
      error = null,
      barCodeTypeSchema = value.typeSchema ?: BarcodeSchema.TYPE_YOUTUBE,
    )
  }
}
