package ptit.vietpq.qr_scanner.presentation.history

import androidx.compose.runtime.Immutable
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarCodeResult
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class HistoryUiState(
  val scannedHistoryList: ImmutableList<BarCodeResult> = persistentListOf(),
  val createdHistoryList: ImmutableList<BarCodeResult> = persistentListOf(),
  val isLoading: Boolean = false,
  val isLoadingAds: Boolean = false,
)
