package ptit.vietpq.qr_scanner.presentation.create_detail.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ptit.vietpq.qr_scanner.presentation.area_code.navigation.AreaCodeNavigation
import ptit.vietpq.qr_scanner.presentation.create_detail.CreateQrDetailRoute
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.presentation.main.QrAppViewModel
import ptit.vietpq.qr_scanner.presentation.main.navigation.destination.QRNavigationDestination

data object CreateQrDetailNavigation : QRNavigationDestination {
  override val route: String = "createQrDetail_navigation"
  override val destination: String = "createQrDetail_destination"
  const val idArgument = "idArgument"
  val routeWithArgument = "$route/{$idArgument}"

  fun createNavigationRoute(type: Int): String = "$route/$type"

  fun savedStateRoute(savedStateHandle: SavedStateHandle): Int = savedStateHandle.get<Int>(
    idArgument,
  ) ?: 0
}

fun NavGraphBuilder.createQrDetailGraph(
  mainViewModel: QrAppViewModel,
  onCountryCodeClicked: () -> Unit,
  onBackPressed: () -> Unit,
  onCreatePressed: () -> Unit,
) = composable(
  route = CreateQrDetailNavigation.routeWithArgument,
  arguments = listOf(
    navArgument(CreateQrDetailNavigation.idArgument) {
      type = androidx.navigation.NavType.IntType
    },
  ),
) { entry ->
  val result = entry.savedStateHandle.getStateFlow(AreaCodeNavigation.areaCode, "+888").collectAsStateWithLifecycle()
  CreateQrDetailRoute(
    mainViewModel = mainViewModel,
    countryCode = result.value,
    onBackPressed = onBackPressed,
    onCreatePressed = onCreatePressed,
    onCountryCodeClicked = onCountryCodeClicked,
  )
}
