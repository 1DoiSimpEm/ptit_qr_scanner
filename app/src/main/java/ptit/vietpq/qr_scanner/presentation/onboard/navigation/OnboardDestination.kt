package ptit.vietpq.qr_scanner.presentation.onboard.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.presentation.main.QrAppViewModel
import ptit.vietpq.qr_scanner.presentation.main.navigation.destination.QRNavigationDestination
import ptit.vietpq.qr_scanner.presentation.onboard.OnboardRoute

data object OnboardDestination : QRNavigationDestination {
  override val route: String = "onboard_route"
  override val destination: String = "onboard_destination"
}

fun NavGraphBuilder.onboardGraph(onOnboardFinished: () -> Unit, mainViewModel: QrAppViewModel) =
  composable(route = OnboardDestination.route) {
    OnboardRoute(mainViewModel = mainViewModel, onOnboardFinished = onOnboardFinished)
  }
