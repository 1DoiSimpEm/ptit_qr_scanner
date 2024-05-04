package ptit.vietpq.qr_scanner.presentation.create_detail.contents

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import ptit.vietpq.qr_scanner.extension.copyToClipboard
import ptit.vietpq.qr_scanner.presentation.create_detail.components.CreateButton
import ptit.vietpq.qr_scanner.presentation.create_detail.components.InputField
import ptit.vietpq.qr_scanner.presentation.create_detail.components.SuggestUrlItem
import kotlinx.collections.immutable.persistentListOf
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale

@Composable
fun CreateWebsite(onWebsiteCreated: (String) -> Unit, modifier: Modifier = Modifier) {
  val context = LocalContext.current
  val urlPrefixList = persistentListOf(
    "https://",
    "http://",
    "www.",
    ".com",
  )
  var linkTextState by rememberSaveable { mutableStateOf("") }
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.Start,
  ) {
    Image(
      modifier = Modifier
        .size(QrCodeTheme.dimen.qrTypeImageSize)
        .padding(top = QrCodeTheme.dimen.marginDefault)
        .align(Alignment.CenterHorizontally),
      painter = painterResource(id = R.drawable.ic_website),
      contentDescription = null,
    )
    InputField(
      title = QrLocale.strings.link,
      placeholder = QrLocale.strings.pleaseFillInTheWebsiteAddress,
      value = linkTextState,
      onValueChange = {
        linkTextState = it
      },
      regex = Regex(".*\\w.*"),
      errorMessage = QrLocale.strings.pleaseFillInTheWebsiteAddress,
    )
    Row(
      modifier = Modifier.padding(start = QrCodeTheme.dimen.marginDefault, top = QrCodeTheme.dimen.marginPrettySmall),
      horizontalArrangement = Arrangement.Absolute.Center,
    ) {
      Text(
        modifier = Modifier.align(Alignment.CenterVertically),
        text = QrLocale.strings.linkExample,
        color = QrCodeTheme.color.neutral2,
        style = QrCodeTheme.typo.caption,
      )
      Image(
        modifier = Modifier
          .clickable {
            context.copyToClipboard(linkTextState, linkTextState)
          }
          .padding(start = QrCodeTheme.dimen.marginPrettySmall),
        painter = painterResource(id = R.drawable.ic_copy),
        contentDescription = null,
      )
    }
    LazyRow(modifier = Modifier.padding(QrCodeTheme.dimen.marginDefault)) {
      items(
        count = urlPrefixList.size,
        key = {
          urlPrefixList[it]
        },
      ) { index ->
        SuggestUrlItem(
          modifier = Modifier.padding(end = QrCodeTheme.dimen.marginPrettySmall),
          item = urlPrefixList[index],
          onClick = { content ->
            linkTextState += content
          },
        )
      }
    }
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
        isEnable = linkTextState.isNotEmpty() && linkTextState.matches(Regex(".*\\w.*")),
        onClick = {
          onWebsiteCreated.invoke(linkTextState)
        },
      )
    }
  }
}
