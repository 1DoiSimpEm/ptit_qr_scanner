package ptit.vietpq.qr_scanner.presentation.resultscan.component.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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

@Composable
fun UrlBookmarkContent(textUrl: String, modifier: Modifier = Modifier) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier.fillMaxWidth(),
  ) {
    Image(
      painter = painterResource(id = R.drawable.ic_website),
      contentDescription = "Your Image Description",
      modifier = Modifier.size(56.dp),
    )

    Spacer(modifier = Modifier.width(16.dp))

    Column {
      Text(
        text = QrLocale.strings.website,
        style = QrCodeTheme.typo.innerBoldSize16LineHeight24,
      )
      Text(
        text = textUrl,
        style = QrCodeTheme.typo.innerMediumSize14LineHeight20,
      )
    }
  }
}

@Preview
@Composable
private fun UrlBookmarkContentPreview() {
  UrlBookmarkContent(textUrl = "https://www.google.com")
}
