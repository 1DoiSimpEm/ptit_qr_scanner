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
import androidx.compose.ui.unit.dp
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarcodeSchema
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale

@Composable
fun SocialContentView(typeSchema: BarcodeSchema, url: String, modifier: Modifier = Modifier) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier.fillMaxWidth(),
  ) {
    val drawerImage = when (typeSchema) {
      BarcodeSchema.TYPE_FACEBOOK -> {
        R.drawable.ic_facebook
      }

      BarcodeSchema.TYPE_INSTAGRAM -> {
        R.drawable.ic_instagram
      }

      BarcodeSchema.TYPE_SPOTIFY -> {
        R.drawable.ic_spotify
      }

      BarcodeSchema.TYPE_TELEGRAM -> {
        R.drawable.ic_telegram
      }

      BarcodeSchema.TYPE_TIKTOK -> {
        R.drawable.ic_tiktok
      }

      BarcodeSchema.TYPE_TWITTER -> {
        R.drawable.ic_x
      }

      BarcodeSchema.TYPE_WHATSAPP -> {
        R.drawable.ic_whatsapp
      }

      BarcodeSchema.TYPE_YOUTUBE -> {
        R.drawable.ic_youtube
      }

      else -> {
        R.drawable.ic_website
      }
    }

    Image(
      painter = painterResource(id = drawerImage),
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
        text = url,
        style = QrCodeTheme.typo.innerMediumSize14LineHeight20,
      )
    }
  }
}
