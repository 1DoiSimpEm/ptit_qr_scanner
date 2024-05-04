package ptit.vietpq.qr_scanner.presentation.create_detail.contents

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ptit.vietpq.qr_scanner.presentation.create_detail.components.CreateButton
import ptit.vietpq.qr_scanner.presentation.create_detail.components.InputField
import kotlinx.collections.immutable.persistentListOf
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale

@Composable
fun CreateWifi(
  onCreateClicked: (ssid: String, password: String, securityType: String) -> Unit,
  modifier: Modifier = Modifier,
) {
  val titles = persistentListOf("WPA/WPA2", "WEP", "NONE")
  var selectedTabIndex by remember { mutableIntStateOf(0) }
  var networkNameTextState by remember { mutableStateOf("") }
  var passwordTextState by remember { mutableStateOf("") }
  Column(
    modifier = modifier.background(QrCodeTheme.color.backgroundCard),
    horizontalAlignment = Alignment.Start,
  ) {
    Image(
      modifier = Modifier
        .size(QrCodeTheme.dimen.qrTypeImageSize)
        .padding(top = QrCodeTheme.dimen.marginDefault)
        .align(Alignment.CenterHorizontally),
      painter = painterResource(id = R.drawable.ic_wifi),
      contentDescription = null,
    )
    InputField(
      title = QrLocale.strings.networkName,
      placeholder = QrLocale.strings.pleaseFillInTheSSID,
      value = networkNameTextState,
      onValueChange = {
        networkNameTextState = it
      },
    )
    TabRow(
      modifier = Modifier
        .padding(
          top = QrCodeTheme.dimen.marginDefault,
          start = QrCodeTheme.dimen.marginDefault,
          end = QrCodeTheme.dimen.marginDefault,
        )
        .clip(QrCodeTheme.shape.roundedSuperLarge),
      indicator = {},
      divider = {},
      selectedTabIndex = selectedTabIndex,
      containerColor = QrCodeTheme.color.neutral5,
    ) {
      titles.forEachIndexed { index, title ->
        val isSelected = selectedTabIndex == index
        Tab(
          modifier = Modifier
            .padding(end = if (index == 0) 8.dp else 0.dp)
            .clip(QrCodeTheme.shape.roundedSuperLarge)
            .background(if (isSelected) QrCodeTheme.color.primary else QrCodeTheme.color.neutral5),
          text = {
            Crossfade(targetState = isSelected, label = "") { isTabSelected ->
              Text(
                text = title,
                style = QrCodeTheme.typo.body,
                color = if (isTabSelected) QrCodeTheme.color.neutral5 else QrCodeTheme.color.neutral1,
              )
            }
          },
          selected = isSelected,
          onClick = {
            selectedTabIndex = index
          },
          selectedContentColor = QrCodeTheme.color.primary,
          unselectedContentColor = QrCodeTheme.color.neutral5,
        )
      }
    }
    InputField(
      title = QrLocale.strings.password,
      placeholder = QrLocale.strings.pleaseFillInThePassword,
      value = passwordTextState,
      onValueChange = {
        passwordTextState = it
      },
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
        isEnable = networkNameTextState.isNotEmpty() && passwordTextState.isNotEmpty(),
        onClick = {
          onCreateClicked(
            networkNameTextState,
            passwordTextState,
            titles[selectedTabIndex],
          )
        },
      )
    }
  }
}
