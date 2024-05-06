package ptit.vietpq.qr_scanner.presentation.customize.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.dp
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.domain.model.customize_qr.ColorModel

@Composable
fun ColorItem(
    colorItem: ColorModel,
    isSelected: Boolean,
    onColorSelected: (ColorModel) -> Unit,
    modifier: Modifier = Modifier,
) {
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
      onColorSelected(colorItem)
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
      if (colorItem.drawableResId != null) {
        Image(
          modifier = Modifier
            .size(48.dp)
            .clip(CircleShape),
          painter = painterResource(id = colorItem.drawableResId),
          contentDescription = null,
        )
      } else {
        Box(
          modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(colorItem.color ?: Color.Black),
        )
      }
    }
  }
}
