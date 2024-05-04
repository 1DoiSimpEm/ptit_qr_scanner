package ptit.vietpq.qr_scanner.presentation.create_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ptit.vietpq.qr_scanner.core.AppCoroutineDispatchers
import ptit.vietpq.qr_scanner.presentation.create_detail.navigation.CreateQrDetailNavigation
import javax.inject.Inject

@HiltViewModel
class CreateQrDetailViewModel @Inject constructor(
  private val appCoroutineDispatchers: AppCoroutineDispatchers,
  private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
  private fun initTypeUiState(): CreateQrDetailUiState {
    val title = CreateQrDetailNavigation.savedStateRoute(savedStateHandle)
    val type = getQrType(title)
    val chips = getItemsByCategory(type.categoryType)
    return CreateQrDetailUiState.initialType(type, chips)
  }

  private val _uiState = MutableStateFlow(initTypeUiState())
  val uiState = _uiState.asStateFlow()

  fun updateSelectedChip(selectedChip: CreateQrType) {
    _uiState.update {
      it.copy(selectedChip = selectedChip)
    }
  }

  companion object {
    private const val TAG = "CreateQrDetailViewModel"
  }
}
