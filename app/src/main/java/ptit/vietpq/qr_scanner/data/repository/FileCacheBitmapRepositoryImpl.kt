package ptit.vietpq.qr_scanner.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.repository.FileCacheBitmapRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import ptit.vietpq.qr_scanner.utils.AppConstant
import java.io.File
import java.io.FileInputStream
import javax.inject.Inject

class FileCacheBitmapRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context) :
  FileCacheBitmapRepository {

  override suspend fun fetchCachedBitmap(fileName: String): Result<Bitmap> {
    val cacheDir = context.externalCacheDir ?: throw Exception("external cache not found")
    return getBitmapForFile(File(cacheDir, fileName))
  }

  private fun getBitmapForFile(file: File): Result<Bitmap> = runCatching {
    val exif = ExifInterface(file.absolutePath)
    val inputStream = FileInputStream(file)
    val fileBitmap = BitmapFactory.decodeStream(inputStream)

    return@runCatching when (exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
      ExifInterface.ORIENTATION_ROTATE_90 -> fileBitmap.rotate(90)
      ExifInterface.ORIENTATION_ROTATE_180 -> fileBitmap.rotate(180)
      ExifInterface.ORIENTATION_ROTATE_270 -> fileBitmap.rotate(270)
      else -> fileBitmap
    }
  }.onFailure { it.printStackTrace() }

  override suspend fun cacheBitmap(bitmap: Bitmap): Result<String> {
    val cacheDir = context.externalCacheDir ?: throw Exception("external cache not found")
    val fileName = AppConstant.IMAGE_NAME_CACHE

    val imageFile = File(cacheDir, fileName)

    runCatching {
      val os = imageFile.outputStream()
      bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
      os.close()
    }.onFailure {
      Result.failure<Throwable>(it)
    }

    return Result.success(fileName)
  }
}

fun Bitmap.rotate(degree: Int): Bitmap {
  val matrix = Matrix()
  matrix.postRotate(degree.toFloat())
  return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}
