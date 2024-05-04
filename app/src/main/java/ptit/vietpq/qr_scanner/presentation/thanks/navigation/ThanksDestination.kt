package ptit.vietpq.qr_scanner.presentation.thanks.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ptit.vietpq.qr_scanner.presentation.main.navigation.destination.QRNavigationDestination
import ptit.vietpq.qr_scanner.presentation.thanks.ThanksRoute

data object ThanksDestination : QRNavigationDestination {
  override val route: String = "thanks_route"
  override val destination: String = "thanks_destination"
}

fun NavGraphBuilder.thanksGraph() = composable(route = ThanksDestination.route) {
  ThanksRoute()
}
