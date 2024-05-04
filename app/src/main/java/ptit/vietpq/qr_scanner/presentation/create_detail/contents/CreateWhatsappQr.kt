package ptit.vietpq.qr_scanner.presentation.create_detail.contents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale
import ptit.vietpq.qr_scanner.presentation.create_detail.components.CreateButton
import ptit.vietpq.qr_scanner.presentation.create_detail.components.WhatsappInputField

@Composable
fun CreateWhatsappQr(
  countryCodeState: String,
  onCreateClicked: (String) -> Unit,
  onCountryCodeClicked: () -> Unit,
  modifier: Modifier = Modifier,
) {
  var phoneNumberState by rememberSaveable { mutableStateOf("") }
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.Start,
  ) {
    Image(
      modifier = Modifier
        .size(QrCodeTheme.dimen.qrTypeImageSize)
        .padding(top = QrCodeTheme.dimen.marginDefault)
        .align(Alignment.CenterHorizontally),
      painter = painterResource(id = R.drawable.ic_whatsapp),
      contentDescription = null,
    )
    WhatsappInputField(
      onValueChange = {
        phoneNumberState = it
      },
      onCountryCodeClicked = onCountryCodeClicked,
      countryCode = countryCodeState,
      phoneNumber = phoneNumberState,
    )
    Text(
      modifier = Modifier.padding(QrCodeTheme.dimen.marginDefault),
      text = QrLocale.strings.whatsappDescription,
      style = QrCodeTheme.typo.caption,
      color = QrCodeTheme.color.neutral2,
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
        isEnable = phoneNumberState.isNotEmpty(),
        onClick = {
          onCreateClicked.invoke("${countryCodeState}$phoneNumberState")
        },
      )
    }
  }
}
