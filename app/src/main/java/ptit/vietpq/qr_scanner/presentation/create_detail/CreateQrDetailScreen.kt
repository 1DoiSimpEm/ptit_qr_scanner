package ptit.vietpq.qr_scanner.presentation.create_detail

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BaseBarcodeModel
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema.CalendarEvent
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema.ContactInfoContent
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema.EmailContent
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema.Phone
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema.SmsContent
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema.TextContent
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema.UrlBookmark
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema.WifiContent
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema.profile.FacebookProfile
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema.profile.InstagramProfile
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema.profile.SpotifyProfile
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema.profile.TelegramProfile
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema.profile.TikTokProfile
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema.profile.TwitterProfile
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema.profile.WhatsappProfile
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema.profile.YoutubeProfile
import ptit.vietpq.qr_scanner.presentation.create_detail.components.ChipGroup
import ptit.vietpq.qr_scanner.presentation.create_detail.contents.CalendarQR
import ptit.vietpq.qr_scanner.presentation.create_detail.contents.CreateContact
import ptit.vietpq.qr_scanner.presentation.create_detail.contents.CreateEmail
import ptit.vietpq.qr_scanner.presentation.create_detail.contents.CreateMessage
import ptit.vietpq.qr_scanner.presentation.create_detail.contents.CreateMyCard
import ptit.vietpq.qr_scanner.presentation.create_detail.contents.CreatePhone
import ptit.vietpq.qr_scanner.presentation.create_detail.contents.CreateSocial
import ptit.vietpq.qr_scanner.presentation.create_detail.contents.CreateText
import ptit.vietpq.qr_scanner.presentation.create_detail.contents.CreateWebsite
import ptit.vietpq.qr_scanner.presentation.create_detail.contents.CreateWhatsappQr
import ptit.vietpq.qr_scanner.presentation.create_detail.contents.CreateWifi
import ptit.vietpq.qr_scanner.presentation.create_result.CreateQrResultViewModel
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.presentation.main.QrAppViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale

