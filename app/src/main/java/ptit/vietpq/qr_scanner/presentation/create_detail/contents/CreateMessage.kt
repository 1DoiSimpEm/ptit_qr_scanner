package ptit.vietpq.qr_scanner.presentation.create_detail.contents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale
import ptit.vietpq.qr_scanner.presentation.create_detail.components.CreateButton
import ptit.vietpq.qr_scanner.presentation.create_detail.components.InputField

@Composable
fun CreateMessage(onMessageCreated: (String, String) -> Unit, modifier: Modifier = Modifier) {
  var recipientNumberTextState by rememberSaveable { mutableStateOf("") }
  var messageTextState by rememberSaveable { mutableStateOf("") }
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.Start,
  ) {
    Image(
      modifier = Modifier
        .size(QrCodeTheme.dimen.qrTypeImageSize)
        .padding(top = QrCodeTheme.dimen.marginDefault)
        .align(Alignment.CenterHorizontally),
      painter = painterResource(id = R.drawable.ic_message),
      contentDescription = null,
    )
    InputField(
      title = QrLocale.strings.recipientNumber,
      placeholder = QrLocale.strings.pleaseFillInTheRecipientNumber,
      value = recipientNumberTextState,
      onValueChange = {
        recipientNumberTextState = it
      },
      regex = Regex(".*\\d.*"),
      errorMessage = QrLocale.strings.pleaseFillInTheRecipientNumber,
      keyboardOption = KeyboardOptions(keyboardType = KeyboardType.Phone),
    )
    InputField(
      title = QrLocale.strings.message,
      placeholder = QrLocale.strings.pleaseInputMessageContent,
      value = messageTextState,
      onValueChange = {
        messageTextState = it
      },
      errorMessage = QrLocale.strings.pleaseInputMessageContent,
    )
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(bottom = QrCodeTheme.dimen.marginDefault),
    ) {
      CreateButton(
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .fillMaxWidth()
          .padding(horizontal = QrCodeTheme.dimen.marginDefault),
        isEnable = recipientNumberTextState.isNotEmpty() && messageTextState.isNotEmpty() &&
          recipientNumberTextState.matches(
            Regex(".*\\d.*"),
          ) && messageTextState.matches(Regex(".*\\w.*")),
        onClick = {
          onMessageCreated.invoke(
            recipientNumberTextState,
            messageTextState,
          )
        },
      )
    }
  }
}
