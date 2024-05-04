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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale
import ptit.vietpq.qr_scanner.presentation.create_detail.components.CustomSwitchButton

@Preview
@Composable
private fun BeepItemPreview() {
  BeepItem(true, {})
}

@Composable
fun BeepItem(value: Boolean, onSwitched: (Boolean) -> Unit, modifier: Modifier = Modifier) {
  Column(modifier = modifier) {
    Row(
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Image(painter = painterResource(id = R.drawable.ic_setting_beep), contentDescription = null)
      Column(
        modifier = Modifier
          .padding(horizontal = 16.dp)
          .weight(1f),
      ) {
        Text(
          text = QrLocale.strings.beep,
          style = QrCodeTheme.typo.subTitle,
          color = QrCodeTheme.color.neutral1,
        )
        Text(
          text = QrLocale.strings.beepDescription,
          style = QrCodeTheme.typo.body2,
          color = QrCodeTheme.color.neutral3,
        )
      }
      CustomSwitchButton(
        modifier = Modifier
          .fillMaxWidth(),
        value = value,
        onSwitched = {
          onSwitched(it)
        },
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
