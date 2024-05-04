package ptit.vietpq.qr_scanner.presentation.create_detail

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class CreateQrDetailUiState(
  val chips: ImmutableList<CreateQrType>,
  var selectedChip: CreateQrType,
  val isLoading: Boolean,
  val error: Throwable? = null,
) {
  companion object {
    fun initialType(selectedChip: CreateQrType, chips: ImmutableList<CreateQrType>) = CreateQrDetailUiState(
      chips = chips,
      selectedChip = selectedChip,
      isLoading = false,
    )
  }
}
