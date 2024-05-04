package ptit.vietpq.qr_scanner.presentation.scan.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.common.TwButton
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale

@Composable
fun PermissionDeniedForwardSetting(modifier: Modifier = Modifier, onClickForwardSettings: () -> Unit) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 36.dp, vertical = 8.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text(
      text = QrLocale.strings.cameraPermissionAccess,
      color = Color.White,
      style = QrCodeTheme.typo.innerBoldSize20LineHeight28,
      modifier = Modifier.fillMaxWidth(),
      textAlign = TextAlign.Center,
    )

    Spacer(modifier = Modifier.height(24.dp))

    Text(
      text = QrLocale.strings.cameraNeedToLaunch,
      color = Color.White,
      style = QrCodeTheme.typo.innerMediumSize14LineHeight20,
      modifier = Modifier.fillMaxWidth(),
      textAlign = TextAlign.Center,
    )

    Spacer(modifier = Modifier.height(24.dp))

    TwButton(
      modifier = Modifier.fillMaxWidth(),
      text = QrLocale.strings.goSettings,
      onClick = {
        onClickForwardSettings()
      },
    )
  }
}

@Preview
@Composable
private fun PermissionDeniedForwardSettingPreview() {
  PermissionDeniedForwardSetting(
    onClickForwardSettings = {},
  )
}
