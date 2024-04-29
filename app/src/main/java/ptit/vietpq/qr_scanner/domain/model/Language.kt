package com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.R
import kotlinx.collections.immutable.persistentListOf
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class Language(val code: String, val nativeName: String, val flagRes: Int) : Parcelable {
  companion object {

    val languageList = persistentListOf(
      Language("en", "English", R.drawable.ic_uk),
      Language("es", "Español", R.drawable.ic_spanish),
      Language("de", "Deutsch", R.drawable.ic_german),
      Language("it", "Italiano", R.drawable.ic_italian),
      Language("tr", "Türkçe", R.drawable.ic_turkey),
      Language("ru", "Русский", R.drawable.ic_russia),
      Language("vi", "Tiếng Việt", R.drawable.ic_vietnam),
      Language("ja", "日本語", R.drawable.ic_japan),
      Language("ko", "한국어", R.drawable.ic_korea),
    )

    fun getNameFromLangCode(code: String): String = languageList.firstOrNull {
      it.code == code
    }?.nativeName ?: "English"
  }
}
