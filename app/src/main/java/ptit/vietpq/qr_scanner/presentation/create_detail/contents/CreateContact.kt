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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale
import ptit.vietpq.qr_scanner.presentation.create_detail.components.CreateButton
import ptit.vietpq.qr_scanner.presentation.create_detail.components.InputField

@Composable
fun CreateContact(onContactCreated: (String, String, String) -> Unit, modifier: Modifier = Modifier) {
  var nameTextState by rememberSaveable { mutableStateOf("") }
  var phoneNumberTextState by rememberSaveable { mutableStateOf("") }
  var emailTextState by rememberSaveable { mutableStateOf("") }

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
            painter = painterResource(id = R.drawable.ic_contact_infor),
            contentDescription = null,
          )
        }
        InputField(
          title = QrLocale.strings.name,
          placeholder = QrLocale.strings.pleaseFillInTheContact,
          value = nameTextState,
          onValueChange = {
            nameTextState = it
          },
          regex = Regex(".*\\w.*"),
          errorMessage = QrLocale.strings.pleaseFillInTheContact,
        )
        InputField(
          title = QrLocale.strings.phoneNumber,
          placeholder = QrLocale.strings.pleaseFillInThePhoneNumber,
          value = phoneNumberTextState,
          onValueChange = {
            phoneNumberTextState = it
          },
          regex = Regex(".*\\d.*"),
          errorMessage = QrLocale.strings.pleaseFillInThePhoneNumber,
          keyboardOption = KeyboardOptions(keyboardType = KeyboardType.Phone),
        )
        InputField(
          title = QrLocale.strings.emailAddress,
          placeholder = QrLocale.strings.pleaseFillInTheEmail,
          value = emailTextState,
          onValueChange = {
            emailTextState = it
          },
          regex = Regex(".*@.*\\..*"),
          errorMessage = QrLocale.strings.pleaseFillInTheCorrectEmail,
        )
      }
    }
    CreateButton(
      modifier = Modifier
        .fillMaxWidth()
        .align(Alignment.BottomCenter)
        .padding(
          start = QrCodeTheme.dimen.marginDefault,
          end = QrCodeTheme.dimen.marginDefault,
          bottom = QrCodeTheme.dimen.marginDefault,
        ),
      isEnable = nameTextState.isNotEmpty() &&
        phoneNumberTextState.isNotEmpty() &&
        emailTextState.isNotEmpty() &&
        emailTextState.matches(
          Regex(".*@.*\\..*"),
        ),
      onClick = {
        onContactCreated.invoke(
          nameTextState,
          phoneNumberTextState,
          emailTextState,
        )
      },
    )
  }
}

@Preview
@Composable
private fun CreateContactPreview() {
  CreateContact(onContactCreated = { _, _, _ -> })
}
