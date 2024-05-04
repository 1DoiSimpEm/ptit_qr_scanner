package ptit.vietpq.qr_scanner.presentation.create

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import ptit.vietpq.qr_scanner.presentation.create_detail.screenTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ptit.vietpq.qr_scanner.presentation.base.EventChannel
import ptit.vietpq.qr_scanner.presentation.base.HasEventFlow
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class CreateViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val eventChannel: EventChannel<CreateEvent>,
) : ViewModel(),
  HasEventFlow<CreateEvent> by eventChannel {

  private val _uiState = MutableStateFlow(CreateUiState.initial(screenTypes))
  val uiState = _uiState.asStateFlow()

}
