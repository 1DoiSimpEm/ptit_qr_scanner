package ptit.vietpq.qr_scanner.presentation.history_selection

import androidx.compose.runtime.Immutable
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarCodeResult
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.immutableListOf

@Immutable
data class HistorySelectionUiState(
  val listHistory: ImmutableList<BarCodeResult>,
  val isCreated: Boolean,
  val isLoading: Boolean = false,
  val error: Throwable? = null,
) {
  companion object {
    fun default(isCreated: Boolean): HistorySelectionUiState = HistorySelectionUiState(
      isLoading = true,
      isCreated = isCreated,
      listHistory = immutableListOf(),
    )
  }
}
