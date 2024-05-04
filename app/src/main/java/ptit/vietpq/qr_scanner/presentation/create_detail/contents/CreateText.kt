package ptit.vietpq.qr_scanner.presentation.create_detail.contents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale
import ptit.vietpq.qr_scanner.presentation.create_detail.components.CreateButton
import ptit.vietpq.qr_scanner.presentation.create_detail.components.InputField

@Composable
fun CreateText(onCreateClicked: (content: String) -> Unit, modifier: Modifier = Modifier) {
  var contentTextState by remember { mutableStateOf("") }
  Box(
    modifier = modifier.fillMaxSize().background(QrCodeTheme.color.backgroundCard),
  ) {
    Column(
      horizontalAlignment = Alignment.Start,
    ) {
      Image(
        modifier = Modifier
          .size(QrCodeTheme.dimen.qrTypeImageSize)
          .padding(top = QrCodeTheme.dimen.marginDefault)
          .align(Alignment.CenterHorizontally),
        painter = painterResource(id = R.drawable.ic_text),
        contentDescription = null,
      )
      InputField(
        title = QrLocale.strings.content,
        placeholder = QrLocale.strings.pleaseFillInTheContent,
        value = contentTextState,
        onValueChange = {
          contentTextState = it
        },
      )
    }
    CreateButton(
      modifier = Modifier
        .fillMaxWidth().padding(
          start = QrCodeTheme.dimen.marginDefault,
          end = QrCodeTheme.dimen.marginDefault,
          bottom = QrCodeTheme.dimen.marginDefault,
        ).align(Alignment.BottomCenter),
      isEnable = contentTextState.isNotEmpty(),
      onClick = {
        onCreateClicked(
          contentTextState,
        )
      },
    )
  }
}

@Preview
@Composable
private fun CreateTextPreview() {
  Box {
    CreateText(onCreateClicked = {})
  }
}
