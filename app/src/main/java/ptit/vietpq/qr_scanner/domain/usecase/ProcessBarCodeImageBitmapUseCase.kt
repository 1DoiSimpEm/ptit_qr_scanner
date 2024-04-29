package ptit.vietpq.qr_scanner.domain.usecase

import android.graphics.Bitmap
import com.google.mlkit.vision.barcode.common.Barcode
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.repository.ProcessImageBarCodeRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ptit.vietpq.qr_scanner.core.AppCoroutineDispatchers

class ProcessBarCodeImageBitmapUseCase @Inject constructor(
  private val appCoroutineDispatchers: AppCoroutineDispatchers,
  private val processImageBarCodeRepository: ProcessImageBarCodeRepository,
) {
  operator fun invoke(bitmap: Bitmap): Flow<Barcode> = flow { emit(value = bitmap) }
    .flatMapLatest { processImageBarCodeRepository.observerImageBarCodeFromBitmap(it) }
    .map { barCode -> barCode }
    .flowOn(appCoroutineDispatchers.io)
}
