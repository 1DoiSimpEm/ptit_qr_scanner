package ptit.vietpq.qr_scanner.presentation.language

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ptit.vietpq.qr_scanner.data.datalocal.sharepref.SharePreferenceProvider
import ptit.vietpq.qr_scanner.presentation.base.EventChannel
import ptit.vietpq.qr_scanner.presentation.base.HasEventFlow
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
  private val sharePreferenceProvider: SharePreferenceProvider,
) : ViewModel(){
  var languageCode: String
    get() = sharePreferenceProvider.get<String>(SharePreferenceProvider.CURRENT_LANGUAGE_CODE) ?: "en"
    set(value) {
      sharePreferenceProvider.save<String>(SharePreferenceProvider.CURRENT_LANGUAGE_CODE, value)
    }

  var onboardLanguagePassed: Boolean
    get() = sharePreferenceProvider.get<Boolean>(SharePreferenceProvider.ONBOARD_LANGUAGE) ?: true
    set(value) {
      sharePreferenceProvider.save<Boolean>(SharePreferenceProvider.ONBOARD_LANGUAGE, value)
    }

  private val _stateLanguage: MutableStateFlow<Boolean> = MutableStateFlow(false)
  val stateLanguage: StateFlow<Boolean> = _stateLanguage.asStateFlow()


}
