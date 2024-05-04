package ptit.vietpq.qr_scanner.presentation.history_selection.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ptit.vietpq.qr_scanner.presentation.history_selection.HistorySelectionRoute
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.presentation.main.QrAppViewModel
import ptit.vietpq.qr_scanner.presentation.main.navigation.destination.QRNavigationDestination

data object HistorySelectionNavigation : QRNavigationDestination {
  override val route: String = "history_selection_route"
  override val destination: String = "history_selection_destination"
  const val idArgument = "idArgument"
  val routeWithArgument = "$route/{$idArgument}"

  fun createNavigationRoute(isCreated: Boolean): String = "$route/$isCreated"

  fun savedStateRoute(savedStateHandle: SavedStateHandle): Boolean = savedStateHandle.get<Boolean>(
    idArgument,
  ) ?: false
}

fun NavGraphBuilder.historySelectionGraph(mainViewModel: QrAppViewModel, onBackPress: () -> Unit): Unit = composable(
  route = HistorySelectionNavigation.routeWithArgument,
  arguments = listOf(
    navArgument(HistorySelectionNavigation.idArgument) {
      type = androidx.navigation.NavType.BoolType
    },
  ),
) {
  HistorySelectionRoute(
    onBackPress = onBackPress,
    mainViewModel = mainViewModel,
  )
}
