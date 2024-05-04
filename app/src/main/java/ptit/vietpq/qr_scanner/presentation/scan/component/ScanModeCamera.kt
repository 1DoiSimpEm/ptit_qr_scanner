package ptit.vietpq.qr_scanner.presentation.scan.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale

@Composable
fun ScanModeCamera(
    onTabClick: (tab: TabMode) -> Unit,
    onPickImageClick: () -> Unit,
    onFlashLightClick: () -> Unit,
    isFlashOn: Boolean,
    tabSelected: TabMode,
    modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
  ) {
    Icon(
      modifier = Modifier
        .size(44.dp)
        .clickable {
          onPickImageClick()
        },
      imageVector = ImageVector.vectorResource(id = R.drawable.ic_pick_gallery),
      contentDescription = "Some icon",
      tint = Color.Unspecified,
    )

    Spacer(modifier = Modifier.width(width = 16.dp))

    TabSelectMode(
      tabHolder = persistentListOf(QrLocale.strings.single, QrLocale.strings.batch),
      onItemSelect = onTabClick,
      selectedTab = tabSelected,
      modifier = Modifier.fillMaxWidth(0.7f),
    )

    Spacer(modifier = Modifier.width(width = 16.dp))

    Image(
      modifier = Modifier
        .size(44.dp)
        .clickable {
          onFlashLightClick()
        },
      imageVector = if (isFlashOn) {
        ImageVector.vectorResource(
          id = R.drawable.ic_flash_on,
        )
      } else {
        ImageVector.vectorResource(id = R.drawable.ic_flashlight_off)
      },
      contentDescription = "Some icon",
    )
  }
}

@Preview
@Composable
private fun ScanModeCameraPreview() {
  ScanModeCamera(
    modifier = Modifier,
    onTabClick = {},
    onPickImageClick = {},
    isFlashOn = false,
    tabSelected = TabMode.SINGLE,
    onFlashLightClick = {},
  )
}
