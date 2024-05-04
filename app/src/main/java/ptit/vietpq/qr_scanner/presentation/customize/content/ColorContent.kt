package ptit.vietpq.qr_scanner.presentation.customize.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.customize_qr.ColorModel
import ptit.vietpq.qr_scanner.presentation.create.Title
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.presentation.customize.component.ColorItem
import ptit.vietpq.qr_scanner.R

@Composable
fun ColorContent(
  selectedForegroundColor: Color,
  selectedBackgroundColor: Color,
  onColorWheelStarted: (Boolean) -> Unit,
  onForegroundColorSelected: (Color) -> Unit,
  onBackgroundColorSelected: (Color) -> Unit,
  modifier: Modifier = Modifier,
) {
  val colorList = remember {
    ColorModel.getColorList()
  }

  Column(
    modifier = modifier,
  ) {
    Title(title = stringResource(R.string.foreground))

    Spacer(modifier = Modifier.size(12.dp))

    LazyVerticalGrid(
      columns = GridCells.Fixed(6),
      verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
      items(
        colorList,
        key = {
          it.hashCode()
        },
      ) { color ->

        ColorItem(
          colorItem = color,
          isSelected = when (selectedForegroundColor) {
            Color.Black -> color == colorList.getOrNull(0)
            else -> colorList.any { it.color == selectedForegroundColor } && selectedForegroundColor == color.color
          },
          onColorSelected = {
            when (it.drawableResId) {
              R.drawable.ic_none -> onForegroundColorSelected(Color.Black)
              R.drawable.ic_color_wheel -> onColorWheelStarted(false)
              else -> onForegroundColorSelected(it.color ?: Color.Black)
            }
          },
        )
      }
    }
    Spacer(modifier = Modifier.size(24.dp))

    Title(title = stringResource(R.string.background))

    Spacer(modifier = Modifier.size(12.dp))

    LazyVerticalGrid(
      columns = GridCells.Fixed(6),
      verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
      items(
        colorList,
        key = {
          it.hashCode()
        },
      ) { color ->

        ColorItem(
          colorItem = color,
          isSelected = when (selectedBackgroundColor) {
            Color.Transparent -> color == colorList.getOrNull(0)
            else -> colorList.any { it.color == selectedBackgroundColor } && selectedBackgroundColor == color.color
          },
          onColorSelected = {
            when (it.drawableResId) {
              R.drawable.ic_none -> onBackgroundColorSelected(Color.Transparent)
              R.drawable.ic_color_wheel -> onColorWheelStarted(true)
              else -> onBackgroundColorSelected(it.color ?: Color.White)
            }
          },
        )
      }
    }
  }
}
