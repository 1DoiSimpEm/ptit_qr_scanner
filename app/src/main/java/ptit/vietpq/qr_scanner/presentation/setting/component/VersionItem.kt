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
import androidx.compose.ui.unit.dp
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale

@Composable
fun VersionItem(version: String, modifier: Modifier = Modifier) {
  Column(modifier = modifier) {
    Row(
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Image(painter = painterResource(id = R.drawable.ic_setting_version), contentDescription = null)
      Text(
        modifier = Modifier
          .padding(horizontal = 16.dp)
          .weight(1f),
        text = QrLocale.strings.version,
        style = QrCodeTheme.typo.subTitle,
        color = QrCodeTheme.color.neutral1,
      )
      Text(
        modifier = Modifier.padding(horizontal = 8.dp),
        text = version,
        style = QrCodeTheme.typo.body2,
        color = QrCodeTheme.color.primary,
      )
    }
  }
}
