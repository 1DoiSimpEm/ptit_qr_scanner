package ptit.vietpq.qr_scanner.presentation.customize.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.common.TwButton
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale

@Composable
fun ColorPickerDialog(
  isShown: Boolean,
  onDismiss: () -> Unit,
  onColorPicked: (Color) -> Unit,
  modifier: Modifier = Modifier,
) {
  var pickedColor by remember {
    mutableStateOf(Color.White)
  }
  if (isShown) {
    Dialog(onDismissRequest = onDismiss) {
      Card(
        modifier = modifier
          .background(Color.Transparent),
        shape = QrCodeTheme.shape.roundedLarge,
        colors = CardColors(
          containerColor = QrCodeTheme.color.neutral5,
          contentColor = QrCodeTheme.color.primary,
          disabledContainerColor = QrCodeTheme.color.neutral4,
          disabledContentColor = QrCodeTheme.color.neutral3,
        ),
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
          horizontalAlignment = Alignment.CenterHorizontally,
        ) {
          CircleColorPicker(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onColorPicked = {
              pickedColor = it
            },
          )
          Spacer(modifier = Modifier.height(24.dp))
          ActionsRow(
            modifier = Modifier.fillMaxWidth(),
            positive = QrLocale.strings.save,
            negative = QrLocale.strings.cancel,
            onPositiveClick = {
              onColorPicked(pickedColor)
              onDismiss()
            },
            onNegativeClick = {
              onDismiss()
            },
          )
        }
      }
    }
  }
}

@Composable
private fun ActionsRow(
  modifier: Modifier = Modifier,
  positive: String? = null,
  negative: String? = null,
  onPositiveClick: (() -> Unit)? = null,
  onNegativeClick: (() -> Unit)? = null,
  positiveEnabled: Boolean = true,
  negativeEnabled: Boolean = true,
  buttonColorsPositive: ButtonColors = ButtonDefaults.buttonColors(
    containerColor = QrCodeTheme.color.primary,
    contentColor = QrCodeTheme.color.onButton,
  ),
  buttonColorsNegative: ButtonColors = ButtonDefaults.buttonColors(
    containerColor = QrCodeTheme.color.neutral4,
    contentColor = QrCodeTheme.color.neutral3,
  ),
) {
  Row(
    modifier = modifier
      .padding(vertical = 8.dp)
      .fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
  ) {
    if (negative != null) {
      TwButton(
        modifier = Modifier
          .weight(1f),
        buttonColors = buttonColorsNegative,
        text = negative,
        enabled = negativeEnabled,
        onClick = { onNegativeClick?.invoke() },
      )

      Spacer(modifier = Modifier.width(8.dp))
    }
    if (positive != null) {
      TwButton(
        modifier = Modifier
          .weight(1f),
        buttonColors = buttonColorsPositive,
        text = positive,
        enabled = positiveEnabled,
        onClick = { onPositiveClick?.invoke() },
      )
    }
  }
}

@Preview
@Composable
private fun ColorPickerDialogPreview() {
  ColorPickerDialog(isShown = true, onDismiss = {}, {})
}
