package ptit.vietpq.qr_scanner.presentation.create_detail.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale

@Composable
fun CreateButton(modifier: Modifier = Modifier, isEnable: Boolean = true, onClick: () -> Unit) {
  Button(
    onClick = onClick,
    modifier = modifier,
    colors = ButtonDefaults.buttonColors(
      containerColor = QrCodeTheme.color.primary,
      disabledContainerColor = QrCodeTheme.color.primaryDisable,
    ),
    enabled = isEnable,
  ) {
    Text(
      modifier = Modifier.padding(vertical = QrCodeTheme.dimen.marginSmall),
      text = QrLocale.strings.create,
      style = QrCodeTheme.typo.button,
      color = QrCodeTheme.color.neutral5,
    )
  }
}
