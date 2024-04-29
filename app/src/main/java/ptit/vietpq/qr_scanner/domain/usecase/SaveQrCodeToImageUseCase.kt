package ptit.vietpq.qr_scanner.domain.usecase

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import kotlinx.coroutines.withContext
import ptit.vietpq.qr_scanner.core.AppCoroutineDispatchers

class SaveQrCodeToImageUseCase @Inject constructor(
  @ApplicationContext private val context: Context,
  private val dispatcher: AppCoroutineDispatchers,
) {
  suspend operator fun invoke(imageBitmap: ImageBitmap): String? {
    val originalBitmap = imageBitmap.asAndroidBitmap()
    val bitmapWithWhiteBackground = Bitmap.createBitmap(
      originalBitmap.width,
      originalBitmap.height,
      originalBitmap.config,
    )
    val canvas = Canvas(bitmapWithWhiteBackground)
    canvas.drawColor(Color.WHITE)
    canvas.drawBitmap(originalBitmap, 0f, 0f, null)

    return withContext(dispatcher.io) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val resolver = context.contentResolver
        val contentValues = ContentValues().apply {
          put(MediaStore.MediaColumns.DISPLAY_NAME, "QR_${System.currentTimeMillis()}.png")
          put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
          put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + File.separator + "qr_codes")
        }
        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        uri?.let {
          resolver.openOutputStream(it)?.use { outputStream ->
            bitmapWithWhiteBackground.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
          }
          it.toString()
        }
      } else {
        val root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()
        val myDir = File("$root/qr_codes")
        myDir.mkdirs()
        val file = File(myDir, "QR_${System.currentTimeMillis()}.png")
        file.createNewFile()
        FileOutputStream(file).use { outputStream ->
          bitmapWithWhiteBackground.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        }
        file.absolutePath
      }
    }
  }
}
