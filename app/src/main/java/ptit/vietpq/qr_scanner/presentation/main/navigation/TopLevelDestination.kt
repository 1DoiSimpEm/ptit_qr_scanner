package ptit.vietpq.qr_scanner.presentation.main.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ptit.vietpq.qr_scanner.presentation.create.navigation.CreateDestination
import ptit.vietpq.qr_scanner.presentation.history.navigation.HistoryDestination
import ptit.vietpq.qr_scanner.presentation.main.navigation.destination.QRNavigationDestination
import ptit.vietpq.qr_scanner.presentation.scan.navigation.ScanDestination
import ptit.vietpq.qr_scanner.presentation.setting.navigation.SettingDestination
import ptit.vietpq.qr_scanner.R

enum class TopLevelDestination(
  override val route: String,
  override val destination: String,
  @DrawableRes val iconResourceId: Int,
  @StringRes val textResourceId: Int,
) : QRNavigationDestination {
  Scan(
    route = ScanDestination.route,
    destination = ScanDestination.destination,
    iconResourceId = R.drawable.ic_scan,
    textResourceId = R.string.scan_tab,
  ),
  Create(
    route = CreateDestination.route,
    destination = CreateDestination.destination,
    iconResourceId = R.drawable.ic_create,
    textResourceId = R.string.create,
  ),
  History(
    route = HistoryDestination.route,
    destination = HistoryDestination.destination,
    iconResourceId = R.drawable.ic_history,
    textResourceId = R.string.history_tab,
  ),
  Settings(
    route = SettingDestination.route,
    destination = SettingDestination.destination,
    iconResourceId = R.drawable.ic_setting,
    textResourceId = R.string.settings__settings,
  ),
}
