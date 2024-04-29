package ptit.vietpq.qr_scanner.designsystem.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.ui.common.BaseDialog

@Composable
fun RationaleDialog(
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
    onNegativeClick = onNegative ?: {},
  )
}

@Preview
@Composable
private fun RationaleDialogPreview() {
  RationaleDialog(
    title = "Title",
    text = "Text",
  )
}
