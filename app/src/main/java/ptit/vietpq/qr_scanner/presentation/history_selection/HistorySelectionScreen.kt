package ptit.vietpq.qr_scanner.presentation.history_selection

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarCodeResult
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.presentation.main.QrAppViewModel
import ptit.vietpq.qr_scanner.presentation.multicodes.ConfirmDeleteDialog
import ptit.vietpq.qr_scanner.presentation.multicodes.ItemRowBarCode
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale

private const val NUMBER_CODES = 2

@Composable
internal fun HistorySelectionRoute(
  mainViewModel: QrAppViewModel,
  onBackPress: () -> Unit,
  viewModel: HistorySelectionViewModel = hiltViewModel(),
) {
  val uiState: HistorySelectionUiState by viewModel.uiState.collectAsStateWithLifecycle()


  viewModel.uiFlow.collectAsStateWithLifecycle()



  HistorySelectionScreen(
    modifier = Modifier,
    uiState = uiState,
    onBackPressed = {
        onBackPress()
    },
    onClickSelectedDelete = remember { viewModel::selectedBarCode },
    onClickSelectedAll = remember { viewModel::selectAllBarCode },
    onConfirmDeleted = remember { viewModel::deleteHistories },
    onClickDeselectAll = remember { viewModel::deselectAllBarCode },
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorySelectionScreen(
  uiState: HistorySelectionUiState,
  modifier: Modifier = Modifier,
  onBackPressed: () -> Unit = {},
  onClickSelectedDelete: (BarCodeResult) -> Unit = {},
  onClickSelectedAll: () -> Unit = {},
  onClickDeselectAll: () -> Unit = {},
  onConfirmDeleted: () -> Unit = {},
) {
  var showDialogConfirm: Boolean by remember { mutableStateOf(false) }

  BackHandler {
    onBackPressed.invoke()
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
        navigationIcon = {
          Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "Back",
            modifier = Modifier
              .padding(vertical = 16.dp, horizontal = 16.dp)
              .clickable {
                onBackPressed()
              },
          )
        },
        title = {
          Text(
            text = stringResource(R.string.selected_item_delete, uiState.listHistory.count { it.isSelected }),
            color = QrCodeTheme.color.neutral1,
            style = QrCodeTheme.typo.innerBoldSize20LineHeight28,
          )
        },
        actions = {
          Row(horizontalArrangement = Arrangement.End) {
            IconButton(
              onClick = {
                if (uiState.listHistory.any { it.isSelected }) {
                  onClickDeselectAll()
                } else {
                  onClickSelectedAll()
                }
              },
            ) {
              Image(
                painterResource(
                  id = if (uiState.listHistory.size == uiState.listHistory.count { it.isSelected }) {
                    R.drawable.ic_box_checked
                  } else {
                    R.drawable.ic_box_select_all
                  },
                ),
                contentDescription = null,
              )
            }
            IconButton(
              onClick = {
                if (uiState.listHistory.any { it.isSelected }) {
                  showDialogConfirm = true
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
        LazyColumn {
          items(
            count = uiState.listHistory.size,
            key = { index -> uiState.listHistory[index].date },
          ) { index ->
            ItemRowBarCode(
              item = uiState.listHistory[index],
              modeSelected = true,
              onClickSelectedDelete = {
                onClickSelectedDelete(it)
              },
            )
          }
        }

        if (showDialogConfirm) {
          val selectedCount = uiState.listHistory.count { it.isSelected }
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
              onConfirmDeleted()
            },
            title = stringResource(R.string.confirm_delete),
            text = stringResource(R.string.want_to_delete_this, selectedCount, textCount),
            positiveText = QrLocale.strings.commonCancel,
            negativeText = QrLocale.strings.delete,
          )
        }
      }
    },
  )
}
