package ptit.vietpq.qr_scanner.presentation.multicodes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarCodeResult
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale

private const val MORE_CODES_LIMIT = 9

@Composable
fun MultiScanResult(
  barCodePresent: ImmutableList<BarCodeResult>,
  onTabShowMultiCodes: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = modifier
      .clickable {
        onTabShowMultiCodes()
      }
      .fillMaxWidth()
      .clip(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 0.dp, bottomEnd = 0.dp, bottomStart = 16.dp))
      .background(color = QrCodeTheme.color.neutral5),
  ) {
    Column {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(all = 16.dp),
      ) {
        Text(
          text = if (barCodePresent.size >= MORE_CODES_LIMIT) {
            stringResource(
              R.string.more_codes,
            )
          } else {
            barCodePresent.size.toString()
          },
          color = QrCodeTheme.color.primary,
          style = QrCodeTheme.typo.innerBoldSize24LineHeight28,
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
          modifier = Modifier.fillMaxWidth(0.9f),
        ) {
          Text(
            text = barCodePresent.firstOrNull()?.text ?: QrLocale.strings.text,
            style = QrCodeTheme.typo.innerBoldSize16LineHeight24,
            overflow = TextOverflow.Ellipsis,
            color = QrCodeTheme.color.neutral1,
            maxLines = 1,
          )

          Text(
            text = barCodePresent.firstOrNull()?.formattedText ?: QrLocale.strings.text,
            style = QrCodeTheme.typo.innerBoldSize16LineHeight24,
            overflow = TextOverflow.Ellipsis,
            color = QrCodeTheme.color.neutral3,
            maxLines = 1,
          )
        }

        Image(
          modifier = Modifier.size(16.dp, 24.dp),
          painter = painterResource(id = R.drawable.ic_arrow_mode),
          contentDescription = "Arrow Mode",
        )
      }
    }
  }
}

@Composable
@Preview
private fun MultiScanResultContentPreview() {
  MultiScanResult(
    barCodePresent = persistentListOf(),
    {},
  )
}
