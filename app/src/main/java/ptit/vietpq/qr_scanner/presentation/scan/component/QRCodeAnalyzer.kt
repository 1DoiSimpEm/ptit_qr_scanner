package ptit.vietpq.qr_scanner.presentation.scan.component

import android.graphics.Bitmap
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import ptit.vietpq.qr_scanner.utils.BitmapUtils

internal class QRCodeAnalyzer(
  private val onSuccess: (barCode: Barcode, imageRaw: Bitmap) -> Unit,
  private val onFailure: ((Exception) -> Unit),
  private val onPassCompleted: ((Boolean) -> Unit),
) : ImageAnalysis.Analyzer {

  // catch if for some reason MlKitContext has not been initialized
  private val barcodeScanner: BarcodeScanner? by lazy {
    val optionsBuilder = BarcodeScannerOptions
      .Builder()
      .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
    try {
      BarcodeScanning.getClient(optionsBuilder.build())
    } catch (e: Exception) {
      onFailure(e)
      null
    }
  }

  private val bitmapUtils: BitmapUtils by lazy {
    BitmapUtils()
  }

  // Remove cached state from CPU capture
  @Volatile
  private var failureOccurred = false
  private var failureTimestamp = 0L

  @OptIn(ExperimentalGetImage::class)
  override fun analyze(imageProxy: ImageProxy) {
    if (imageProxy.image == null) return

    // throttle analysis if error occurred in previous pass
    if (failureOccurred && System.currentTimeMillis() - failureTimestamp < 1000L) {
      imageProxy.close()
      return
    }

    failureOccurred = false

    val bitmap = bitmapUtils.getBitmap(imageProxy)
    if (bitmap == null) {
      imageProxy.close()
      return
    }

    val image = InputImage.fromBitmap(bitmap, 0)

    barcodeScanner?.let { scanner ->
      scanner.process(image)
        .addOnSuccessListener { codes ->
          codes.firstNotNullOfOrNull { it }?.let {
            onSuccess(
              it,
              bitmap,
            )
          }
        }
        .addOnFailureListener {
          failureOccurred = true
          failureTimestamp = System.currentTimeMillis()
          onFailure(it)
        }
        .addOnCompleteListener {
          onPassCompleted(failureOccurred)
          imageProxy.close()
        }
    }
  }

  @ExperimentalGetImage
  private fun ImageProxy.toInputImage() = InputImage.fromMediaImage(image!!, imageInfo.rotationDegrees)
}
