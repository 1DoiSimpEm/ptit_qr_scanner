package ptit.vietpq.qr_scanner.presentation.create_detail.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme

@Composable
fun SuggestUrlItem(item: String, onClick: (String) -> Unit, modifier: Modifier = Modifier) {
  Button(
    modifier = modifier,
    onClick = { onClick.invoke(item) },
    colors = ButtonDefaults.buttonColors(containerColor = QrCodeTheme.color.neutral5),
    shape = QrCodeTheme.shape.roundedSmall,
  ) {
    Text(
      text = item,
      style = QrCodeTheme.typo.body,
      color = QrCodeTheme.color.neutral1,
    )
  }
}
