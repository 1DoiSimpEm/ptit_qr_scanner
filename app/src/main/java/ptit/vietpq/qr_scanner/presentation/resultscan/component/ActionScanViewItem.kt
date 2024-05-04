package ptit.vietpq.qr_scanner.presentation.resultscan.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme

private const val ONE_ITEM_PER_LINE = 1
private const val TWO_ITEM_PER_LINE = 2
private const val THREE_ITEM_PER_LINE = 3
private const val TOP_CHUNKED_ITEM = 4

@Composable
fun ActionScan(
    listAction: ImmutableList<ActionOptionModel>,
    modifier: Modifier = Modifier,
    onClickAction: (ActionOptionModel) -> Unit,
) {
  LazyColumn(
    modifier = modifier
      .fillMaxWidth(),
  ) {
    items(items = listAction.chunked(TOP_CHUNKED_ITEM)) { rowActions ->
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement =
        when (rowActions.size) {
          ONE_ITEM_PER_LINE -> {
            Arrangement.Start
          }

          TWO_ITEM_PER_LINE -> {
            Arrangement.SpaceAround
          }

          THREE_ITEM_PER_LINE, TOP_CHUNKED_ITEM -> {
            Arrangement.SpaceBetween
          }

          else -> {
            Arrangement.SpaceEvenly
          }
        },
      ) {
        rowActions.forEach { actionOptionModel ->
          CardItemAction(
            option = actionOptionModel,
            onItemClicked = onClickAction,
          )
        }
      }
    }
  }
}

@Composable
fun ContentActionScan(
    option: ActionOptionModel,
    modifier: Modifier = Modifier,
    onClickAction: (ActionOptionModel) -> Unit,
) {
  Column(
    modifier = modifier
      .wrapContentWidth()
      .clickable {
        onClickAction(option)
      },
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    CardItemAction(
      option = option,
      onItemClicked = onClickAction,
    )
  }
}

@Composable
fun CardItemAction(
    option: ActionOptionModel,
    modifier: Modifier = Modifier,
    onItemClicked: (ActionOptionModel) -> Unit,
) {
  Column(
    modifier = modifier
      .clickable {
        onItemClicked(option)
      }
      .size(QrCodeTheme.dimen.featureWidthCard),
    verticalArrangement = Arrangement.spacedBy(QrCodeTheme.dimen.marginPrettySmall),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Image(
      painter = painterResource(id = option.image),
      contentDescription = null,
      modifier = Modifier
        .size(40.dp)
        .align(Alignment.CenterHorizontally),
    )
    Text(
      text = option.title,
      textAlign = TextAlign.Center,
      color = QrCodeTheme.color.neutral1,
      style = QrCodeTheme.typo.body,
    )
  }
}

@Preview
@Composable
private fun VerticalCardItemPreview() {
  CardItemAction(
    option = ActionOptionModel.createMenuOptionModel(
      id = MenuOptionIndex.OPEN_URL,
      image = R.drawable.ic_copy,
      title = "Open 132131 URL ads 123",
    ),
    onItemClicked = {},
  )
}

@Preview
@Composable
private fun ContentActionScanPreview() {
  ContentActionScan(
    option = ActionOptionModel.createMenuOptionModel(
      id = MenuOptionIndex.OPEN_URL,
      image = R.drawable.ic_copy,
      title = "Open URL ads 123",
    ),
    onClickAction = {},
  )
}
