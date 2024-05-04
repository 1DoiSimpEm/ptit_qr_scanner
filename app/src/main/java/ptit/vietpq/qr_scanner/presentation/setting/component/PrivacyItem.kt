package ptit.vietpq.qr_scanner.presentation.setting.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
fun PrivacyItem(modifier: Modifier = Modifier) {
  Column(modifier = modifier) {
    Row(
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Image(painter = painterResource(id = R.drawable.ic_setting_feedback), contentDescription = null)
      Text(
        modifier = Modifier
          .padding(horizontal = 16.dp)
          .weight(1f),
        text = QrLocale.strings.privacyPolicy,
        style = QrCodeTheme.typo.subTitle,
        color = QrCodeTheme.color.neutral1,
      )
    }
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .padding(
          top = QrCodeTheme.dimen.marginSmall,
          start = 42.dp,
        )
        .height(1.dp)
        .background(QrCodeTheme.color.backgroundCard),
    )
  }
}
