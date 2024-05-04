package ptit.vietpq.qr_scanner.presentation.scan.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme

enum class TabMode(val value: Int) {
  SINGLE(0),
  MULTIPLE(1),
}

@Composable
fun TabSelectMode(
  tabHolder: ImmutableList<String>,
  onItemSelect: (tabMode: TabMode) -> Unit,
  selectedTab: TabMode,
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = modifier
      .clip(QrCodeTheme.shape.tab)
      .background(QrCodeTheme.color.neutral6),
  ) {
    TabRow(
      containerColor = Color.Transparent,
      selectedTabIndex = selectedTab.value,
      indicator = {
      },
      divider = {
        HorizontalDivider(
          modifier = Modifier.height(0.dp),
        )
      },
    ) {
      tabHolder.forEachIndexed { index, text ->
        val selected = selectedTab.value != index

        val textColor by animateColorAsState(if (selected) Color.White else Color.Black, label = text)

        LeadingIconTab(
          selectedContentColor = Color.White,
          unselectedContentColor = Color.White,
          icon = {
            if (index == 0) {
              Image(
                painter = painterResource(id = R.drawable.ic_scan_single),
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                  color = textColor,
                ),
              )
            } else {
              Image(
                painter = painterResource(id = R.drawable.ic_scan_batch),
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                  color = textColor,
                ),
              )
            }
          },
          modifier = Modifier
            .clip(QrCodeTheme.shape.tab)
            .padding(0.dp)
            .fillMaxHeight()
            .background(color = if (selected) Color.Transparent else Color.White, shape = QrCodeTheme.shape.tab),
          selected = selected,
          onClick = {
            onItemSelect(if (index == 0) TabMode.SINGLE else TabMode.MULTIPLE)
          },
          text = {
            Text(
              text = text,
              color = textColor,
              style = QrCodeTheme.typo.innerMediumSize14LineHeight20,
            )
          },
        )
      }
    }
  }
}

@Preview
@Composable
private fun PreviewTabSelectMode() {
  TabSelectMode(
    modifier = Modifier,
    tabHolder = persistentListOf("Active", "Completed"),
    onItemSelect = {},
    selectedTab = TabMode.SINGLE,
  )
}
