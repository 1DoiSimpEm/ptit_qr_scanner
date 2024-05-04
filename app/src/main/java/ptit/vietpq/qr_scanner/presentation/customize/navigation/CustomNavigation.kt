package ptit.vietpq.qr_scanner.presentation.customize.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ptit.vietpq.qr_scanner.presentation.customize.CustomRoute
import ptit.vietpq.qr_scanner.presentation.main.navigation.destination.QRNavigationDestination

data object CustomNavigation : QRNavigationDestination {
  override val route: String = "custom_route"
  override val destination: String = "custom_destination"
}

fun NavGraphBuilder.customRoute(onBackPressed: () -> Unit) = composable(
  route = CustomNavigation.route,
) {
  CustomRoute(onBackPressed = onBackPressed)
}
