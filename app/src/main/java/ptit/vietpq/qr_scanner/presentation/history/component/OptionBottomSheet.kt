package ptit.vietpq.qr_scanner.presentation.history.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionBottomSheet(
  isBottomSheetVisible: Boolean,
  sheetState: SheetState,
  onDeleteClicked: () -> Unit,
  onDismissRequest: () -> Unit,
  modifier: Modifier = Modifier,
) {
  if (isBottomSheetVisible) {
    ModalBottomSheet(
      modifier = modifier,
      onDismissRequest = onDismissRequest,
      sheetState = sheetState,
      containerColor = QrCodeTheme.color.neutral5,
      dragHandle = {},
      shape = RoundedCornerShape(
        topStart = 16.dp,
        topEnd = 16.dp,
      ),
      content = {
        OptionItem(
          modifier = Modifier.padding(QrCodeTheme.dimen.marginDefault),
          imageId = R.drawable.ic_trash,
          content = QrLocale.strings.delete,
          onItemClicked = onDeleteClicked,
        )
      },
    )
  }
}

@Composable
fun OptionItem(imageId: Int, content: String, onItemClicked: () -> Unit, modifier: Modifier = Modifier) {
  Row(
    modifier = modifier.fillMaxWidth().clickable {
      onItemClicked()
    },
    horizontalArrangement = Arrangement.spacedBy(16.dp),
  ) {
    Image(
      modifier = Modifier.align(Alignment.CenterVertically),
      painter = painterResource(id = imageId),
      contentDescription = content,
    )
    Text(
      modifier = Modifier.align(Alignment.CenterVertically),
      text = content,
      style = QrCodeTheme.typo.body2,
      color = QrCodeTheme.color.neutral1,
    )
  }
}
