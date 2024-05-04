package ptit.vietpq.qr_scanner.presentation.customize.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ptit.vietpq.qr_scanner.presentation.customize.component.LogoItem
import kotlinx.collections.immutable.persistentListOf
import ptit.vietpq.qr_scanner.R

@Composable
fun LogoContent(selectedLogo: Int, onLogoSelected: (Int) -> Unit, modifier: Modifier = Modifier) {
  val drawableList = remember {
    getDrawableList()
  }

  LazyVerticalGrid(
    modifier = modifier,
    columns = GridCells.Fixed(5),
    verticalArrangement = Arrangement.spacedBy(16.dp),
  ) {
    items(
      items = drawableList,
      key = {
        it.hashCode()
      },
      itemContent = { logo ->

        LogoItem(
          drawableResId = logo,
          isSelected = selectedLogo == logo,
          onLogoSelected = onLogoSelected,
        )
      },
    )
  }
}

fun getDrawableList() = persistentListOf(
  R.drawable.ic_none,
  R.drawable.ic_my_business_card,
  R.drawable.ic_email,
  R.drawable.ic_website,
  R.drawable.ic_contact_infor,
  R.drawable.ic_telephone,
  R.drawable.ic_message,
  R.drawable.ic_whatsapp,
)
