package ptit.vietpq.qr_scanner.presentation.setting.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale

@Preview
@Composable
private fun LanguageItemPreview() {
  LanguageItem(
    langText = "English",
  )
}

@Composable
fun LanguageItem(langText: String, modifier: Modifier = Modifier) {
  Column(modifier = modifier) {
    Row(
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Image(painter = painterResource(id = R.drawable.ic_setting_language), contentDescription = null)
      Text(
        modifier = Modifier
          .padding(horizontal = 16.dp)
          .weight(1f),
        text = QrLocale.strings.languages,
        style = QrCodeTheme.typo.subTitle,
        color = QrCodeTheme.color.neutral1,
      )
      Text(
        modifier = Modifier.padding(horizontal = 8.dp),
        text = langText,
        style = QrCodeTheme.typo.body2,
        color = QrCodeTheme.color.primary,
      )
      Image(painter = painterResource(id = R.drawable.ic_arrow), contentDescription = null)
    }
  }
}
