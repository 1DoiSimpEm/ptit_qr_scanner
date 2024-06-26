package ptit.vietpq.qr_scanner.ui.common

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme

@Composable
fun RoundedCornerCheckbox(
  label: String,
  isChecked: Boolean,
  modifier: Modifier = Modifier,
  size: Float = 24f,
  checkedColor: Color = QrCodeTheme.color.primary,
  uncheckedColor: Color = QrCodeTheme.color.background,
  onValueChange: (Boolean) -> Unit,
) {
  val checkboxColor: Color by animateColorAsState(if (isChecked) checkedColor else uncheckedColor, label = "")
  val borderColor: Color by animateColorAsState(if (isChecked) checkedColor else QrCodeTheme.color.neutral3, label = "")
  val density = LocalDensity.current
  val duration = 200

  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier.fillMaxWidth(),
  ) {
    Box(
      modifier = Modifier
        .size(size.dp)
        .background(color = checkboxColor, shape = CircleShape)
        .border(width = 1.5.dp, color = borderColor, shape = CircleShape),
      contentAlignment = Alignment.Center,
    ) {
      androidx.compose.animation.AnimatedVisibility(
        visible = isChecked,
        enter = slideInHorizontally(animationSpec = tween(duration)) {
          with(density) { (size * -0.5).dp.roundToPx() }
        } + expandHorizontally(
          expandFrom = Alignment.Start,
          animationSpec = tween(duration),
        ),
        exit = fadeOut(),
      ) {
        Icon(
          Icons.Default.Check,
          modifier = Modifier.padding(5.dp),
          contentDescription = null,
          tint = uncheckedColor,
        )
      }
    }
  }
}

@Preview
@Composable
private fun RoundedCornerCheckboxPreview() {
  RoundedCornerCheckbox(
    label = "RoundedCornerCheckbox",
    isChecked = true,
    onValueChange = {},
  )
}
