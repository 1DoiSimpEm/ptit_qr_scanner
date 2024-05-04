package ptit.vietpq.qr_scanner.presentation.language

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.Language
import kotlinx.collections.immutable.ImmutableList
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale
import ptit.vietpq.qr_scanner.extension.restartApp
import ptit.vietpq.qr_scanner.extension.setCurrentLanguage
import ptit.vietpq.qr_scanner.ui.common.scaleBounce

@Composable
internal fun LanguageRoute(
    onLanguageFinished: () -> Unit,
    onBackPressed: () -> Unit,
    viewModel: LanguageViewModel = hiltViewModel(),
) {
  val context = LocalContext.current

  val activity = LocalContext.current as Activity

  val rememberLanguageFinished by rememberUpdatedState(newValue = onLanguageFinished)

  val uiLanguageState by viewModel.stateLanguage.collectAsStateWithLifecycle()


  LanguageScreen(
    languageCode = viewModel.languageCode,
    isFirstTime = viewModel.onboardLanguagePassed,
    loadingState = uiLanguageState,
    onBackPressed = onBackPressed,
    onLanguageFinished = {
      viewModel.languageCode = it.code
      context.setCurrentLanguage(it.code)
      if (!viewModel.onboardLanguagePassed) {
        viewModel.onboardLanguagePassed = true
      }
      rememberLanguageFinished()
    },
    onLanguageChanged = {
      viewModel.languageCode = it.code
      context.setCurrentLanguage(it.code)
    },
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageScreen(
  languageCode: String,
  isFirstTime: Boolean,
  loadingState: Boolean,
  onBackPressed: () -> Unit,
  onLanguageChanged: (Language) -> Unit,
  onLanguageFinished: (Language) -> Unit,
  modifier: Modifier = Modifier,
) {
  val context = LocalContext.current
  val selectedLanguage = rememberSaveable {
    mutableStateOf(
      Language.languageList.firstOrNull { it.code == languageCode } ?: Language.languageList.first(),
    )
  }
  BackHandler {
    onBackPressed.invoke()
  }

  Scaffold(
    modifier = modifier,
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = QrLocale.strings.languages,
            textAlign = TextAlign.Center,
            color = QrCodeTheme.color.neutral1,
            style = QrCodeTheme.typo.heading,
          )
        },
        navigationIcon = {
          if (isFirstTime) {
            Image(
              painter = painterResource(id = R.drawable.ic_back),
              contentDescription = null,
              modifier = Modifier
                .clickable {
                  onBackPressed.invoke()
                }
                .padding(QrCodeTheme.dimen.marginDefault),
            )
          }
        },
        actions = {
          Button(
            modifier = Modifier
              .padding(end = QrCodeTheme.dimen.marginSmall)
              .scaleBounce(),
            colors = ButtonDefaults.buttonColors(containerColor = QrCodeTheme.color.primary),
            onClick = {
              if (isFirstTime) {
                onLanguageChanged.invoke(selectedLanguage.value)
                context.restartApp()
              } else {
                onLanguageFinished.invoke(selectedLanguage.value)
              }
            },
          ) {
            Text(text = QrLocale.strings.done, color = QrCodeTheme.color.neutral5, style = QrCodeTheme.typo.body)
          }
        },
      )
    },
    content = { innerPadding ->
      Column(
        modifier = Modifier
          .padding(
            top = innerPadding.calculateTopPadding(),
            bottom = innerPadding.calculateBottomPadding(),
          )
          .background(QrCodeTheme.color.backgroundCard),
      ) {
        Box(
          modifier = Modifier
            .fillMaxSize()
            .weight(1f)
            .padding(horizontal = 16.dp),
        ) {
          LanguageList(
            modifier = Modifier
              .fillMaxSize(),
            languages = Language.languageList,
            selectedLanguage = selectedLanguage.value,
            onLanguageSelected = {
              selectedLanguage.value = it
            },
          )
        }
      }
    },
  )
}

@Composable
fun LanguageList(
  languages: ImmutableList<Language>,
  selectedLanguage: Language?,
  onLanguageSelected: (Language) -> Unit,
  modifier: Modifier = Modifier,
) {
  LazyColumn(
    modifier = modifier,
  ) {
    items(
      languages.size,
      key = {
        languages[it].code
      },
    ) { index ->
      LanguageItem(
        language = languages[index],
        isSelected = selectedLanguage == languages[index],
        onLanguageSelected = onLanguageSelected,
      )
    }
  }
}

@Preview
@Composable
private fun LanguageScreenPreview() {
  LanguageScreen(
    languageCode = "en",
    isFirstTime = true,
    onBackPressed = {},
    onLanguageChanged = {},
    onLanguageFinished = {},
    loadingState = true,
  )
}

@Composable
fun LanguageItem(
  language: Language,
  isSelected: Boolean,
  onLanguageSelected: (Language) -> Unit,
  modifier: Modifier = Modifier,
) {
  val backgroundColor by animateColorAsState(
    targetValue =
    if (isSelected) QrCodeTheme.color.primary else Color.White,
    animationSpec = TweenSpec(600),
    label = "",
  )
  val textColor by animateColorAsState(
    targetValue =
    if (isSelected) QrCodeTheme.color.neutral5 else QrCodeTheme.color.neutral1,
    animationSpec = TweenSpec(600),
    label = "",
  )
  Row(
    modifier = modifier
      .padding(vertical = QrCodeTheme.dimen.marginSmall)
      .height(QrCodeTheme.dimen.buttonHeightMedium)
      .clip(QrCodeTheme.shape.roundedDefault)
      .background(backgroundColor)
      .fillMaxSize()
      .clickable {
        onLanguageSelected.invoke(language)
      },
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Image(
      modifier = Modifier.padding(start = QrCodeTheme.dimen.marginDefault),
      painter = painterResource(id = language.flagRes),
      contentDescription = null,
    )
    Text(
      modifier = Modifier.padding(start = QrCodeTheme.dimen.marginDefault),
      text = language.nativeName,
      color = textColor,
      style = QrCodeTheme.typo.body,
    )
  }
}