@Composable
fun CreateQrDetailRoute(
  mainViewModel: QrAppViewModel,
  countryCode: String,
  onCountryCodeClicked: () -> Unit,
  onBackPressed: () -> Unit,
  onCreatePressed: () -> Unit,
  viewModel: CreateQrDetailViewModel = hiltViewModel(),
  resultSharedViewModel: CreateQrResultViewModel = viewModel(LocalContext.current as ComponentActivity),
) {
  val uiState: CreateQrDetailUiState by viewModel.uiState.collectAsStateWithLifecycle()


  CreateQrScreen(
    uiState = uiState,
    countryCode = countryCode,
    onBackPressed = {
                    onBackPressed()
    },
    onCountryCodeClicked = onCountryCodeClicked,
    onChipSelected = {
      viewModel.updateSelectedChip(it)
    },
    onCreatePressed = { content ->
      val result = uiState.selectedChip.toResult(content)
      resultSharedViewModel.onTypeChanged(result)
      resultSharedViewModel.insertHistory(result)
      onCreatePressed()
    },
  )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CreateQrScreen(
  uiState: CreateQrDetailUiState,
  countryCode: String,
  onBackPressed: () -> Unit,
  onChipSelected: (CreateQrType) -> Unit,
  onCountryCodeClicked: () -> Unit,
  onCreatePressed: (BaseBarcodeModel) -> Unit,
  modifier: Modifier = Modifier,
) {
  val pagerState =
    rememberPagerState(
      pageCount = { uiState.chips.size },
      initialPage = uiState.chips.indexOf(uiState.selectedChip),
    )
  val listState = rememberLazyListState()
  val coroutineScope = rememberCoroutineScope()

  LaunchedEffect(pagerState.currentPage) {
    val currentChip = uiState.chips.getOrNull(pagerState.currentPage) ?: return@LaunchedEffect
    onChipSelected.invoke(currentChip)
    listState.animateScrollToItem(pagerState.currentPage)
  }

  BackHandler {
    onBackPressed.invoke()
  }

  Scaffold(
    modifier = modifier,
    topBar = {
      TopAppBar(
        modifier = Modifier.padding(start = 4.dp),
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = QrCodeTheme.color.neutral5,
          titleContentColor = QrCodeTheme.color.primary,
        ),
        title = {
          Text(
            modifier = Modifier.padding(
              start = QrCodeTheme.dimen.marginDefault,
              end = QrCodeTheme.dimen.marginDefault,
            ),
            text = "${QrLocale.strings.createQr} ${stringResource(uiState.selectedChip.titleResId)}",
            style = QrCodeTheme.typo.heading,
            color = QrCodeTheme.color.neutral1,
          )
        },
        navigationIcon = {
          Image(
            modifier = Modifier.clickable {
              onBackPressed()
            },
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null,
          )
        },
      )
    },
    contentColor = QrCodeTheme.color.primary,
    containerColor = QrCodeTheme.color.backgroundCard,
    content = { innerPadding ->
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(top = innerPadding.calculateTopPadding()),
      ) {
        Column(
          modifier = Modifier.weight(1f),
        ) {
          if (uiState.selectedChip != CreateQrType.MyBusinessCard) {
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .background(QrCodeTheme.color.neutral5),
            ) {
              ChipGroup(
                modifier = Modifier
                  .fillMaxWidth(),
                chips = uiState.chips,
                currentSelected = uiState.selectedChip,
                onChipSelected = {
                  onChipSelected.invoke(it)
                  coroutineScope.launch {
                    pagerState.animateScrollToPage(uiState.chips.indexOf(it))
                  }
                },
                listState = listState,
              )
            }
          }
          HorizontalPager(
            modifier = Modifier
              .padding(top = QrCodeTheme.dimen.marginPrettySmall)
              .background(QrCodeTheme.color.backgroundCard),
            state = pagerState,
          ) { page ->
            when (uiState.chips[page]) {
              CreateQrType.Telephone -> {
                CreatePhone(
                  onCreateClicked = { phone ->
                    onCreatePressed.invoke(
                      Phone(phone),
                    )
                  },
                )
              }

              CreateQrType.MyBusinessCard -> {
                CreateMyCard(
                  onCreateClicked = { name, phoneNumber, email, address, company, dateOfBirth, description ->
                    onCreatePressed(
                      ContactInfoContent(
                        name = name,
                        phone = phoneNumber,
                        email = email,
                        address = address,
                        organization = company,
                        dateOfBirth = dateOfBirth,
                        description = description,
                      ),
                    )
                  },
                )
              }

              CreateQrType.Email -> {
                CreateEmail(
                  onEmailCreated = { email, subject, message ->
                    onCreatePressed.invoke(
                      EmailContent(
                        address = email,
                        subject = subject,
                        body = message,
                      ),
                    )
                  },
                )
              }

              CreateQrType.ContactInfo -> {
                CreateContact(
                  onContactCreated = { name, phone, email ->
                    onCreatePressed.invoke(
                      ContactInfoContent(
                        name = name,
                        phone = phone,
                        email = email,
                      ),
                    )
                  },
                )
              }

              CreateQrType.Website -> {
                CreateWebsite(
                  onWebsiteCreated = { content ->
                    onCreatePressed.invoke(
                      UrlBookmark(content, content),
                    )
                  },
                )
              }

              CreateQrType.Message -> {
                CreateMessage(
                  onMessageCreated = { phone, message ->
                    onCreatePressed.invoke(
                      SmsContent(
                        phoneNumber = phone,
                        message = message,
                      ),
                    )
                  },
                )
              }

              CreateQrType.Wifi -> {
                CreateWifi(
                  onCreateClicked = { ssid, password, securityType ->
                    onCreatePressed(
                      WifiContent(
                        ssid = ssid,
                        password = password,
                        encryptionType = securityType,
                      ),
                    )
                  },
                )
              }

              CreateQrType.Text -> {
                CreateText(
                  onCreateClicked = { content ->
                    onCreatePressed(
                      TextContent(content),
                    )
                  },
                )
              }

              CreateQrType.Calendar -> {
                CalendarQR(
                  onCreateClicked = { title, start, end, description ->
                    onCreatePressed(
                      CalendarEvent(
                        summary = title,
                        start = start,
                        end = end,
                        description = description,
                      ),
                    )
                  },
                )
              }

              CreateQrType.WhatsApp -> {
                CreateWhatsappQr(
                  onCountryCodeClicked = onCountryCodeClicked,
                  countryCodeState = countryCode,
                  onCreateClicked = {
                    onCreatePressed(WhatsappProfile(it))
                  },
                )
              }

              else -> {
                CreateSocial(
                  socialType = uiState.chips[page],
                  onCreateClicked = { content, isId ->
                    var stateContent = when (uiState.selectedChip) {
                      CreateQrType.Facebook -> FacebookProfile(content)
                      CreateQrType.Instagram -> InstagramProfile(content)
                      CreateQrType.Twitter -> TwitterProfile(content)
                      CreateQrType.Spotify -> SpotifyProfile(content)
                      CreateQrType.TikTok -> TikTokProfile(content)
                      CreateQrType.Telegram -> TelegramProfile(content)
                      CreateQrType.YouTube -> YoutubeProfile(content)
                      else -> InstagramProfile(content)
                    }
                    if (!isId) {
                      stateContent = UrlBookmark(content, content)
                    }
                    onCreatePressed(stateContent)
                  },
                )
              }
            }
          }
        }
      }
    },
  )
}

@Preview
@Composable
private fun CreateQrScreenPreview() {
  CreateQrScreen(
    uiState = CreateQrDetailUiState(
      chips = persistentListOf(
        CreateQrType.Telephone,
        CreateQrType.MyBusinessCard,
        CreateQrType.Email,
        CreateQrType.ContactInfo,
        CreateQrType.Website,
        CreateQrType.Message,
        CreateQrType.Wifi,
        CreateQrType.Text,
        CreateQrType.Calendar,
        CreateQrType.WhatsApp,
        CreateQrType.Facebook,
        CreateQrType.Instagram,
        CreateQrType.Twitter,
        CreateQrType.Spotify,
        CreateQrType.TikTok,
        CreateQrType.Telegram,
        CreateQrType.YouTube,

        ),
      selectedChip = CreateQrType.Telephone,
      false,
    ),
    countryCode = "+84",
    onBackPressed = {},
    onChipSelected = {},
    onCountryCodeClicked = {},
    onCreatePressed = {},
  )
}
