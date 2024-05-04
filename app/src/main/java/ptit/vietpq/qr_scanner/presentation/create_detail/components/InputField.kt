package ptit.vietpq.qr_scanner.presentation.create_detail.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale

@Composable
fun BoxScope.InputField(
  title: String,
  placeholder: String,
  value: String,
  modifier: Modifier = Modifier,
  onValueChange: (String) -> Unit = {},
  isEnable: Boolean = true,
  regex: Regex = Regex(".+"),
  errorMessage: String = QrLocale.strings.pleaseFillTheID,
  keyboardOption: KeyboardOptions = KeyboardOptions.Default,
) {
  var isError by remember { mutableStateOf(false) }
  val textFieldColor = TextFieldDefaults.colors(
    focusedIndicatorColor = QrCodeTheme.color.primary,
    unfocusedIndicatorColor = Color.Transparent,
    cursorColor = QrCodeTheme.color.primary,
    unfocusedPlaceholderColor = QrCodeTheme.color.neutral3,
    focusedLabelColor = QrCodeTheme.color.neutral1,
    unfocusedLabelColor = QrCodeTheme.color.neutral3,
    focusedContainerColor = QrCodeTheme.color.neutral5,
    unfocusedContainerColor = QrCodeTheme.color.neutral5,
    disabledTextColor = QrCodeTheme.color.neutral1,
    disabledIndicatorColor = Color.Transparent,
    disabledContainerColor = QrCodeTheme.color.neutral5,
  )
  Text(
    modifier = Modifier.padding(
      top = QrCodeTheme.dimen.marginPrettyLarge,
      start = QrCodeTheme.dimen.marginDefault,
      end = QrCodeTheme.dimen.marginDefault,
    ),
    text = title,
    style = QrCodeTheme.typo.body,
    color = QrCodeTheme.color.neutral1,
  )
  OutlinedTextField(
    modifier = modifier
      .fillMaxWidth()
      .padding(
        start = QrCodeTheme.dimen.marginDefault,
        end = QrCodeTheme.dimen.marginDefault,
        top = QrCodeTheme.dimen.marginPrettySmall,
      ),
    value = value,
    colors = textFieldColor,
    onValueChange = {
      isError = !regex.matches(it)
      onValueChange(it)
    },
    textStyle = QrCodeTheme.typo.body,
    placeholder = {
      Text(text = placeholder, style = QrCodeTheme.typo.body, color = QrCodeTheme.color.neutral3)
    },
    enabled = isEnable,
    singleLine = true,
    isError = isError,
    shape = QrCodeTheme.shape.roundedLarge,
    keyboardOptions = keyboardOption,
    supportingText = {
      if (isError) {
        Text(
          text = errorMessage,
          style = QrCodeTheme.typo.caption,
          color = QrCodeTheme.color.error,
        )
      }
    },
  )
}

@Composable
fun ColumnScope.InputField(
  title: String,
  placeholder: String,
  value: String,
  onValueChange: (String) -> Unit,
  modifier: Modifier = Modifier,
  regex: Regex = Regex(".+"),
  errorMessage: String = QrLocale.strings.pleaseFillTheID,
  keyboardOption: KeyboardOptions = KeyboardOptions.Default,
) {
  var isError by remember { mutableStateOf(false) }
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
    text = title,
    style = QrCodeTheme.typo.body,
    color = QrCodeTheme.color.neutral1,
  )
  OutlinedTextField(
    modifier = modifier
      .fillMaxWidth()
      .padding(
        start = QrCodeTheme.dimen.marginDefault,
        end = QrCodeTheme.dimen.marginDefault,
        top = QrCodeTheme.dimen.marginPrettySmall,
      ),
    value = value,
    colors = textFieldColor,
    onValueChange = {
      isError = !regex.matches(it)
      onValueChange(it)
    },
    textStyle = QrCodeTheme.typo.body,
    placeholder = {
      Text(text = placeholder, style = QrCodeTheme.typo.body, color = QrCodeTheme.color.neutral3)
    },
    singleLine = true,
    isError = isError,
    shape = QrCodeTheme.shape.roundedLarge,
    keyboardOptions = keyboardOption,
    supportingText = {
      if (isError) {
        Text(
          text = errorMessage,
          style = QrCodeTheme.typo.caption,
          color = QrCodeTheme.color.error,
        )
      }
    },
  )
}
