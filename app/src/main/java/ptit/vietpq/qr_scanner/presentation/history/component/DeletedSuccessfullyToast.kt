package ptit.vietpq.qr_scanner.presentation.history.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale

@Preview
@Composable
private fun DeletedSuccessfullyToastPreview() {
  Box {
    DeletedSuccessfullyToast(isShow = true)
  }
}

@Composable
fun DeletedSuccessfullyToast(isShow: Boolean, modifier: Modifier = Modifier) {
  AnimatedVisibility(
    modifier = modifier,
    visible = isShow,
    enter = fadeIn(),
    exit = fadeOut(),
  ) {
    Box(modifier = Modifier.clip(QrCodeTheme.shape.roundedButton).background(QrCodeTheme.color.neutral3)) {
      Text(
        modifier = Modifier
          .align(Alignment.Center)
          .padding(horizontal = 24.dp, vertical = 12.dp),
        text = QrLocale.strings.deletedSuccessfully,
        style = QrCodeTheme.typo.body1,
        color = QrCodeTheme.color.neutral5,
      )
    }
  }
}
