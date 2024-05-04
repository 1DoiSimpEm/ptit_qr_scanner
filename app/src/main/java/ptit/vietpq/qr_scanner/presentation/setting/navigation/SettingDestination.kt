package ptit.vietpq.qr_scanner.presentation.setting.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ptit.vietpq.qr_scanner.presentation.main.navigation.destination.QRNavigationDestination
import ptit.vietpq.qr_scanner.presentation.setting.SettingsRoute

data object SettingDestination : QRNavigationDestination {
  override val route = "setting_route"
  override val destination = "setting_destination"
}

fun NavGraphBuilder.settingGraph(onLanguageNavigating: () -> Unit) = composable(route = SettingDestination.route) {
  SettingsRoute(
    onLanguageNavigating = onLanguageNavigating,
  )
}
