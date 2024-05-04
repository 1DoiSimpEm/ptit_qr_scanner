package ptit.vietpq.qr_scanner.presentation.multicodes

import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale
import ptit.vietpq.qr_scanner.ui.common.BaseDialog

@Composable
fun ConfirmDeleteDialog(
  title: String? = null,
  text: String? = null,
  positiveText: String? = null,
  negativeText: String? = null,
  onPositive: (() -> Unit)? = null,
  onNegative: (() -> Unit)? = null,
  onDismissRequest: () -> Unit = {},
) {
  BaseDialog(
    onDismissRequest = onDismissRequest,
    title = title,
    body = text,
    positive = positiveText ?: QrLocale.strings.settings,
    negative = negativeText ?: QrLocale.strings.commonCancel,
    onPositiveClick = {
      if (onPositive != null) {
        onPositive.invoke()
      } else {
        onDismissRequest()
      }
    },
    showActionRow = true,
    onNegativeClick = onNegative ?: {},
    buttonColorsNegative = ButtonDefaults.buttonColors(
      containerColor = QrCodeTheme.color.accentRed,
      contentColor = QrCodeTheme.color.background,
    ),
  )
}
