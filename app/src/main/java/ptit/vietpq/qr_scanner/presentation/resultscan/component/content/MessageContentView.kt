package ptit.vietpq.qr_scanner.presentation.resultscan.component.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema.SmsContent
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale
import ptit.vietpq.qr_scanner.extension.EMPTY

@Composable
fun MessageContentView(smsContent: SmsContent, modifier: Modifier = Modifier) {
  Column(
    modifier = Modifier.fillMaxWidth(),
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = modifier,
    ) {
      Image(
        painter = painterResource(id = R.drawable.ic_message),
        contentDescription = "Your Image Description",
        modifier = Modifier.size(56.dp),
      )

      Spacer(modifier = Modifier.width(16.dp))

      Text(
        text = QrLocale.strings.message,
        color = QrCodeTheme.color.neutral1,
        style = QrCodeTheme.typo.innerBoldSize16LineHeight24,
      )
    }

    Spacer(modifier = Modifier.height(16.dp))

    Column {
      if (smsContent.phoneNumber != String.EMPTY) {
        TwoColorText(QrLocale.strings.tel, smsContent.phoneNumber)
      }

      if (smsContent.message != String.EMPTY) {
        TwoColorText(QrLocale.strings.content, smsContent.message)
      }
    }
  }
}
