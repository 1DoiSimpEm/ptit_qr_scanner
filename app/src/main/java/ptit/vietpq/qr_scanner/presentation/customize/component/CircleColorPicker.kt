package ptit.vietpq.qr_scanner.presentation.customize.component

import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ptit.vietpq.qr_scanner.extension.darken
import ptit.vietpq.qr_scanner.extension.drawColorSelector
import ptit.vietpq.qr_scanner.extension.lighten
import ptit.vietpq.qr_scanner.presentation.customize.component.Colors.gradientColors
import kotlin.math.atan2
import kotlin.math.roundToInt
import kotlinx.collections.immutable.persistentListOf
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.utils.BoundedPointStrategy
import ptit.vietpq.qr_scanner.utils.ColorPickerHelper
import ptit.vietpq.qr_scanner.utils.MathHelper.getBoundedPointWithInRadius
import ptit.vietpq.qr_scanner.utils.MathHelper.getLength

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CircleColorPicker(onColorPicked: (Color) -> Unit, modifier: Modifier = Modifier) {
  var radius by remember {
    mutableFloatStateOf(0f)
  }
  var pickerLocation by remember(radius) {
    mutableStateOf(Offset(radius, radius))
  }
  var pickedColor by remember {
    mutableStateOf(Color.White)
  }
  var circleColor by remember {
    mutableStateOf(Color.White)
  }
  var brightness by remember {
    mutableFloatStateOf(0f)
  }
  var alpha by remember {
    mutableFloatStateOf(1f)
  }
  var rangeProgress by remember {
    mutableDoubleStateOf(1.0)
  }
  var radiusProgress by remember {
    mutableFloatStateOf(1f)
  }
  var range by remember {
    mutableStateOf(ColorRange.RedToYellow)
  }

  LaunchedEffect(brightness, pickedColor, alpha) {
    onColorPicked(
      pickedColor,
    )
  }
  Column(
    modifier = modifier.width(IntrinsicSize.Max),
  ) {
    Canvas(
      modifier = Modifier
        .size(232.dp)
        .align(Alignment.CenterHorizontally)
        .onSizeChanged {
          radius = it.width / 2f
        }
        .pointerInteropFilter {
          when (it.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
              val angle =
                (
                  Math.toDegrees(
                    atan2(
                      it.y - radius,
                      it.x - radius,
                    ).toDouble(),
                  ) + 360
                  ) % 360
              val length = getLength(it.x, it.y, radius)
              radiusProgress = 1 - (length / radius).coerceIn(0f, 1f)
              val angleProgress = angle / 360f
              val tempRange = ColorPickerHelper.calculateRangeProgress(
                angleProgress,
              )
              rangeProgress = tempRange.first
              range = tempRange.second
              pickedColor = updatePickedColor(
                range,
                rangeProgress,
                radiusProgress,
                brightness,
                alpha.roundToInt(),
              )
              circleColor = pickedColor
              pickerLocation = getBoundedPointWithInRadius(
                it.x,
                it.y,
                length,
                radius,
                BoundedPointStrategy.Inside,
              )
            }
          }
          return@pointerInteropFilter true
        },
    ) {
      drawCircle(
        Brush.sweepGradient(gradientColors),
      )
      drawCircle(
        ShaderBrush(
          RadialGradientShader(
            Offset(size.width / 2f, size.height / 2f),
            colors = listOf(
              Color.White,
              Color.Transparent,
            ),
            radius = size.width / 2f,
          ),
        ),
      )
      drawColorSelector(pickedColor, pickerLocation)
    }
    Spacer(modifier = Modifier.height(24.dp))
    Row {
      Box(
        modifier = Modifier
          .size(40.dp)
          .clip(QrCodeTheme.shape.roundedSmall)
          .background(pickedColor),
      ) {
        Image(
          modifier = Modifier.align(Alignment.Center),
          painter = painterResource(id = R.drawable.ic_get_color),
          contentDescription = null,
        )
      }
      Column {
        ColorSlideBar(
          modifier = Modifier.padding(start = 16.dp),
          colors = persistentListOf(
            Color.Black,
            circleColor,
          ),
          progressColor = pickedColor,
          onProgress = {
            brightness = 1 - it
            pickedColor = updatePickedColor(
              range,
              rangeProgress,
              radiusProgress,
              brightness,
              alpha.roundToInt(),
            )
          },
        )

        Spacer(modifier = Modifier.height(16.dp))
        ColorSlideBar(
          modifier = Modifier.padding(start = 16.dp),
          colors = persistentListOf(Color.Transparent, circleColor),
          progressColor = pickedColor,
          onProgress = {
            alpha = it
            pickedColor = pickedColor.copy(alpha = it)
          },
        )
      }
    }
  }
}

