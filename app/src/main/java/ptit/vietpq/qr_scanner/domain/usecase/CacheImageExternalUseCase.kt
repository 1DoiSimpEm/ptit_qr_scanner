package ptit.vietpq.qr_scanner.domain.usecase

import android.graphics.Bitmap
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.repository.FileCacheBitmapRepository
import javax.inject.Inject

class CacheImageExternalUseCase @Inject constructor(private val fileProvider: FileCacheBitmapRepository) {
  suspend operator fun invoke(bitmap: Bitmap): Result<String> = fileProvider.cacheBitmap(bitmap = bitmap)
}
