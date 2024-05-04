package ptit.vietpq.qr_scanner.presentation.multicodes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarCodeResult
import ptit.vietpq.qr_scanner.data.mapper.imageDrawerByType
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.extension.toCustomFormatted
import ptit.vietpq.qr_scanner.ui.common.RoundedCornerCheckbox

@Composable
fun ItemRowBarCode(
  item: BarCodeResult,
  modifier: Modifier = Modifier,
  modeSelected: Boolean = false,
  onClickSelectedDelete: (BarCodeResult) -> Unit = {},
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .clickable { onClickSelectedDelete(item) }
      .padding(horizontal = 16.dp, vertical = 8.dp)
      .clip(shape = QrCodeTheme.shape.roundedLarge)
      .background(QrCodeTheme.color.background),
  ) {
    Column(
      modifier = Modifier.fillMaxWidth(1f),
      verticalArrangement = Arrangement.SpaceBetween,
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(all = 16.dp),
      ) {
        Image(
          painter = painterResource(id = item.imageDrawerByType()),
          modifier = Modifier.size(40.dp),
          contentDescription = item.text,
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
          modifier = Modifier
            .fillMaxWidth(if (modeSelected) 0.9f else 1f)
            .padding(end = 16.dp),
        ) {
          Text(
            text = item.formattedText,
            style = QrCodeTheme.typo.innerBoldSize16LineHeight24,
            overflow = TextOverflow.Ellipsis,
            color = QrCodeTheme.color.neutral1,
            maxLines = 1,
          )

          Spacer(modifier = Modifier.height(5.dp))

          Text(
            text = item.text,
            style = QrCodeTheme.typo.innerRegularSize14LineHeight20,
            overflow = TextOverflow.Ellipsis,
            color = QrCodeTheme.color.neutral1,
            maxLines = 1,
          )

          Spacer(modifier = Modifier.height(2.dp))

          Text(
            text = item.date.toCustomFormatted(),
            style = QrCodeTheme.typo.innerMediumSize12LineHeight16,
            overflow = TextOverflow.Ellipsis,
            color = QrCodeTheme.color.neutral3,
            maxLines = 1,
          )
        }
        if (modeSelected) {
          RoundedCornerCheckbox(
            label = "Select",
            isChecked = item.isSelected,
            onValueChange = { /* handle checkbox state change */ },
          )
        }
      }
    }
  }
}

@Preview
@Composable
private fun ItemRowBarCodePreview() {
  ItemRowBarCode(
    item = BarCodeResult.INITIAL,
    modeSelected = true,
  )
}
