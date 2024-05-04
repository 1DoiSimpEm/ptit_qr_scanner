package ptit.vietpq.qr_scanner.presentation.history

import androidx.activity.ComponentActivity
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarCodeResult
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.customize_qr.ShapeModel
import ptit.vietpq.qr_scanner.presentation.multicodes.ConfirmDeleteDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.internal.immutableListOf
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale
import ptit.vietpq.qr_scanner.extension.EMPTY
import ptit.vietpq.qr_scanner.presentation.create_result.CreateQrResultViewModel
import ptit.vietpq.qr_scanner.presentation.history.component.DeletedSuccessfullyToast
import ptit.vietpq.qr_scanner.presentation.history.component.ItemHistoryScan
import ptit.vietpq.qr_scanner.presentation.history.component.OptionBottomSheet

/**
 * A composable function that defines the scan route of the application.
 */
@Composable
internal fun HistoryRoute(
  onItemClicked: (Boolean, String) -> Unit,
  onItemLongClicked: (Boolean) -> Unit,
  onScanNowClicked: (Boolean) -> Unit,
  viewModel: HistoryViewModel = hiltViewModel(),
  sharedResultViewModel: CreateQrResultViewModel = viewModel(LocalContext.current as ComponentActivity),
) {
  val uiState: HistoryUiState by viewModel.uiState.collectAsStateWithLifecycle()
  viewModel.uiFlow.collectAsStateWithLifecycle()

  HistoryScreen(
    uiState = uiState,
    onItemClicked = { isCreated, data ->
      val result = if (isCreated) {
        sharedResultViewModel.saveCustomQrCode(
          shape = ShapeModel.default,
          foregroundColor = Color.Black,
          backgroundColor = Color.White,
        )
        sharedResultViewModel.onTypeChanged(data)
        String.EMPTY
      } else {
        viewModel.getNavigateValue(data)
      }
        onItemClicked(isCreated, result)
    },
    onScanNowClicked = {
      onScanNowClicked.invoke(it)
    },
    onItemLongClicked = { onItemLongClicked(it.isGenerated) },
    onAllDeleted = remember { viewModel::deleteAllHistory },
    onItemDeleted = remember { viewModel::deleteHistory },
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
  onItemClicked: (Boolean, BarCodeResult) -> Unit,
  onItemLongClicked: (BarCodeResult) -> Unit,
  onAllDeleted: (Boolean) -> Unit,
  onItemDeleted: (BarCodeResult) -> Unit,
  onScanNowClicked: (Boolean) -> Unit,
  uiState: HistoryUiState,
  modifier: Modifier = Modifier,
) {
  val titles =
    immutableListOf(QrLocale.strings.scan, QrLocale.strings.create)
  var selectedTabIndex by remember {
    mutableIntStateOf(0)
  }
  val currentList = if (selectedTabIndex == 0) uiState.scannedHistoryList else uiState.createdHistoryList
  var showDeleteDialog by remember { mutableStateOf(false) }
  var showDeleteAllDialog by remember { mutableStateOf(false) }
  var isBottomSheetVisible by remember { mutableStateOf(false) }
  val sheetState = rememberModalBottomSheetState(
    skipPartiallyExpanded = true,
  )
  var selectedItem by remember { mutableStateOf(BarCodeResult.INITIAL) }
  var showToast by remember { mutableStateOf(false) }
  val scope = rememberCoroutineScope()

  OptionBottomSheet(
    isBottomSheetVisible = isBottomSheetVisible,
    sheetState = sheetState,
    onDeleteClicked = {
      showDeleteDialog = true
      scope.launch {
        sheetState.hide()
      }.invokeOnCompletion {
        isBottomSheetVisible = false
      }
    },
    onDismissRequest = {
      scope.launch {
        sheetState.hide()
      }.invokeOnCompletion {
        isBottomSheetVisible = false
      }
    },
  )
  if (showDeleteAllDialog || showDeleteDialog) {
    ConfirmDeleteDialog(
      title = QrLocale.strings.confirmDelete,
      text = QrLocale.strings.areYouSureWantToDelete,
      positiveText = QrLocale.strings.cancel,
      negativeText = QrLocale.strings.delete,
      onPositive = {
        showDeleteDialog = false
        showDeleteAllDialog = false
      },
      onNegative = {
        if (showDeleteDialog) {
          onItemDeleted(selectedItem)
        }
        if (showDeleteAllDialog) {
          onAllDeleted(selectedTabIndex == 1)
        }
        showDeleteAllDialog = false
        showDeleteDialog = false
        showToast = true
      },
    )
  }

  LaunchedEffect(showToast) {
    if (showToast) {
      delay(2000)
      showToast = false
    }
  }

  Scaffold(
    contentColor = QrCodeTheme.color.primary,
    containerColor = QrCodeTheme.color.backgroundCard,
    modifier = modifier,
    topBar = {
      TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = QrCodeTheme.color.backgroundCard,
          titleContentColor = QrCodeTheme.color.neutral1,
        ),
        title = {
          Text(
            text = QrLocale.strings.history,
            color = QrCodeTheme.color.neutral1,
            style = QrCodeTheme.typo.innerBoldSize20LineHeight28,
          )
        },
        actions = {
          Row(horizontalArrangement = Arrangement.End) {
            IconButton(
              onClick = {
                if (currentList.isNotEmpty()) {
                  showDeleteAllDialog = true
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
          .fillMaxSize()
          .padding(
            top = innerPadding.calculateTopPadding(),
            start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
            end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
          ),
      ) {
        Column(
          modifier = Modifier
            .fillMaxSize()
            .align(Alignment.TopStart),
        ) {
          TabRow(
            modifier = Modifier
              .padding(
                start = QrCodeTheme.dimen.marginDefault,
                end = QrCodeTheme.dimen.marginDefault,
                bottom = QrCodeTheme.dimen.marginDefault,
              )
              .clip(QrCodeTheme.shape.roundedSuperLarge),
            indicator = {},
            divider = {},
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Transparent,
          ) {
            titles.forEachIndexed { index, title ->
              val isSelected = selectedTabIndex == index
              Tab(
                modifier = Modifier
                  .padding(end = if (index == 0) 8.dp else 0.dp)
                  .clip(QrCodeTheme.shape.roundedSuperLarge)
                  .background(if (isSelected) QrCodeTheme.color.primary else QrCodeTheme.color.neutral5),
                text = {
                  Crossfade(targetState = isSelected, label = "") { isTabSelected ->
                    Text(
                      text = title,
                      style = QrCodeTheme.typo.body,
                      color = if (isTabSelected) QrCodeTheme.color.neutral5 else QrCodeTheme.color.neutral1,
                    )
                  }
                },
                selected = isSelected,
                onClick = {
                  selectedTabIndex = index
                },
                selectedContentColor = QrCodeTheme.color.primary,
                unselectedContentColor = QrCodeTheme.color.neutral5,
              )
            }
          }
          if (currentList.isEmpty()) {
            NoHistory(
              text = if (selectedTabIndex == 0) QrLocale.strings.scanNow else QrLocale.strings.createNow,
              onScanNowClicked = { onScanNowClicked(selectedTabIndex == 1) },
            )
          } else {
            LazyColumn {
              items(
                count = currentList.size,
                key = { index -> currentList[index].id },
              ) { index ->

                ItemHistoryScan(
                  item = currentList[index],
                  onItemClicked = {
                    onItemClicked(selectedTabIndex == 1, it)
                  },
                  onOptionClicked = {
                    selectedItem = it
                    isBottomSheetVisible = true
                  },
                  onItemLongClicked = onItemLongClicked,
                )
              }
            }
          }
        }
        DeletedSuccessfullyToast(
          isShow = showToast,
          modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 16.dp),
        )
      }
    },
  )
}

@Composable
private fun NoHistory(text: String, modifier: Modifier = Modifier, onScanNowClicked: () -> Unit) {
  Box(
    modifier = modifier.fillMaxSize(),
    contentAlignment = Alignment.Center,
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Image(
        painter = painterResource(id = R.drawable.ic_no_history),
        contentDescription = null,
      )
      Spacer(modifier = Modifier.size(16.dp))
      Text(
        text = QrLocale.strings.noHistory,
        style = QrCodeTheme.typo.body,
        color = QrCodeTheme.color.neutral3,
      )
      Spacer(modifier = Modifier.size(16.dp))
      Button(
        modifier = Modifier.size(200.dp, 48.dp),
        onClick = onScanNowClicked,
      ) {
        Text(text = text, style = QrCodeTheme.typo.button, color = QrCodeTheme.color.neutral5)
      }
    }
  }
}
