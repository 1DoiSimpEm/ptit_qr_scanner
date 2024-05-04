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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ptit.vietpq.qr_scanner.presentation.create_detail.CreateQrType
import ptit.vietpq.qr_scanner.presentation.create_detail.components.CreateButton
import ptit.vietpq.qr_scanner.presentation.create_detail.components.InputField
import kotlinx.collections.immutable.persistentListOf
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale

@Composable
fun CreateSocial(socialType: CreateQrType, onCreateClicked: (String, Boolean) -> Unit, modifier: Modifier = Modifier) {
  val titles = persistentListOf("${stringResource(id = socialType.titleResId)} ID", "URL")
  var selectedTabIndex by remember { mutableIntStateOf(0) }
  var idTextState by remember { mutableStateOf("") }
  var urlTextState by remember { mutableStateOf("") }
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.Start,
  ) {
    Image(
      modifier = Modifier
        .size(QrCodeTheme.dimen.qrTypeImageSize)
        .padding(top = QrCodeTheme.dimen.marginDefault)
        .align(Alignment.CenterHorizontally),
      painter = painterResource(id = socialType.imageResId),
      contentDescription = null,
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
    when (selectedTabIndex) {
      0 -> {
        Column {
          InputField(
            title = QrLocale.strings.content,
            placeholder = "${QrLocale.strings.enter} ${stringResource(id = socialType.titleResId)} ID",
            value = idTextState,
            onValueChange = {
              idTextState = it
            },
            regex = Regex("^[a-zA-Z0-9_]*\$"),
            errorMessage = QrLocale.strings.pleaseFillTheID,
          )
        }
      }

      1 -> {
        Column {
          InputField(
            title = QrLocale.strings.content,
            placeholder = "${QrLocale.strings.enter} ${stringResource(id = socialType.titleResId)} URL",
            value = urlTextState,
            onValueChange = {
              urlTextState = it
            },
            regex = Regex("^(http|https)://"),
            errorMessage = QrLocale.strings.linkExample,
          )
        }
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
        isEnable = if (selectedTabIndex ==
          0
        ) {
          idTextState.isNotEmpty()
        } else {
          urlTextState.isNotEmpty()
        },
        onClick = {
          onCreateClicked.invoke(
            if (selectedTabIndex == 0) idTextState else urlTextState,
            selectedTabIndex == 0,
          )
        },
      )
    }
  }
}
