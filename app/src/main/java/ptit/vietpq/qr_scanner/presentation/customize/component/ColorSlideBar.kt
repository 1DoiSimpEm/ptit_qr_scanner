package ptit.vietpq.qr_scanner.presentation.customize.component

import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import ptit.vietpq.qr_scanner.extension.drawTransparentBackground
import kotlinx.collections.immutable.PersistentList

private const val thumbRadius = 12f

@ExperimentalComposeUiApi
@Composable
internal fun ColorSlideBar(
  colors: PersistentList<Color>,
  progressColor: Color,
  onProgress: (Float) -> Unit,
  modifier: Modifier = Modifier,
) {
  var progress by remember {
    mutableFloatStateOf(1f)
  }
  var slideBarSize by remember {
    mutableStateOf(IntSize.Zero)
  }
  LaunchedEffect(progress) {
    onProgress(progress)
  }
  Canvas(
    modifier = modifier
      .width(192.dp)
      .height(12.dp)
      .onSizeChanged {
        slideBarSize = it
      }
      .pointerInteropFilter {
        when (it.action) {
          MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
            progress = (it.x / slideBarSize.width).coerceIn(0f, 1f)
          }
        }
        return@pointerInteropFilter true
      }
      .clipToBounds()
      .clip(RoundedCornerShape(100))
      .border(0.2.dp, Color.LightGray, RoundedCornerShape(100)),
  ) {
    drawTransparentBackground(3)
    drawRect(Brush.horizontalGradient(colors, startX = size.height / 2, endX = size.width - size.height / 2))
    drawCircle(
      color = Color.White,
      radius = thumbRadius,
      center = Offset(
        thumbRadius + (size.height / 2 - thumbRadius) +
          ((size.width - (thumbRadius + (size.height / 2 - thumbRadius)) * 2) * progress),
        size.height / 2,
      ),
    )
    drawCircle(
      color = progressColor,
      radius = thumbRadius - 1.dp.toPx(),
      center = Offset(
        thumbRadius + (size.height / 2 - thumbRadius) +
          ((size.width - (thumbRadius + (size.height / 2 - thumbRadius)) * 2) * progress),
        size.height / 2,
      ),
    )
  }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Preview
private fun ColorSlideBarPreview() {
  ColorSlideBar(Colors.gradientColors, Color.Red, {})
}
