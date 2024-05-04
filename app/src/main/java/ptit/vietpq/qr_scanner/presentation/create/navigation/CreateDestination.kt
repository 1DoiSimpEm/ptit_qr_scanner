package ptit.vietpq.qr_scanner.presentation.create.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ptit.vietpq.qr_scanner.presentation.create.CreateRoute
import ptit.vietpq.qr_scanner.presentation.create_detail.CreateQrType
import ptit.vietpq.qr_scanner.presentation.main.navigation.destination.QRNavigationDestination

data object CreateDestination : QRNavigationDestination {
  override val route: String = "create_route"
  override val destination: String = "create_destination"
}

fun NavGraphBuilder.createGraph(onItemClicked: (CreateQrType) -> Unit) = composable(route = CreateDestination.route) {
  CreateRoute(
    onItemClicked = onItemClicked,
  )
}
