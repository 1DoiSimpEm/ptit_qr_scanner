package ptit.vietpq.qr_scanner.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import java.text.SimpleDateFormat
import java.util.Locale

fun Long.toTimeFormatted(): String {
  val date = java.util.Date(this)
  val format = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
  return format.format(date)
}

fun Long.toCustomFormatted(): String = try {
  val date = java.util.Date(this)
  val format = SimpleDateFormat("HH:mm  yyyy-MM-dd", Locale.getDefault())
  format.format(date)
} catch (e: Exception) {
  ""
}

fun Long.toDateFormatted(): String {
  val date = java.util.Date(this)
  val format = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
  return format.format(date)
}


inline val Dp.px: Float
  @Composable get() = with(LocalDensity.current) { this@px.toPx() }
