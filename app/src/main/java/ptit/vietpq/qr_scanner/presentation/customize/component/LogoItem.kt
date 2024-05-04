package ptit.vietpq.qr_scanner.presentation.customize.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme

@Composable
fun LogoItem(drawableResId: Int, isSelected: Boolean, onLogoSelected: (Int) -> Unit, modifier: Modifier = Modifier) {
  val outerStrokeColor by animateColorAsState(
    targetValue = if (isSelected) QrCodeTheme.color.primary else Color.Transparent,
    label = "",
  )
  val innerStrokeColor by animateColorAsState(
    targetValue = if (isSelected) QrCodeTheme.color.neutral5 else Color.Transparent,
    label = "",
  )

  Card(
    modifier = modifier.wrapContentSize().clickable {
      onLogoSelected(drawableResId)
    },
    shape = CircleShape,
    border = BorderStroke(
      width = 1.dp,
      color = outerStrokeColor,
    ),
    colors = CardDefaults.cardColors(
      containerColor = Color.Transparent,
    ),
  ) {
    Card(
      modifier = Modifier.wrapContentSize(),
      shape = CircleShape,
      border = BorderStroke(
        width = 3.dp,
        color = innerStrokeColor,
      ),
      colors = CardDefaults.cardColors(
        containerColor = Color.Transparent,
      ),
    ) {
      Image(
        modifier = Modifier
          .size(48.dp)
          .clip(CircleShape),
        painter = painterResource(id = drawableResId),
        contentDescription = null,
      )
    }
  }
}

@Preview
@Composable
private fun LogoItemPreview() {
  LogoItem(drawableResId = R.drawable.ic_my_business_card, isSelected = true, onLogoSelected = {})
}
