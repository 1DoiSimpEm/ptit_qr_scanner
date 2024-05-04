package ptit.vietpq.qr_scanner.presentation.resultscan.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.presentation.main.QrAppViewModel
import ptit.vietpq.qr_scanner.presentation.main.navigation.destination.QRNavigationDestination
import ptit.vietpq.qr_scanner.presentation.resultscan.QrCodeResultRoute
import ptit.vietpq.qr_scanner.presentation.resultscan.navigation.QrCodeResultDestination.idArgument
import java.net.URLDecoder
import java.net.URLEncoder
import kotlin.text.Charsets.UTF_8

private val URL_CHARACTER_ENCODING = UTF_8.name()

data object QrCodeResultDestination : QRNavigationDestination {
  override val route = "qr_code_result_route"
  override val destination = "qr_code_result_destination"

  const val idArgument = "idArgument"

  val routeWithArgument = "$route/{$idArgument}"

  fun createNavigationRoute(result: String): String {
    val encodedId = URLEncoder.encode(result, URL_CHARACTER_ENCODING)
    return "$route/$encodedId"
  }

  fun savedStateRoute(savedStateHandle: SavedStateHandle): String = URLDecoder.decode(
    savedStateHandle.get<String>(idArgument),
    URL_CHARACTER_ENCODING,
  )
}

fun NavGraphBuilder.qrCodeResultGraph(mainVieModel: QrAppViewModel, onBackPressed: () -> Unit) = composable(
  route = QrCodeResultDestination.routeWithArgument,
  arguments = listOf(navArgument(idArgument) { type = androidx.navigation.NavType.StringType }),
) {
  QrCodeResultRoute(
    mainViewModel = mainVieModel,
    onBackPressed = onBackPressed,
  )
}
