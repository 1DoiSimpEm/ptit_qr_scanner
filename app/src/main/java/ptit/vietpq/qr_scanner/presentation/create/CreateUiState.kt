package ptit.vietpq.qr_scanner.presentation.create

import androidx.compose.runtime.Immutable
import ptit.vietpq.qr_scanner.presentation.create_detail.CreateQrType
import kotlinx.collections.immutable.PersistentList

@Immutable
data class CreateUiState(
    val data: PersistentList<CreateQrType>,
    val isLoading: Boolean,
    val adsInterLoading: Boolean,
) {
  companion object {
    fun initial(data: PersistentList<CreateQrType>) = CreateUiState(
      data = data,
      isLoading = false,
      adsInterLoading = false,
    )
  }
}
