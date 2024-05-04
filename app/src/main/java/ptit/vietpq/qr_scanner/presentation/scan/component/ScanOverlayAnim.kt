package ptit.vietpq.qr_scanner.presentation.scan.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import ptit.vietpq.qr_scanner.R

@Composable
@NonRestartableComposable
fun QrScannerOverlayAnim(
  modifier : Modifier = Modifier,
) {
  val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_scanning_code))

  LottieAnimation(
    modifier = modifier,
    composition = composition,
    iterations = LottieConstants.IterateForever,
  )
}

@Preview
@Composable
private fun QrScannerOverlayPreview2() {
  QrScannerOverlayAnim()
}
