package ptit.vietpq.qr_scanner.presentation.resultscan.component.content

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme

@Composable
fun TwoColorText(textTitle: String, textContent: String, modifier: Modifier = Modifier) {
  val text = buildAnnotatedString {
    withStyle(style = SpanStyle(color = QrCodeTheme.color.neutral3)) {
      append(textTitle)
    }
    withStyle(style = SpanStyle(color = QrCodeTheme.color.neutral3)) {
      append(": ")
    }
    withStyle(style = SpanStyle(color = QrCodeTheme.color.neutral1)) {
      append(textContent)
    }
  }

  Text(modifier = modifier, text = text, style = QrCodeTheme.typo.innerRegularSize14LineHeight20)
}
