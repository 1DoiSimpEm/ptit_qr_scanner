package ptit.vietpq.qr_scanner.presentation.create_detail.contents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.remember
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
fun CreatePhone(onCreateClicked: (phoneNumber: String) -> Unit, modifier: Modifier = Modifier) {
  var phoneTextState by remember { mutableStateOf("") }
  Column(
    modifier = modifier.background(QrCodeTheme.color.backgroundCard),
    horizontalAlignment = Alignment.Start,
  ) {
    Image(
      modifier = Modifier
        .size(QrCodeTheme.dimen.qrTypeImageSize)
        .padding(top = QrCodeTheme.dimen.marginDefault)
        .align(Alignment.CenterHorizontally),
      painter = painterResource(id = R.drawable.ic_category_tel),
      contentDescription = null,
    )
    InputField(
      title = QrLocale.strings.phoneNumber,
      placeholder = QrLocale.strings.pleaseFillInTheContent,
      value = phoneTextState,
      onValueChange = {
        phoneTextState = it
      },
      keyboardOption = KeyboardOptions(keyboardType = KeyboardType.Phone),
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
        isEnable = phoneTextState.isNotEmpty(),
        onClick = {
          onCreateClicked(
            phoneTextState,
          )
        },
      )
    }
  }
}
