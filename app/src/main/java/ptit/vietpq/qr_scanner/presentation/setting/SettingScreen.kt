package ptit.vietpq.qr_scanner.presentation.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ptit.vietpq.qr_scanner.BuildConfig
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale
import ptit.vietpq.qr_scanner.extension.openBrowser
import ptit.vietpq.qr_scanner.extension.startFeedback
import ptit.vietpq.qr_scanner.presentation.setting.component.BeepItem
import ptit.vietpq.qr_scanner.presentation.setting.component.FeedbackItem
import ptit.vietpq.qr_scanner.presentation.setting.component.LanguageItem
import ptit.vietpq.qr_scanner.presentation.setting.component.PrivacyItem
import ptit.vietpq.qr_scanner.presentation.setting.component.VersionItem
import ptit.vietpq.qr_scanner.presentation.setting.component.VibrateItem

const val URL_PRIVACY_POLICY = "https://begamob.com/bega-policy.html"

@Composable
fun SettingsRoute(onLanguageNavigating: () -> Unit, viewModel: SettingViewModel = hiltViewModel()) {

  SettingScreen(
    enableBeep = viewModel.isBeepEnable,
    enableVibrate = viewModel.isVibrateEnable,
    currentLanguage = viewModel.currentLanguage,
    onBeepSwitched = { viewModel.isBeepEnable = it },
    onVibrateSwitched = { viewModel.isVibrateEnable = it },
    onLanguageNavigating = onLanguageNavigating,
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
  onLanguageNavigating: () -> Unit,
  enableBeep: Boolean,
  onBeepSwitched: (Boolean) -> Unit,
  enableVibrate: Boolean,
  onVibrateSwitched: (Boolean) -> Unit,
  currentLanguage: String,
  modifier: Modifier = Modifier,
) {
  val context = LocalContext.current
  var beepSwitched by rememberSaveable { mutableStateOf(enableBeep) }
  var vibrateSwitched by rememberSaveable { mutableStateOf(enableVibrate) }

  Scaffold(
    modifier = modifier,
    contentColor = QrCodeTheme.color.primary,
    containerColor = QrCodeTheme.color.backgroundCard,
    topBar = {
      TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = QrCodeTheme.color.backgroundCard,
          titleContentColor = QrCodeTheme.color.neutral1,
        ),
        title = {
          Text(text = QrLocale.strings.settings, style = QrCodeTheme.typo.heading, color = QrCodeTheme.color.neutral1)
        },
      )
    },
    content = { innerPadding ->
      Column(
        modifier = Modifier.padding(
          top = innerPadding.calculateTopPadding(),
          start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
          end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
        ),
      ) {
        Text(
          modifier = Modifier.padding(start = 16.dp),
          text = QrLocale.strings.general,
          color = QrCodeTheme.color.neutral1,
          style = QrCodeTheme.typo.button,
        )
        Column(
          modifier = Modifier
            .padding(
              top = 8.dp,
              start = 16.dp,
              end = 16.dp,
            )
            .clip(
              shape = QrCodeTheme.shape.roundedLarge,
            )
            .background(QrCodeTheme.color.neutral5),
        ) {
          BeepItem(
            modifier = Modifier.padding(16.dp),
            value = beepSwitched,
            onSwitched = {
              beepSwitched = it
              onBeepSwitched(it)
            },
          )
          VibrateItem(
            modifier = Modifier.padding(
              start = 16.dp,
              end = 16.dp,
              bottom = 16.dp,
            ),
            value = vibrateSwitched,
            onSwitched = {
              vibrateSwitched = it
              onVibrateSwitched(it)
            },
          )
          LanguageItem(
            langText = currentLanguage,
            modifier = Modifier
              .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp,
              )
              .clickable { onLanguageNavigating() },
          )
        }
        Text(
          modifier = Modifier.padding(
            top = 16.dp,
            start = 16.dp,
          ),
          text = QrLocale.strings.advanced,
          color = QrCodeTheme.color.neutral1,
          style = QrCodeTheme.typo.button,
        )
        Column(
          modifier = Modifier
            .padding(
              top = 8.dp,
              start = 16.dp,
              end = 16.dp,
            )
            .clip(shape = QrCodeTheme.shape.roundedLarge)
            .background(QrCodeTheme.color.neutral5),
        ) {
          FeedbackItem(
            modifier = Modifier
              .padding(16.dp)
              .clickable {
                context.startFeedback()
              },
          )
          PrivacyItem(
            modifier = Modifier
              .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp,
              )
              .clickable {
                context.openBrowser(URL_PRIVACY_POLICY)
              },
          )
          VersionItem(
            modifier = Modifier.padding(
              start = 16.dp,
              end = 16.dp,
              bottom = 16.dp,
            ),
            version = BuildConfig.VERSION_NAME,
          )
        }
      }
    },
  )
}
