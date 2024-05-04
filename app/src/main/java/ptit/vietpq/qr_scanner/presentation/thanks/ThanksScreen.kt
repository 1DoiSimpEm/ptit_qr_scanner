package ptit.vietpq.qr_scanner.presentation.thanks

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme

@Composable
fun ThanksRoute() {
  ThanksScreen()
}

@Composable
fun ThanksScreen(modifier: Modifier = Modifier) {
  val activity = LocalContext.current as Activity

  LaunchedEffect(Unit) {
    delay(2000)
    activity.finish()
  }


  Box(
    modifier = modifier
      .fillMaxSize()
      .background(QrCodeTheme.color.neutral5),
    contentAlignment = Alignment.Center,
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Image(painter = painterResource(id = R.drawable.img_thanks), contentDescription = null)
      Spacer(modifier = Modifier.size(16.dp))
      Text(
        text = stringResource(R.string.thanks_for_using_our_app),
        style = QrCodeTheme.typo.subTitle,
        color = QrCodeTheme.color.neutral1,
      )
    }
  }
}

@Preview
@Composable
private fun ThanksScreenPreview() {
  ThanksScreen()
}
