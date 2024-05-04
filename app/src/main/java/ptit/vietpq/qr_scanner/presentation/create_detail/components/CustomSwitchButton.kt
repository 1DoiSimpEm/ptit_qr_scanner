package ptit.vietpq.qr_scanner.presentation.create_detail.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme

@Composable
fun CustomSwitchButton(
  value: Boolean,
  onSwitched: (Boolean) -> Unit,
  modifier: Modifier = Modifier,
  buttonWidth: Dp = 32.dp,
  buttonHeight: Dp = 16.dp,
  switchPadding: Dp = 1.dp,
) {
  val switchSize by remember {
    mutableStateOf(buttonHeight - switchPadding * 2)
  }

  val interactionSource = remember {
    MutableInteractionSource()
  }

  var padding by remember {
    mutableStateOf(0.dp)
  }

  padding = if (value) buttonWidth - switchSize - switchPadding * 2 else 0.dp

  val animateSize by animateDpAsState(
    targetValue = if (value) padding else 0.dp,
    tween(
      durationMillis = 300,
      delayMillis = 0,
      easing = FastOutLinearInEasing,
    ),
    label = "",
  )

  val colorAnimate by animateColorAsState(
    animationSpec = tween(
      durationMillis = 300,
      delayMillis = 0,
      easing = FastOutLinearInEasing,
    ),
    targetValue = if (value) QrCodeTheme.color.primary else QrCodeTheme.color.neutral4,
    label = "",
  )

  Box(
    modifier = Modifier
      .width(buttonWidth)
      .height(buttonHeight)
      .clip(CircleShape)
      .background(colorAnimate)
      .clickable(
        interactionSource = interactionSource,
        indication = null,
      ) {
        onSwitched(!value)
      },
  ) {
    Row(
      modifier = Modifier
        .fillMaxSize()
        .padding(switchPadding),
    ) {
      Box(
        modifier = Modifier
          .fillMaxHeight()
          .width(animateSize)
          .background(Color.Transparent),
      )
      Box(
        modifier = Modifier
          .size(switchSize)
          .clip(CircleShape)
          .background(QrCodeTheme.color.neutral5),
      )
    }
  }
}
