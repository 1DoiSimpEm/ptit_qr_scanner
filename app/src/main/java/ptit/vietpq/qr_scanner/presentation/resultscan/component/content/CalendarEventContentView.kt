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
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema.CalendarEvent
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale
import ptit.vietpq.qr_scanner.extension.toCustomFormatted

@Composable
fun CalendarContentView(calendarEvent: CalendarEvent, modifier: Modifier = Modifier) {
  Column(
    modifier = Modifier.fillMaxWidth(),
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = modifier,
    ) {
      Image(
        painter = painterResource(id = R.drawable.ic_calendar),
        contentDescription = "Your Image Description",
        modifier = Modifier.size(56.dp),
      )

      Spacer(modifier = Modifier.width(16.dp))

      Text(
        text = QrLocale.strings.calendar,
        color = QrCodeTheme.color.neutral1,
        style = QrCodeTheme.typo.innerBoldSize16LineHeight24,
      )
    }

    Spacer(modifier = Modifier.height(16.dp))

    Column {
      if (!calendarEvent.summary.isNullOrEmpty()) {
        TwoColorText(QrLocale.strings.subject, calendarEvent.summary ?: "")
      }
      if (calendarEvent.start != 0L) {
        TwoColorText(QrLocale.strings.start, calendarEvent.start?.toCustomFormatted() ?: "")
      }

      if (calendarEvent.end != 0L) {
        TwoColorText(QrLocale.strings.end, calendarEvent.end?.toCustomFormatted() ?: "")
      }

      if (!calendarEvent.description.isNullOrEmpty()) {
        TwoColorText(QrLocale.strings.note, calendarEvent.description)
      }

      if (!calendarEvent.location.isNullOrEmpty()) {
        TwoColorText(QrLocale.strings.address, calendarEvent.location)
      }
    }
  }
}
