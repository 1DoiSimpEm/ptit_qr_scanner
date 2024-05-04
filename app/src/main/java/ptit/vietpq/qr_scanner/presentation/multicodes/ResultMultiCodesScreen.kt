package ptit.vietpq.qr_scanner.presentation.multicodes

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarCodeResult
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarcodeFormat
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarcodeSchema
import ptit.vietpq.qr_scanner.presentation.scan.ScanUiState
import ptit.vietpq.qr_scanner.presentation.scan.ScanViewModel
import ptit.vietpq.qr_scanner.presentation.scan.component.CameraAction
import ptit.vietpq.qr_scanner.presentation.scan.component.TabMode
import kotlinx.collections.immutable.persistentListOf
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale

private const val NUMBER_CODES = 2

@Composable
internal fun MultiCodesRoute(viewModel: ScanViewModel, onBackPress: () -> Unit, onNavigationResult: (String) -> Unit) {
  val uiScanState: ScanUiState by viewModel.scanUiState.collectAsStateWithLifecycle()
  val currentOnNavResult by rememberUpdatedState(newValue = onNavigationResult)
  val context = LocalContext.current

  LaunchedEffect(key1 = Unit) {
    viewModel.loadAdsMultiCode()
  }

  ResultMultiModeScan(
    modifier = Modifier,
    uiState = uiScanState,
    onBackPress = {
      viewModel.unSelectedAllBarCode()
      onBackPress()
    },
    onDeleteTrack = {},
    onClickSelectedDelete = remember { viewModel::selectedBarCode },
    onClickSelectedAll = remember { viewModel::selectAllBarCode },
    onConfirmDeleted = remember { viewModel::removeSelectedBarCode },
    onClickItemResult = remember { viewModel::showResultEvent },
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultMultiModeScan(
    uiState: ScanUiState,
    modifier: Modifier = Modifier,
    onBackPress: () -> Unit = {},
    onClickSelectedDelete: (BarCodeResult) -> Unit = {},
    onClickItemResult: (BarCodeResult) -> Unit = {},
    onClickSelectedAll: () -> Unit = {},
    onConfirmDeleted: () -> Unit = {},
    onDeleteTrack: () -> Unit = {},
) {
  val isDeleteMode = remember { mutableStateOf(false) }
  var showDialogLeave: Boolean by remember { mutableStateOf(false) }
  var showDialogConfirm: Boolean by remember { mutableStateOf(false) }

  BackHandler {
    showDialogLeave = true
  }

  Scaffold(
    contentColor = QrCodeTheme.color.primary,
    containerColor = QrCodeTheme.color.primary,
    modifier = modifier,
    topBar = {
      TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = QrCodeTheme.color.backgroundCard,
          titleContentColor = QrCodeTheme.color.neutral1,
        ),
        navigationIcon = {
          Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "Back",
            modifier = Modifier
              .padding(vertical = 16.dp, horizontal = 16.dp)
              .clickable {
                showDialogLeave = true
              },
          )
        },
        title = {
          Text(
            text = if (isDeleteMode.value) {
              stringResource(R.string.selected_item_delete, uiState.barCodePresent.count { it.isSelected })
            } else {
              stringResource(R.string.codes_number_title, uiState.barCodePresent.size)
            },
            color = QrCodeTheme.color.neutral1,
            style = QrCodeTheme.typo.innerBoldSize20LineHeight28,
          )
        },
        actions = {
          Row(horizontalArrangement = Arrangement.End) {
            if (isDeleteMode.value) {
              IconButton(
                onClick = {
                  onClickSelectedAll()
                },
              ) {
                Icon(painterResource(id = R.drawable.ic_box_select_all), contentDescription = null)
              }
            }
            IconButton(
              onClick = {
                if (uiState.anyItemSelected) {
                  showDialogConfirm = true
                } else {
                  isDeleteMode.value = !isDeleteMode.value
                }
              },
            ) {
              Icon(
                painterResource(R.drawable.ic_trash_selected),
                contentDescription = null,
              )
            }
          }
        },
      )
    },
    content = { innerPadding ->
      Box(
        modifier = Modifier
          .padding(
            top = innerPadding.calculateTopPadding(),
            start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
            end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
          )
          .background(color = QrCodeTheme.color.backgroundCard)
          .fillMaxWidth()
          .fillMaxSize(),
      ) {
        Column {
          LazyColumn(
            modifier = Modifier.weight(1f),
          ) {
            items(
              count = uiState.barCodePresent.size,
              key = { index -> uiState.barCodePresent[index].identifyBarCodeResult },
            ) { index ->
              // Show native ads in here when the index is 2 and the current list size is more than 2
              if (uiState.barCodePresent.size > 2 && index == 2) {
                if (uiState.adsViewMultiCode != null) {
                  AndroidView(
                    factory = { uiState.adsViewMultiCode.value },
                    modifier = remember {
                      Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                    },
                  )
                }
              }

              ItemRowBarCode(
                item = uiState.barCodePresent[index],
                modeSelected = isDeleteMode.value,
                onClickSelectedDelete = {
                  if (isDeleteMode.value) {
                    onClickSelectedDelete(it)
                  } else {
                    onClickItemResult(it)
                  }
                },
              )
            }
          }
        }

        if (showDialogConfirm) {
          val selectedCount = uiState.barCodePresent.count { it.isSelected }
          val textCount = if (selectedCount > NUMBER_CODES) {
            QrLocale.strings.codes
          } else {
            QrLocale.strings.code
          }
          ConfirmDeleteDialog(
            onPositive = {
              showDialogConfirm = false
            },
            onDismissRequest = {
              showDialogConfirm = false
            },
            onNegative = {
              onDeleteTrack()
              onConfirmDeleted()
            },
            title = stringResource(R.string.confirm_delete),
            text = stringResource(R.string.want_to_delete_this, selectedCount, textCount),
            positiveText = QrLocale.strings.commonCancel,
            negativeText = QrLocale.strings.delete,
          )
        }

        if (showDialogLeave) {
          ConfirmLeaveDialog(
            title = QrLocale.strings.confirmLeave,
            text = QrLocale.strings.confirmDeleteTitle,
            positiveText = QrLocale.strings.commonCancel,
            negativeText = QrLocale.strings.leave,
            onPositive = {
              showDialogLeave = false
            },
            onNegative = {
              onBackPress()
            },
            onDismissRequest = { showDialogLeave = false },
          )
        }
      }
    },
  )
}

@Composable
@Preview
private fun ItemRowBarCodePreview() {
  ResultMultiModeScan(
    uiState = ScanUiState(
      isLoading = false,
      tabSelected = TabMode.MULTIPLE,
      isFlashOn = false,
      showGuideLine = false,
      cameraAction = CameraAction.None,
      retry = false,
      isLoadingAds = false,
      barCodePresent = persistentListOf(
        BarCodeResult(
          text = "mel",
          formattedText = "ornatus",
          format = BarcodeFormat.FORMAT_CODABAR,
          typeSchema = BarcodeSchema.OTHER,
          date = 4582,
          isGenerated = false,
          isFavorite = false,
          isSelected = false,
        ),
        BarCodeResult(
          text = "mel",
          formattedText = "ornatus",
          format = BarcodeFormat.FORMAT_CODABAR,
          typeSchema = BarcodeSchema.OTHER,
          date = 4582,
          isGenerated = false,
          isFavorite = false,
          isSelected = false,
        ),
      ),
    ),
  )
}
