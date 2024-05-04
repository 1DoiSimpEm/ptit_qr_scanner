package ptit.vietpq.qr_scanner.presentation.area_code.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ptit.vietpq.qr_scanner.presentation.area_code.AreaCodeRoute
import ptit.vietpq.qr_scanner.presentation.main.navigation.destination.QRNavigationDestination

data object AreaCodeNavigation : QRNavigationDestination {
  override val route = "area_code_route"
  override val destination = "area_code_destination"
  const val areaCode = "areaCode"
}

fun NavGraphBuilder.areaCodeNavigationGraph(onAreaSelected: (String) -> Unit, onBackPressed: () -> Unit) =
  composable(route = AreaCodeNavigation.route) {
    AreaCodeRoute(
      onAreaSelected = onAreaSelected,
      onBackPressed = onBackPressed,
    )
  }
