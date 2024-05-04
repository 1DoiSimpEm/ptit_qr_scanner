package ptit.vietpq.qr_scanner.presentation.customize.content

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.customize_qr.ShapeModel
import ptit.vietpq.qr_scanner.presentation.customize.component.ShapeItem

@Composable
fun ShapeContent(shapeModel: ShapeModel, onShapeSelected: (ShapeModel) -> Unit, modifier: Modifier = Modifier) {
  val shapeList = remember {
    ShapeModel.getShapeList()
  }

  LazyVerticalGrid(
    modifier = modifier,
    columns = GridCells.Fixed(4),
  ) {
    items(
      shapeList,
      key = {
        it.hashCode()
      },
    ) { shape ->

      ShapeItem(
        modifier = Modifier
          .padding(8.dp),
        shape = shape,
        isSelected = shapeModel == shape,
        onShapeSelected = {
          onShapeSelected(it)
        },
      )
    }
  }
}
