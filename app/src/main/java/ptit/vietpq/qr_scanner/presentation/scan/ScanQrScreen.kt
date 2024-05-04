package ptit.vietpq.qr_scanner.presentation.scan

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.Intent.ACTION_OPEN_DOCUMENT
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.mlkit.vision.barcode.common.Barcode
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale
import ptit.vietpq.qr_scanner.extension.settingsIntent
import ptit.vietpq.qr_scanner.presentation.multicodes.MultiScanResult
import ptit.vietpq.qr_scanner.presentation.scan.component.CameraScan
import ptit.vietpq.qr_scanner.presentation.scan.component.PermissionDeniedForwardSetting
import ptit.vietpq.qr_scanner.presentation.scan.component.QrScannerOverlayAnim
import ptit.vietpq.qr_scanner.presentation.scan.component.ScanModeCamera
import ptit.vietpq.qr_scanner.presentation.scan.component.TabMode
import ptit.vietpq.qr_scanner.ui.RequestPermissionDialog
import ptit.vietpq.qr_scanner.utils.CollectWithLifecycleEffect

/**
 * A composable function that defines the scan route of the application.
 */
@Composable
internal fun ScanRoute(
  onNavigationResult: (String) -> Unit,
  onShowTabMultiCodes: () -> Unit,
  onImagePick: () -> Unit,
  showBottomSheet: (show: Boolean) -> Unit,
  showExitBottomSheet: () -> Unit,
  viewModel: ScanViewModel,
) {
  val uiScanState: ScanUiState by viewModel.scanUiState.collectAsStateWithLifecycle()
  val context = LocalContext.current

  val currentOnNavResult by rememberUpdatedState(newValue = onNavigationResult)
  val currentOnExitApp by rememberUpdatedState(newValue = showExitBottomSheet)

  val activity = LocalContext.current as Activity


  viewModel.eventFlow.CollectWithLifecycleEffect { event ->
    when (event) {
      is ScanQrEvent.OnQrScanned -> {
        currentOnNavResult(event.qrBarModeString)
      }

      ScanQrEvent.OnQrScannedError -> {
        Toast.makeText(context, context.getString(R.string.no_qr_code_barcode_found), Toast.LENGTH_SHORT).show()
      }

      is ScanQrEvent.DeleteSuccess -> {
        Toast.makeText(context, context.getString(R.string.deleted_successfully), Toast.LENGTH_SHORT).show()
      }

      is ScanQrEvent.ShowBottomBarEvent -> {
//        showBottomSheet(event.isShow)
      }

      is ScanQrEvent.ShowInterAds -> {
        currentOnNavResult(event.qrBarModeString)
      }
    }
  }
  ScanQrScreen(
    modifier = Modifier,
    uiState = uiScanState,
    onNavigationResultSingle = remember {
      viewModel::resultScanModeSingle
    },
    onNavigationResultMultiMode = remember {
      viewModel::resultScanMultiMode
    },
    onClickForwardSettings = { context.startActivity(context.settingsIntent) },
    onModeScanClick = remember { viewModel::selectTabMode },
//    onPickImage = remember { viewModel::pickImage },
    onPickImage = { onImagePick() },
    onDismissGuideLine = remember { viewModel::saveShowGuideLine },
    onToggleFlashLight = remember { viewModel::toggleFlashLight },
    onTabShowMultiCodes = onShowTabMultiCodes,
    showBottomSheet = remember { viewModel::showBottomBar },
    showExitBottomSheet = { currentOnExitApp() },
  )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScanQrScreen(
  uiState: ScanUiState,
  onNavigationResultSingle: (barCode: Barcode, imageRaw: Bitmap) -> Unit,
  onNavigationResultMultiMode: (barCode: Barcode) -> Unit,
  onModeScanClick: (mode: TabMode) -> Unit,
  onClickForwardSettings: () -> Unit,
  onPickImage: (uri: Uri) -> Unit,
  onDismissGuideLine: (Boolean) -> Unit,
  onTabShowMultiCodes: () -> Unit,
  onToggleFlashLight: () -> Unit,
  showBottomSheet: (show: Boolean) -> Unit,
  showExitBottomSheet: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val context = LocalContext.current
  BackHandler {
    showExitBottomSheet()
  }

  val cameraPermissionState: PermissionState = rememberPermissionState(Manifest.permission.CAMERA)
  val imagePermissionState: PermissionState = rememberPermissionState(
    if (Build.VERSION_CODES.TIRAMISU <= Build.VERSION.SDK_INT) {
      Manifest.permission.READ_MEDIA_IMAGES
    } else {
      Manifest.permission.READ_EXTERNAL_STORAGE
    },
  )
  val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
    val uri = result.data?.data ?: return@rememberLauncherForActivityResult
    onPickImage(uri)
  }

  // Launch the permission request when the cameraPermissionState changes
  LaunchedEffect(Unit) {
    if (cameraPermissionState.status is PermissionStatus.Denied) {
      cameraPermissionState.launchPermissionRequest()
    }
  }

  when(imagePermissionState.status){
    is PermissionStatus.Granted -> {
      onPickImage(Uri.EMPTY)
    }

    is PermissionStatus.Denied -> {
      TODO()
    }
  }

  BackHandler {
    showExitBottomSheet()
  }

  Box(
    modifier = modifier
      .fillMaxHeight()
      .fillMaxWidth(),
  ) {
    CameraScan(
      tabMode = uiState.tabSelected,
      isFlashOn = uiState.isFlashOn,
      cameraAction = uiState.cameraAction,
      onBarCodeResultSingle = { barCode, imageRaw ->
        onNavigationResultSingle.invoke(barCode, imageRaw)
      },
      onBarCodeResultMulti = {
        onNavigationResultMultiMode(it)
      },
      retry = uiState.retry,
    )

    Column(
      modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth(),
      verticalArrangement = Arrangement.Top,
    ) {
      if (uiState.hasContentMultiMode) {
        MultiScanResult(
          barCodePresent = uiState.barCodePresent,
          onTabShowMultiCodes = onTabShowMultiCodes,
          modifier = Modifier.padding(start = 16.dp),
        )
      }
    }

    ScanCameraActions(
      cameraPermissionGrand = cameraPermissionState.status.isGranted,
      shouldShowRationale = cameraPermissionState.status.shouldShowRationale,
      onModeScanClick = onModeScanClick,
      isFlashOn = uiState.isFlashOn,
      tabSelected = uiState.tabSelected,
      onClickRequestPermission = cameraPermissionState::launchPermissionRequest,
      onClickForwardSettings = onClickForwardSettings,
      onClickFlashLight = onToggleFlashLight,
      onPickImage = {
        val intent = Intent(ACTION_OPEN_DOCUMENT).apply {
          val mimeTypes = arrayOf("image/png", "image/jpg", "image/jpeg")
          type = "*/*"
          putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
          addCategory(Intent.CATEGORY_OPENABLE)
          addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        pickMedia.launch(intent)
      },
    )
  }
}

@Composable
private fun ScanCameraActions(
  cameraPermissionGrand: Boolean,
  shouldShowRationale: Boolean,
  onModeScanClick: (mode: TabMode) -> Unit,
  isFlashOn: Boolean,
  tabSelected: TabMode,
  onClickForwardSettings: () -> Unit,
  onClickRequestPermission: () -> Unit,
  onClickFlashLight: () -> Unit,
  onPickImage: () -> Unit,
) {
  if (!cameraPermissionGrand) {
    if (shouldShowRationale) {
      RequestPermissionDialog(
        positiveText = QrLocale.strings.cameraDeny,
        negativeText = QrLocale.strings.cameraAllow,
        rationaleTitle = QrLocale.strings.whyNeedCamera,
        rationaleText = QrLocale.strings.cameraNeedToLaunch,
        onAllowPermission = onClickRequestPermission,
      )
    }
    PermissionDeniedForwardSetting(
      onClickForwardSettings = onClickForwardSettings,
    )
  } else {
    Box(modifier = Modifier.fillMaxSize()) {
      QrScannerOverlayAnim(
        modifier = Modifier
          .size(280.dp)
          .align(Alignment.Center),
      )
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(bottom = 30.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        ScanModeCamera(
          onTabClick = onModeScanClick,
          isFlashOn = isFlashOn,
          onPickImageClick = onPickImage,
          tabSelected = tabSelected,
          onFlashLightClick = onClickFlashLight,
        )
      }
    }
  }
}
