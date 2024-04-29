package ptit.vietpq.qr_scanner.domain.usecase

import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarCodeResult
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.repository.BarCodeResultRepository
import javax.inject.Inject

class DeleteHistoryUseCase @Inject constructor(private val barCodeResultRepository: BarCodeResultRepository) {
  suspend operator fun invoke(barCodeResult: BarCodeResult) = barCodeResultRepository.deleteHistory(barCodeResult)
}
