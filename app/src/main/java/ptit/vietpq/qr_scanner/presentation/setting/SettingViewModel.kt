package ptit.vietpq.qr_scanner.presentation.setting

import androidx.lifecycle.ViewModel
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.Language
import dagger.hilt.android.lifecycle.HiltViewModel
import ptit.vietpq.qr_scanner.data.datalocal.sharepref.SharePreferenceProvider
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val sharePreferenceProvider: SharePreferenceProvider) : ViewModel() {

  var isBeepEnable = sharePreferenceProvider.get<Boolean>(SharePreferenceProvider.IS_BEEP_ENABLE) ?: false
    set(value) {
      field = value
      sharePreferenceProvider.save<Boolean>(SharePreferenceProvider.IS_BEEP_ENABLE, value)
    }

  var isVibrateEnable = sharePreferenceProvider.get<Boolean>(SharePreferenceProvider.IS_VIBRATE_ENABLE) ?: false
    set(value) {
      field = value
      sharePreferenceProvider.save<Boolean>(SharePreferenceProvider.IS_VIBRATE_ENABLE, value)
    }

  val currentLanguage = Language.getNameFromLangCode(
    sharePreferenceProvider.get<String>(SharePreferenceProvider.CURRENT_LANGUAGE_CODE) ?: "en",
  )
}
