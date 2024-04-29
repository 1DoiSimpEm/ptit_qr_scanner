package com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.R
import kotlinx.collections.immutable.persistentListOf
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class OnboardPage(
  @StringRes val titleResId: Int = 0,
  @StringRes val descriptionResId: Int = 0,
  @DrawableRes val imageResId: Int = 0,
) : Parcelable {
  companion object {
    val DEFAULT = OnboardPage()
    val onboardPages = persistentListOf(
      OnboardPage(
        R.string.intro_title_1,
        R.string.intro_desc_1,
        R.drawable.intro_1,
      ),
      OnboardPage(
        R.string.intro_title_2,
        R.string.intro_desc_2,
        R.drawable.intro_2,
      ),
      OnboardPage(
        R.string.intro_title_3,
        R.string.intro_desc_3,
        R.drawable.intro_3,
      ),
    )
  }
}
