package ptit.vietpq.qr_scanner.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.repository.ProcessImageBarCodeRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ProcessImageBarCodeRepositoryImpl @Inject constructor(
  @ApplicationContext private val context: Context,
  private val barCodeScannerClient: BarcodeScanner,
) : ProcessImageBarCodeRepository {

  override fun observerImageBarCodeFromBitmap(bitmap: Bitmap): Flow<Barcode> = callbackFlow {
    val imageInput = InputImage.fromBitmap(bitmap, 0)
    barCodeScannerClient.process(imageInput)
      .addOnSuccessListener { codes ->
        codes.firstNotNullOfOrNull { it }?.let {
          trySend(it)
        }
      }
      .addOnFailureListener {
        it.printStackTrace()
        close(it)
      }.addOnCompleteListener { close() }

    awaitClose { close() }
  }

  override fun observerImageBarCodeFromUri(uri: Uri): Flow<Barcode?> {
    val imageInput = InputImage.fromFilePath(context, uri)
    return callbackFlow {
      barCodeScannerClient.process(imageInput)
        .addOnSuccessListener { codes ->
          trySend(codes.firstNotNullOfOrNull { it })
        }
        .addOnFailureListener {
          it.printStackTrace()
          throw it
        }.addOnCompleteListener { close() }

      awaitClose { close() }
    }
  }
}
