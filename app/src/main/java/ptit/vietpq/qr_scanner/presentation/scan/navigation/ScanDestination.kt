package ptit.vietpq.qr_scanner.presentation.scan.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ptit.vietpq.qr_scanner.presentation.main.navigation.destination.QRNavigationDestination
import ptit.vietpq.qr_scanner.presentation.scan.ScanRoute
import ptit.vietpq.qr_scanner.presentation.scan.ScanViewModel

data object ScanDestination : QRNavigationDestination {
  override val route = "scan_route"
  override val destination = "scan_destination"
}

fun NavGraphBuilder.scanGraph(
  viewModel: ScanViewModel,
  qrCodeResult: (String) -> Unit,
  onShowTabMultiCodes: () -> Unit,
  showBottomSheet: (Boolean) -> Unit,
  showExitBottomSheet: () -> Unit,
  onImagePick :( ) -> Unit
) = composable(route = ScanDestination.route) {
  ScanRoute(
    viewModel = viewModel,
    onNavigationResult = qrCodeResult,
    onShowTabMultiCodes = onShowTabMultiCodes,
    showBottomSheet = showBottomSheet,
    showExitBottomSheet = showExitBottomSheet,
    onImagePick =  onImagePick,
  )
}
