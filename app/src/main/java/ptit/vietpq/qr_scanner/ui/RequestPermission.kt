package ptit.vietpq.qr_scanner.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import ptit.vietpq.qr_scanner.designsystem.common.RationaleDialog

@Composable
fun RequestPermissionDialog(
  positiveText: String,
  negativeText: String,
  rationaleTitle: String = "",
  rationaleText: String = "",
  onAllowPermission: () -> Unit = {},
) {
  var shouldShowDialog by remember { mutableStateOf(true) }

  if (shouldShowDialog) {
    RationaleDialog(
      title = rationaleTitle,
      text = rationaleText,
      positiveText = positiveText,
      negativeText = negativeText,
      onPositive = {
        shouldShowDialog = false
      },
      onNegative = {
        onAllowPermission()
      },
      onDismissRequest = {
        shouldShowDialog = false
      },
    )
  }
}

@Preview(device = Devices.PIXEL_4_XL, showBackground = true)
@Composable
private fun RequestPermissionPreview() {
  RequestPermissionDialog(
    positiveText = "Allow",
    negativeText = "Deny",
    rationaleTitle = "Camera permission",
    rationaleText = "We need camera permission to scan QR codes",
    onAllowPermission = {},
  )
}
