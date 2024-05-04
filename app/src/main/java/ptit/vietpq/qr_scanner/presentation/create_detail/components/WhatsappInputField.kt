package ptit.vietpq.qr_scanner.presentation.create_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale

@Composable
fun ColumnScope.WhatsappInputField(
  countryCode: String,
  phoneNumber: String,
  onValueChange: (String) -> Unit,
  onCountryCodeClicked: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val textFieldColor = TextFieldDefaults.colors(
    focusedIndicatorColor = QrCodeTheme.color.primary,
    unfocusedIndicatorColor = Color.Transparent,
    cursorColor = QrCodeTheme.color.primary,
    unfocusedPlaceholderColor = QrCodeTheme.color.neutral3,
    focusedLabelColor = QrCodeTheme.color.neutral1,
    unfocusedLabelColor = QrCodeTheme.color.neutral3,
    focusedContainerColor = QrCodeTheme.color.neutral5,
    unfocusedContainerColor = QrCodeTheme.color.neutral5,
  )
  Text(
    modifier = Modifier.padding(
      top = QrCodeTheme.dimen.marginPrettyLarge,
      start = QrCodeTheme.dimen.marginDefault,
      end = QrCodeTheme.dimen.marginDefault,
    ),
    text = QrLocale.strings.userName,
    style = QrCodeTheme.typo.body,
    color = QrCodeTheme.color.neutral1,
  )
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(
        start = QrCodeTheme.dimen.marginDefault,
        end = QrCodeTheme.dimen.marginDefault,
        top = QrCodeTheme.dimen.marginPrettySmall,
      ),
    horizontalArrangement = Arrangement.SpaceBetween,
  ) {
    Button(
      modifier = Modifier
        .height(48.dp),
      onClick = onCountryCodeClicked,
      colors = ButtonDefaults.buttonColors(containerColor = QrCodeTheme.color.primaryDisable),
      shape = QrCodeTheme.shape.roundedLarge,
    ) {
      Text(
        text = countryCode,
        style = QrCodeTheme.typo.body,
        color = QrCodeTheme.color.primary,
      )
    }
    OutlinedTextField(
      modifier = Modifier
        .height(48.dp)
        .padding(start = QrCodeTheme.dimen.marginPrettySmall)
        .weight(1f, true),
      value = phoneNumber,
      colors = textFieldColor,
      onValueChange = {
        onValueChange(it)
      },
      textStyle = QrCodeTheme.typo.body,
      placeholder = {
        Text(text = QrLocale.strings.phoneNumber, style = QrCodeTheme.typo.body, color = QrCodeTheme.color.neutral3)
      },
      shape = QrCodeTheme.shape.roundedLarge,
      keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Number,
      ),
    )
  }
}