private fun updatePickedColor(
  range: ColorRange,
  rangeProgress: Double,
  radiusProgress: Float,
  brightness: Float,
  alpha: Int,
): Color = when (range) {
  ColorRange.RedToYellow -> {
    Color(
      red = 255
        .lighten(radiusProgress)
        .darken(brightness),
      green = (255f * rangeProgress)
        .lighten(radiusProgress)
        .darken(brightness)
        .roundToInt(),
      blue = 0
        .lighten(radiusProgress)
        .darken(brightness),
      alpha = (255 * alpha),
    )
  }

  ColorRange.YellowToGreen -> {
    Color(
      red = (255 * (1 - rangeProgress))
        .lighten(radiusProgress)
        .darken(brightness)
        .roundToInt(),
      green = 255.lighten(radiusProgress).darken(brightness),
      blue = 0.lighten(radiusProgress),
      alpha = (255 * alpha),
    )
  }

  ColorRange.GreenToCyan -> {
    Color(
      red = 0.lighten(radiusProgress).darken(brightness),
      green = 255.lighten(radiusProgress).darken(brightness),
      blue = (255 * rangeProgress)
        .lighten(radiusProgress)
        .darken(brightness)
        .roundToInt(),
      alpha = (255 * alpha),
    )
  }

  ColorRange.CyanToBlue -> {
    Color(
      red = 0.lighten(radiusProgress).darken(brightness),
      green = (255 * (1 - rangeProgress))
        .lighten(radiusProgress)
        .darken(brightness)
        .roundToInt(),
      blue = 255.lighten(radiusProgress).darken(brightness),
      alpha = (255 * alpha),
    )
  }

  ColorRange.BlueToPurple -> {
    Color(
      red = (255 * rangeProgress)
        .lighten(radiusProgress)
        .darken(brightness)
        .roundToInt(),
      green = 0.lighten(radiusProgress).darken(brightness),
      blue = 255.lighten(radiusProgress).darken(brightness),
      alpha = (255 * alpha),
    )
  }

  ColorRange.PurpleToRed -> {
    Color(
      red = 255.lighten(radiusProgress).darken(brightness),
      green = 0.lighten(radiusProgress).darken(brightness),
      blue = (255 * (1 - rangeProgress))
        .lighten(radiusProgress)
        .darken(brightness)
        .roundToInt(),
      alpha = (255 * alpha),
    )
  }
}

@Preview
@Composable
private fun CircleColorPickerPreview() {
  CircleColorPicker(onColorPicked = {})
}

@Stable
@Immutable
internal enum class ColorRange {
  RedToYellow,
  YellowToGreen,
  GreenToCyan,
  CyanToBlue,
  BlueToPurple,
  PurpleToRed,
}

@Stable
@Immutable
internal object Colors {
  val gradientColors = persistentListOf(
    Color(0xffff0000),
    Color(0xffffff00),
    Color(0xff00ff00),
    Color(0xff00ffff),
    Color(0xff0000ff),
    Color(0xffff00ff),
    Color(0xffff0000),
  )
}
