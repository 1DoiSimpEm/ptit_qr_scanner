package ptit.vietpq.qr_scanner.presentation.create

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ptit.vietpq.qr_scanner.presentation.create_detail.CreateQrType
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale
import ptit.vietpq.qr_scanner.utils.CollectWithLifecycleEffect

@Composable
internal fun CreateRoute(onItemClicked: (CreateQrType) -> Unit, viewModel: CreateViewModel = hiltViewModel()) {
  val uiState: CreateUiState by viewModel.uiState.collectAsStateWithLifecycle()

  CreateScreen(
    uiState = uiState,
    onItemClicked = {
      onItemClicked(it)
    },
  )
}

@Composable
fun CreateScreen(uiState: CreateUiState, onItemClicked: (CreateQrType) -> Unit, modifier: Modifier = Modifier) {
  Box(
    modifier = modifier
      .fillMaxSize()
      .background(QrCodeTheme.color.backgroundCard),
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(
          top = QrCodeTheme.dimen.marginDefault,
          start = QrCodeTheme.dimen.marginDefault,
          end = QrCodeTheme.dimen.marginDefault,
        ),
      verticalArrangement = Arrangement.SpaceEvenly,
      horizontalAlignment = Alignment.Start,
    ) {
      Text(
        text = QrLocale.strings.createQrCode,
        color = QrCodeTheme.color.neutral1,
        style = QrCodeTheme.typo.heading,
      )
      CreateQRCodeContent(
        uiState = uiState,
        modifier = Modifier
          .padding(top = QrCodeTheme.dimen.marginDefault),
      ) {
        onItemClicked.invoke(it)
      }
    }
  }
}

@Composable
fun CreateQRCodeContent(uiState: CreateUiState, modifier: Modifier = Modifier, onItemClicked: (CreateQrType) -> Unit) {
  val cardColor = CardDefaults.cardColors(
    containerColor = QrCodeTheme.color.neutral5,
    contentColor = Color.Transparent,
    disabledContainerColor = Color.Transparent,
    disabledContentColor = Color.Transparent,
  )
  LazyColumn(modifier = modifier) {
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = QrCodeTheme.dimen.marginPrettySmall),
        shape = QrCodeTheme.shape.roundedLarge,
        colors = cardColor,
      ) {
        HorizontalCardItem(item = uiState.data[0]) {
          onItemClicked.invoke(it)
        }
      }
    }

    item {
      Title(title = QrLocale.strings.personal)
    }

    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = QrCodeTheme.dimen.marginPrettySmall),
        shape = QrCodeTheme.shape.roundedLarge,
        colors = cardColor,
      ) {
        Column {
          repeat(6) { index ->
            if (index == 2) {
              Column {
                Box(
                  modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(
                      start = QrCodeTheme.dimen.qrTypeImageSize,
                      end = QrCodeTheme.dimen.marginDefault,
                    )
                    .background(QrCodeTheme.color.backgroundCard),
                )
              }
            } else {
              HorizontalCardItem(item = uiState.data[index + 1]) {
                onItemClicked.invoke(it)
              }
            }
          }
        }
      }
    }

    item {
      Title(title = QrLocale.strings.social)
    }
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = QrCodeTheme.dimen.marginPrettySmall),
        shape = QrCodeTheme.shape.roundedLarge,
        colors = cardColor,
      ) {
        Column {
          repeat(2) { columnIndex ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(QrCodeTheme.dimen.marginDefault),
              horizontalArrangement = Arrangement.SpaceBetween,
            ) {
              repeat(4) { rowIndex ->
                VerticalCardItem(
                  item = uiState.data[8 + (columnIndex * 4) + rowIndex - 1],
                ) {
                  onItemClicked.invoke(it)
                }
              }
            }
          }
        }
      }
    }
    item {
      Title(
        title = QrLocale.strings.utilities,
      )
    }

    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = QrCodeTheme.dimen.marginPrettySmall),
        shape = QrCodeTheme.shape.roundedLarge,
        colors = cardColor,
      ) {
        Column {
          repeat(3) { index ->
            HorizontalCardItem(item = uiState.data[15 + index]) {
              onItemClicked.invoke(it)
            }
          }
        }
      }
    }
  }
}

@Composable
fun Title(title: String, modifier: Modifier = Modifier) {
  Row(
    modifier = Modifier.padding(vertical = QrCodeTheme.dimen.marginPrettySmall),
    horizontalArrangement = Arrangement.spacedBy(QrCodeTheme.dimen.marginPrettySmall),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Box(
      modifier = modifier
        .width(QrCodeTheme.dimen.marginVerySmall)
        .height(QrCodeTheme.dimen.marginSmall)
        .background(QrCodeTheme.color.primary, shape = QrCodeTheme.shape.smallCorner),
    )
    Text(
      text = title,
      style = QrCodeTheme.typo.subTitle,
      color = QrCodeTheme.color.neutral1,
    )
  }
}

@SuppressLint("ComposeModifierReused")
@Composable
fun ColumnScope.HorizontalCardItem(
  item: CreateQrType,
  modifier: Modifier = Modifier,
  onItemClicked: (CreateQrType) -> Unit,
) {
  Row(
    modifier = modifier
      .fillMaxSize()
      .clickable {
        onItemClicked(item)
      }
      .padding(QrCodeTheme.dimen.marginDefault),
  ) {
    Image(
      painter = painterResource(id = item.imageResId),
      contentDescription = null,
    )
    Text(
      text = stringResource(id = item.titleResId),
      modifier = modifier
        .padding(start = QrCodeTheme.dimen.marginDefault)
        .align(Alignment.CenterVertically),
      color = QrCodeTheme.color.neutral1,
      style = QrCodeTheme.typo.body,
    )
  }
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .padding(
        start = QrCodeTheme.dimen.qrTypeImageSize,
        end = QrCodeTheme.dimen.marginDefault,
      )
      .height(1.dp)
      .background(QrCodeTheme.color.backgroundCard),
  )
}

@Composable
fun RowScope.VerticalCardItem(
  item: CreateQrType,
  modifier: Modifier = Modifier,
  onItemClicked: (CreateQrType) -> Unit,
) {
  Column(
    modifier = modifier
      .clickable {
        onItemClicked(item)
      }
      .size(QrCodeTheme.dimen.featureHeight),
    verticalArrangement = Arrangement.spacedBy(QrCodeTheme.dimen.marginPrettySmall),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Image(
      painter = painterResource(id = item.imageResId),
      contentDescription = null,
      modifier = Modifier
        .align(Alignment.CenterHorizontally),
    )
    Text(
      text = stringResource(id = item.titleResId),
      modifier = Modifier
        .align(Alignment.CenterHorizontally),
      color = QrCodeTheme.color.neutral1,
      style = QrCodeTheme.typo.body,
    )
  }
}
