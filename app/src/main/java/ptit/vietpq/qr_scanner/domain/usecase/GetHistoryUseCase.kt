package ptit.vietpq.qr_scanner.domain.usecase

import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.repository.BarCodeResultRepository
import javax.inject.Inject

class GetHistoryUseCase @Inject constructor(private val barCodeResultRepository: BarCodeResultRepository) {
  operator fun invoke() = barCodeResultRepository.getHistory()
}
