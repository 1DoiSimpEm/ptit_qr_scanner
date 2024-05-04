package ptit.vietpq.qr_scanner.presentation.history_selection

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarCodeResult
import ptit.vietpq.qr_scanner.presentation.history.HistoryUiState
import ptit.vietpq.qr_scanner.presentation.history_selection.navigation.HistorySelectionNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ptit.vietpq.qr_scanner.domain.usecase.DeleteHistoryUseCase
import ptit.vietpq.qr_scanner.domain.usecase.GetHistoryUseCase
import ptit.vietpq.qr_scanner.utils.asResultFlow
import timber.log.Timber

@HiltViewModel
class HistorySelectionViewModel @Inject constructor(
  getHistoryUseCase: GetHistoryUseCase,
  private val deleteHistoryUseCase: DeleteHistoryUseCase,
  savedStateHandle: SavedStateHandle,
) : ViewModel() {

  private val _uiState = MutableStateFlow(
    HistorySelectionUiState.default(
      HistorySelectionNavigation.savedStateRoute(savedStateHandle),
    ),
  )
  val uiState = _uiState.asStateFlow()

  val uiFlow = getHistoryUseCase()
    .map { result ->
      result.onSuccess { list ->
        _uiState.update { state ->
          state.copy(
            listHistory = list
              .filter {
                it.isGenerated == _uiState.value.isCreated
              }
              .toImmutableList(),
          )
        }
      }.onFailure { error ->
        _uiState.update { it.copy(error = error) }
      }
    }
    .asResultFlow()
    .onEach {
      Timber.tag("HistoryViewModel").d("getHistory: %s", it)
    }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(),
      initialValue = HistoryUiState(),
    )

  fun deleteHistories() {
    viewModelScope.launch {
      val list = _uiState.value.listHistory.filter { it.isSelected }
      list.forEach {
        deleteHistoryUseCase(it)
      }
    }
  }

  fun selectedBarCode(barCodeResult: BarCodeResult) {
    _uiState.update { item ->
      item.copy(
        listHistory = item.listHistory.map {
          if (it == barCodeResult) {
            it.copy(isSelected = !it.isSelected)
          } else {
            it
          }
        }.toImmutableList(),
      )
    }
  }

  fun selectAllBarCode() {
    _uiState.update { item ->
      item.copy(
        listHistory = item.listHistory.map {
          it.copy(isSelected = true)
        }.toImmutableList(),
      )
    }
  }

  fun deselectAllBarCode() {
    _uiState.update { item ->
      item.copy(
        listHistory = item.listHistory.map {
          it.copy(isSelected = false)
        }.toImmutableList(),
      )
    }
  }
}
