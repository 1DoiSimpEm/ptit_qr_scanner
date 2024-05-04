package ptit.vietpq.qr_scanner.presentation.multicodes.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ptit.vietpq.qr_scanner.presentation.main.navigation.destination.QRNavigationDestination
import ptit.vietpq.qr_scanner.presentation.multicodes.MultiCodesRoute
import ptit.vietpq.qr_scanner.presentation.scan.ScanViewModel

data object MultiCodesDestination : QRNavigationDestination {
  override val route = "multi_codes_route"
  override val destination = "multi_codes_destination"
}

fun NavGraphBuilder.multiCodesGraph(
    viewModel: ScanViewModel,
    onBackPress: () -> Unit,
    onNavigationResult: (barCodeResult: String) -> Unit,
): Unit = composable(route = MultiCodesDestination.route) {
  MultiCodesRoute(
    viewModel = viewModel,
    onBackPress = onBackPress,
    onNavigationResult = onNavigationResult,
  )
}
