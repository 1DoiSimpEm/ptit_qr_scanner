package ptit.vietpq.qr_scanner.presentation.history.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ptit.vietpq.qr_scanner.presentation.history.HistoryRoute
import ptit.vietpq.qr_scanner.presentation.main.navigation.destination.QRNavigationDestination

data object HistoryDestination : QRNavigationDestination {
  override val route = "history_route"
  override val destination = "history_destination"
}

fun NavGraphBuilder.historyGraph(
  onItemClicked: (Boolean, String) -> Unit,
  onItemLongClicked: (Boolean) -> Unit,
  onScanNowClicked: (Boolean) -> Unit,
) = composable(route = HistoryDestination.route) {
  HistoryRoute(
    onItemClicked = onItemClicked,
    onItemLongClicked = onItemLongClicked,
    onScanNowClicked = onScanNowClicked,
  )
}
