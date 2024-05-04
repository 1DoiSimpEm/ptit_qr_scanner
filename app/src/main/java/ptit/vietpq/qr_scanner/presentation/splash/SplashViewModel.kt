package ptit.vietpq.qr_scanner.presentation.splash

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ptit.vietpq.qr_scanner.data.datalocal.sharepref.SharePreferenceProvider
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
  private val sharePreferenceProvider: SharePreferenceProvider,
) : ViewModel() {

  val onboardLanguagePassed: Boolean
    get() = sharePreferenceProvider.get<Boolean>(SharePreferenceProvider.ONBOARD_LANGUAGE) ?: true

  val onboardIntroPassed: Boolean
    get() = sharePreferenceProvider.get<Boolean>(SharePreferenceProvider.ONBOARD_INTRO) ?: true

}
