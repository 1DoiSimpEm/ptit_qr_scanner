package ptit.vietpq.qr_scanner.domain.usecase

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarcodeFormat
import kotlinx.coroutines.withContext
import ptit.vietpq.qr_scanner.core.AppCoroutineDispatchers
import ptit.vietpq.qr_scanner.data.mapper.mapMLKitFormatToZxing
import java.util.Hashtable
import javax.inject.Inject

class GenerateQrBitmapUseCase @Inject constructor(private val dispatcher: AppCoroutineDispatchers) {
  suspend operator fun invoke(text: String, barcodeFormat: BarcodeFormat): Result<Bitmap> = withContext(dispatcher.io) {
    val qrParam = Hashtable<EncodeHintType, Any>()
    qrParam[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
    qrParam[EncodeHintType.CHARACTER_SET] = "UTF-8"

    val result: BitMatrix = try {
      MultiFormatWriter().encode(text, barcodeFormat.mapMLKitFormatToZxing(), 400, 400, qrParam)
    } catch (e: Exception) {
      return@withContext Result.failure(e)
    }

    val w = result.width
    val h = result.height
    val pixels = IntArray(w * h)
    pixels.forEachIndexed { index, _ ->
      pixels[index] = if (result[index % w, index / w]) Color.BLACK else Color.WHITE
    }
    val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
    bitmap.setPixels(pixels, 0, 400, 0, 0, w, h)
    Result.success(bitmap)
  }
}
