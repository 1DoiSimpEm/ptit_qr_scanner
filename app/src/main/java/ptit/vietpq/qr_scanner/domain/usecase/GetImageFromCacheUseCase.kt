package ptit.vietpq.qr_scanner.domain.usecase

import android.graphics.Bitmap
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.repository.FileCacheBitmapRepository
import javax.inject.Inject

class GetImageFromCacheUseCase @Inject constructor(private val fileProvider: FileCacheBitmapRepository) {
  suspend operator fun invoke(fileName: String): Result<Bitmap> = fileProvider.fetchCachedBitmap(fileName = fileName)
}
