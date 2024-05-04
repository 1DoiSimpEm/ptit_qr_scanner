package ptit.vietpq.qr_scanner.presentation.scan

import androidx.compose.runtime.Immutable
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarCodeResult
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import ptit.vietpq.qr_scanner.presentation.scan.component.CameraAction
import ptit.vietpq.qr_scanner.presentation.scan.component.TabMode

@Immutable
data class ScanUiState(
    val isLoading: Boolean,
    val isLoadingAds: Boolean,
    val tabSelected: TabMode,
    val isFlashOn: Boolean,
    val showGuideLine: Boolean,
    val cameraAction: CameraAction,
    val retry: Boolean = false,
    val barCodePresent: ImmutableList<BarCodeResult>,
    val isSelectAllMode: Boolean = false,
) {
  companion object {
    val initial = ScanUiState(
      isLoading = false,
      tabSelected = TabMode.SINGLE,
      isFlashOn = false,
      showGuideLine = true,
      retry = false,
      barCodePresent = persistentListOf(),
      cameraAction = CameraAction.None,
      isLoadingAds = false,
    )
  }

  val hasContentMultiMode
    get() = barCodePresent.isNotEmpty() && tabSelected == TabMode.MULTIPLE

  val anyItemSelected get() = barCodePresent.any { it.isSelected }
}
