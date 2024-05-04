package ptit.vietpq.qr_scanner.presentation.customize.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.customize_qr.ShapeModel
import io.github.alexzhirkevich.qrose.options.QrOptions
import io.github.alexzhirkevich.qrose.options.QrShapes
import io.github.alexzhirkevich.qrose.rememberQrCodePainter
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme

@Composable
fun ShapeItem(
  onShapeSelected: (ShapeModel) -> Unit,
  shape: ShapeModel,
  isSelected: Boolean,
  modifier: Modifier = Modifier,
) {
  val borderStateColor by animateColorAsState(
    targetValue = if (isSelected) QrCodeTheme.color.primary else QrCodeTheme.color.neutral4,
    label = "",
  )

  val qrPainter = rememberQrCodePainter(
    data = "ikameglobal.com",
    options = QrOptions(
      shapes = QrShapes(
        ball = shape.ball,
        frame = shape.frame,
      ),
    ),
  )

  Card(
    modifier = modifier
      .clip(QrCodeTheme.shape.roundedDefault)
      .clickable {
        onShapeSelected(shape)
      },
    border = BorderStroke(
      width = 2.dp,
      color = borderStateColor,
    ),
    colors = CardColors(
      containerColor = QrCodeTheme.color.neutral5,
      contentColor = QrCodeTheme.color.primary,
      disabledContainerColor = QrCodeTheme.color.neutral5,
      disabledContentColor = QrCodeTheme.color.neutral4,
    ),
  ) {
    Box {
      Image(
        modifier = Modifier.padding(6.dp),
        painter = qrPainter,
        contentDescription = null,
      )

      this@Card.AnimatedVisibility(
        modifier = Modifier.align(Alignment.Center),
        visible = isSelected,
      ) {
        Image(painter = painterResource(id = R.drawable.ic_checked), contentDescription = null)
      }
    }
  }
}
