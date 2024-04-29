package ptit.vietpq.qr_scanner.designsystem.locale

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import ptit.vietpq.qr_scanner.designsystem.locale.Strings

object QrLocale {
  val strings: Strings
    @Composable
    get() = Strings(LocalContext.current)
}
