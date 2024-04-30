package ptit.vietpq.qr_scanner.ui.common

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale

@SuppressLint("ComposeModifierComposed")
fun Modifier.scaleBounce(enabled: Boolean = true) = this.composed {
  val infiniteTransition = rememberInfiniteTransition(label = "")

  val scale by infiniteTransition.animateFloat(
    initialValue = 0.9f,
    targetValue = 1.1f,
    animationSpec = infiniteRepeatable(
      animation = tween(500, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse,
    ),
    label = "",
  )

  if (enabled) {
    Modifier.scale(scale)
  } else {
    this
  }
}
