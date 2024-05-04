package ptit.vietpq.qr_scanner.presentation.scan.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme

@Composable
fun Modal(content: @Composable () -> Unit, modifier: Modifier = Modifier) {
  Column(
    modifier
      .fillMaxWidth()
      .imePadding()
      .animateContentSize(),
  ) {
//    if (showHandle) {
//      SlideHandle(
//        modifier = Modifier
//          .padding(top = 12.dp)
//          .align(CenterHorizontally),
//      )
//    }

    content()
  }
}

@Composable
fun SlideHandle(modifier: Modifier = Modifier) {
  Box(
    modifier = modifier
      .width(44.dp)
      .height(4.dp)
      .clip(QrCodeTheme.shape.roundedDefault)
      .background(QrCodeTheme.color.background),
  )
}
