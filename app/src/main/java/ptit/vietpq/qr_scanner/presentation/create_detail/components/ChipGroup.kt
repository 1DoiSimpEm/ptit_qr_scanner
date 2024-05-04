package ptit.vietpq.qr_scanner.presentation.create_detail.components

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ptit.vietpq.qr_scanner.presentation.create_detail.CreateQrType
import kotlinx.collections.immutable.ImmutableList

@Composable
fun ChipGroup(
    chips: ImmutableList<CreateQrType>,
    currentSelected: CreateQrType,
    onChipSelected: (CreateQrType) -> Unit,
    listState: LazyListState,
    modifier: Modifier = Modifier,
) {
  LazyRow(
    modifier = modifier,
    state = listState,
  ) {
    items(
      items = chips,
      key = {
        it.titleResId
      },
    ) { chip ->
      Chip(
        chip = chip,
        isSelected = chip == currentSelected,
        onClick = {
          onChipSelected(chip)
        },
      )
    }
  }
}
