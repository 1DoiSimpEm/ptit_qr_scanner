package ptit.vietpq.qr_scanner.presentation.create_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme

@Composable
fun DayPicker(content: String, date: String, modifier: Modifier = Modifier) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
  ) {
    Text(
      modifier = Modifier.padding(horizontal = QrCodeTheme.dimen.marginDefault, vertical = 20.dp),
      text = content,
      style = QrCodeTheme.typo.body2,
      color = QrCodeTheme.color.neutral1,
    )
    Text(
      modifier = Modifier.padding(QrCodeTheme.dimen.marginDefault),
      text = date,
      style = QrCodeTheme.typo.body,
      color = QrCodeTheme.color.primary,
    )
  }
}
