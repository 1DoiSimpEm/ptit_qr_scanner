package ptit.vietpq.qr_scanner.presentation.language.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ptit.vietpq.qr_scanner.presentation.language.LanguageRoute
import ptit.vietpq.qr_scanner.presentation.main.navigation.destination.QRNavigationDestination

data object LanguageDestination : QRNavigationDestination {
  override val route: String = "language_route"
  override val destination: String = "language_destination"
}

fun NavGraphBuilder.languageGraph(onLanguageFinished: () -> Unit, onBackPressed: () -> Unit) =
  composable(route = LanguageDestination.route) {
    LanguageRoute(
      onLanguageFinished = onLanguageFinished,
      onBackPressed = onBackPressed,
    )
  }
