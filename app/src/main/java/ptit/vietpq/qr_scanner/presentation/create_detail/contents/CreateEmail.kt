package ptit.vietpq.qr_scanner.presentation.create_detail.contents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale
import ptit.vietpq.qr_scanner.presentation.create_detail.components.CreateButton
import ptit.vietpq.qr_scanner.presentation.create_detail.components.InputField

@Composable
fun CreateEmail(onEmailCreated: (String, String, String) -> Unit, modifier: Modifier = Modifier) {
  var emailTextState by rememberSaveable { mutableStateOf("") }
  var subjectTextState by rememberSaveable { mutableStateOf("") }
  var messageTextState by rememberSaveable { mutableStateOf("") }
  Box(
    modifier = modifier
      .background(QrCodeTheme.color.backgroundCard)
      .fillMaxSize(),
  ) {
    LazyColumn(
      modifier = Modifier
        .fillMaxHeight()
        .padding(bottom = 72.dp),
    ) {
      item {
        Box(
          modifier = Modifier
            .fillMaxSize()
            .padding(top = QrCodeTheme.dimen.marginDefault),
        ) {
          Image(
            modifier = Modifier
              .size(QrCodeTheme.dimen.qrTypeImageSize)
              .padding(top = QrCodeTheme.dimen.marginDefault)
              .align(Alignment.Center),
            painter = painterResource(id = R.drawable.ic_email),
            contentDescription = null,
          )
        }
        InputField(
          title = QrLocale.strings.emailAddress,
          placeholder = QrLocale.strings.pleaseFillInTheEmail,
          value = emailTextState,
          onValueChange = {
            emailTextState = it
          },
          regex = Regex(".*@.*\\..*"),
          errorMessage = QrLocale.strings.pleaseFillInTheEmail,
        )
        InputField(
          title = QrLocale.strings.subject,
          placeholder = QrLocale.strings.pleaseFillInTheSubject,
          value = subjectTextState,
          onValueChange = {
            subjectTextState = it
          },
          errorMessage = QrLocale.strings.pleaseFillInTheSubject,
        )
        InputField(
          title = QrLocale.strings.message,
          placeholder = QrLocale.strings.pleaseInputEmailContent,
          value = messageTextState,
          onValueChange = {
            messageTextState = it
          },
          errorMessage = QrLocale.strings.pleaseInputEmailContent,
        )
      }
    }
    CreateButton(
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .fillMaxWidth()
        .padding(
          start = QrCodeTheme.dimen.marginDefault,
          end = QrCodeTheme.dimen.marginDefault,
          bottom = QrCodeTheme.dimen.marginDefault,
        ),
      isEnable = emailTextState.isNotEmpty() &&
        subjectTextState.isNotEmpty() &&
        messageTextState.isNotEmpty() &&
        emailTextState.matches(Regex(".*@.*\\..*")),
      onClick = {
        onEmailCreated.invoke(
          emailTextState,
          subjectTextState,
          messageTextState,
        )
      },
    )
  }
}

@Preview
@Composable
private fun CreateEmailPreview() {
  CreateEmail(onEmailCreated = { _, _, _ -> })
}
