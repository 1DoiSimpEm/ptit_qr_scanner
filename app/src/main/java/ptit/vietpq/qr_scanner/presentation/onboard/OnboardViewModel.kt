package ptit.vietpq.qr_scanner.presentation.onboard

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ptit.vietpq.qr_scanner.data.datalocal.sharepref.SharePreferenceProvider
import javax.inject.Inject

@HiltViewModel
class OnboardViewModel @Inject constructor(private val sharePreferenceProvider: SharePreferenceProvider) : ViewModel() {

  var onboardIntroPassed: Boolean
    get() = sharePreferenceProvider.get<Boolean>(SharePreferenceProvider.ONBOARD_INTRO) ?: true
    set(value) {
      sharePreferenceProvider.save<Boolean>(SharePreferenceProvider.ONBOARD_INTRO, value)
    }
}
