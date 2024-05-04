package ptit.vietpq.qr_scanner.presentation.splash

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ptit.vietpq.qr_scanner.presentation.splash.navigation.SplashDestinationEnum
import kotlinx.coroutines.delay
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale

@Composable
fun SplashRoute(
  splashViewModel: SplashViewModel = hiltViewModel(),
  onSplashFinished: (SplashDestinationEnum) -> Unit,
) {
  val rememberActionFinished by rememberUpdatedState(newValue = onSplashFinished)
  val activity = LocalContext.current as Activity

  LaunchedEffect(key1 = Unit) {
    delay(2000)
    val destinationType = when {
      splashViewModel.onboardIntroPassed && splashViewModel.onboardLanguagePassed -> SplashDestinationEnum.TO_MAIN
      !splashViewModel.onboardLanguagePassed -> SplashDestinationEnum.TO_LANGUAGE
      else -> SplashDestinationEnum.TO_INTRO
    }

    rememberActionFinished.invoke(destinationType)
  }

  SplashScreen()
}

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
  Box(
    modifier = modifier
      .fillMaxSize()
      .background(QrCodeTheme.color.backgroundGreen),
    contentAlignment = Alignment.Center,
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Image(
        modifier = Modifier
          .size(200.dp)
          .offset(y = (-24).dp),
        painter = painterResource(id = R.drawable.img),
        contentDescription = null,
      )
      Text(
        text = QrLocale.strings.qrCodeScanner,
        style = QrCodeTheme.typo.heading,
        color = QrCodeTheme.color.neutral1,
      )
    }
    Text(
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .padding(QrCodeTheme.dimen.marginPrettyLarge),
      text = QrLocale.strings.thisActionMayContainsAds,
      style = QrCodeTheme.typo.body,
      color = QrCodeTheme.color.neutral2,
    )
  }
}
