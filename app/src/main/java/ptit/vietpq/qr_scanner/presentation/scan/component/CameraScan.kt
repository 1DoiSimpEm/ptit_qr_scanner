package ptit.vietpq.qr_scanner.presentation.scan.component

import android.graphics.Bitmap
import android.os.Build
import android.util.Size
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.core.resolutionselector.ResolutionStrategy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.common.Barcode
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlinx.coroutines.delay
import ptit.vietpq.qr_scanner.R
import timber.log.Timber

fun CameraSelector.toggleSwitchCamera() = when (this) {
  CameraSelector.DEFAULT_BACK_CAMERA -> CameraSelector.DEFAULT_FRONT_CAMERA
  else -> CameraSelector.DEFAULT_BACK_CAMERA
}

enum class CameraAction {
  Click,
  SwitchCamera,
  None,
}

@Composable
fun CameraScan(
  onBarCodeResultSingle: (barCode: Barcode, imageRaw: Bitmap) -> Unit,
  onBarCodeResultMulti: (barCode: Barcode) -> Unit,
  tabMode: TabMode,
  retry: Boolean,
  cameraAction: CameraAction,
  isFlashOn: Boolean,
  modifier: Modifier = Modifier,
) {
  val context = LocalContext.current
  var showScanner by remember { mutableStateOf(false) }
  val isBelowAndroid8 = Build.VERSION.SDK_INT <= 26
  var cameraSelector by remember { mutableStateOf(CameraSelector.DEFAULT_BACK_CAMERA) }
  val lifecycleOwner = LocalLifecycleOwner.current
  var camera by remember { mutableStateOf<Camera?>(null) }
  val previewView: PreviewView = remember {
    PreviewView(context).apply {
      layoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT,
      )
    }
  }

  // Create an executor that will be used to run the analysis use case
  val updateStateSelectMode by rememberUpdatedState(newValue = tabMode)
  val retryState by rememberUpdatedState(newValue = retry)
  var imageAnalysis by remember { mutableStateOf<ImageAnalysis?>(null) }

// Workaround for https://issuetracker.google.com/issues/285336815
  LaunchedEffect(Unit) {
    if (isBelowAndroid8) {
      delay(200)
    } else {
      withFrameMillis { }
    }
    // Start the camera
    showScanner = true
  }

  LaunchedEffect(isFlashOn) {
    camera?.let {
      if (it.cameraInfo.hasFlashUnit()) {
        it.cameraControl.enableTorch(isFlashOn)
      } else {
        Toast.makeText(context, context.getString(R.string.camera_flash_not_supported), Toast.LENGTH_SHORT).show()
      }
    }
  }

  LaunchedEffect(key1 = tabMode, key2 = retryState) {
    imageAnalysis = ImageAnalysis.Builder()
      .setResolutionSelector(
        ResolutionSelector.Builder().setResolutionStrategy(
          ResolutionStrategy(
            Size(1280, 720),
            ResolutionStrategy.FALLBACK_RULE_CLOSEST_HIGHER_THEN_LOWER,
          ),
        ).build(),
      )
      .build()
      .also {
        it.setAnalyzer(
          ContextCompat.getMainExecutor(context),
          QRCodeAnalyzer(
            onSuccess = { barcode, bitmap: Bitmap ->
              if (updateStateSelectMode == TabMode.SINGLE) {
                onBarCodeResultSingle(barcode, bitmap)
                it.clearAnalyzer()
              } else {
                onBarCodeResultMulti(barcode)
              }
              Timber.tag("ScanQrScreen").d("TabMode: $tabMode")
            },
            onFailure = { exception ->
              Timber.tag("ScanQrScreen").e(exception, "Failed to scan QR code")
            },
            onPassCompleted = { failureOccurred: Boolean ->
              Timber.tag("ScanQrScreen").d("Failure occurred: $failureOccurred")
            },
          ),
        )
      }
  }

  DisposableEffect(key1 = cameraAction) {
    when (cameraAction) {
      CameraAction.Click -> {
      }

      CameraAction.SwitchCamera -> {
        cameraSelector = cameraSelector.toggleSwitchCamera()
      }

      CameraAction.None -> {
      }
    }

    onDispose {
      // Dispose of the camera
    }
  }

  if (showScanner) {
    LaunchedEffect(key1 = cameraSelector, key2 = lifecycleOwner, key3 = retryState) {
      // Initialize the camera provider
      val cameraProvider: ProcessCameraProvider = suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(context).also { future ->
          future.addListener(
            {
              continuation.resume(future.get())
            },
            ContextCompat.getMainExecutor(context),
          )
        }
      }

      // Set up the view use case to display the camera preview
      val previewUseCase: Preview by lazy(LazyThreadSafetyMode.NONE) {
        Preview.Builder()
          .build()
          .also { it.setSurfaceProvider(previewView.surfaceProvider) }
      }

      // Bind the camera use cases to the lifecycle of the view
      runCatching {
        cameraProvider.unbindAll()
        camera = cameraProvider.bindToLifecycle(
          lifecycleOwner,
          cameraSelector,
          previewUseCase,
          imageAnalysis,
        )
      }
    }
  }

  AndroidView(
    modifier = modifier
      .fillMaxHeight()
      .fillMaxWidth(),
    factory = { previewView },
  )
}
