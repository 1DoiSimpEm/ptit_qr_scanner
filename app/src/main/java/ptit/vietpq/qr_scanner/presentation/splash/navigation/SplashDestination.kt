package ptit.vietpq.qr_scanner.presentation.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ptit.vietpq.qr_scanner.presentation.main.navigation.destination.QRNavigationDestination
import ptit.vietpq.qr_scanner.presentation.splash.SplashRoute

data object SplashDestination : QRNavigationDestination {
  override val route: String = "splash_route"
  override val destination: String = "splash_destination"
}

fun NavGraphBuilder.splashGraph(onSplashFinished: (SplashDestinationEnum) -> Unit) =
  composable(route = SplashDestination.route) {
    SplashRoute(onSplashFinished = onSplashFinished)
  }

enum class SplashDestinationEnum {
  TO_LANGUAGE,
  TO_INTRO,
  TO_MAIN,
}
