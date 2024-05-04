package ptit.vietpq.qr_scanner.presentation.create_result.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ptit.vietpq.qr_scanner.presentation.create_result.CreateQrResultRoute
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.presentation.main.QrAppViewModel
import ptit.vietpq.qr_scanner.presentation.main.navigation.destination.QRNavigationDestination

data object CreateQrResultNavigation : QRNavigationDestination {
  override val route: String = "create_qr_result_route"
  override val destination: String = "create_qr_result_destination"
//  const val typeArgument = "typeArgument"
//  const val contentArgument = "contentArgument"

//  val routeWithArgument = "$route/{$typeArgument}/{$contentArgument}"
//  fun createNavigationRoute(type: String, content: String): String = "$route/$type/$content"

//  fun savedStateTypeArgument(savedStateHandle: SavedStateHandle): String = savedStateHandle.get<String>(
//    typeArgument
//  ) ?: ""

//  fun savedStateContentArgument(savedStateHandle: SavedStateHandle): String = savedStateHandle.get<String>(
//    contentArgument
//  ) ?: ""
}

fun NavGraphBuilder.createQrResultGraph(
  mainViewModel: QrAppViewModel,
  onBackPressed: () -> Unit,
  onDoneButtonClicked: () -> Unit,
  onEditClicked: () -> Unit,
) = composable(
  route = CreateQrResultNavigation.route,
) {
  CreateQrResultRoute(
    mainViewModel = mainViewModel,
    onBackPressed = onBackPressed,
    onDoneButtonClicked = onDoneButtonClicked,
    onEditClicked = onEditClicked,
  )
}
