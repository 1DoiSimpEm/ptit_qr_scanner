package ptit.vietpq.qr_scanner.domain.usecase

import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.repository.BarCodeResultRepository
import javax.inject.Inject

class DeleteAllHistoryUseCase @Inject constructor(private val barCodeResultRepository: BarCodeResultRepository) {
  suspend operator fun invoke(isCreated: Boolean) = barCodeResultRepository.deleteAll(isCreated)
}
