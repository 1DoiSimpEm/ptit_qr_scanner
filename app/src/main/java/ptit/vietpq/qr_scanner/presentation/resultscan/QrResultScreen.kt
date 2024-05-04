package ptit.vietpq.qr_scanner.presentation.resultscan

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarcodeSchema
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BaseBarcodeModel
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema.CalendarEvent
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema.ContactInfoContent
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema.EmailContent
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema.GeoPoint
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
import ptit.vietpq.qr_scanner.extension.addToCalendar
import ptit.vietpq.qr_scanner.extension.addToContacts
import ptit.vietpq.qr_scanner.extension.callFromDailer
import ptit.vietpq.qr_scanner.extension.openDirection
import ptit.vietpq.qr_scanner.extension.openFacebook
import ptit.vietpq.qr_scanner.extension.openInstagram
import ptit.vietpq.qr_scanner.extension.openLocationMap
import ptit.vietpq.qr_scanner.extension.openLocationMapWithDestination
import ptit.vietpq.qr_scanner.extension.openSpotify
import ptit.vietpq.qr_scanner.extension.openTelegram
import ptit.vietpq.qr_scanner.extension.openTiktok
import ptit.vietpq.qr_scanner.extension.openUrl
import ptit.vietpq.qr_scanner.extension.openWhatsApp
import ptit.vietpq.qr_scanner.extension.openX
import ptit.vietpq.qr_scanner.extension.openYoutube
import ptit.vietpq.qr_scanner.extension.searchWeb
import ptit.vietpq.qr_scanner.extension.sendEmail
import ptit.vietpq.qr_scanner.extension.sendSmsOrMms
import ptit.vietpq.qr_scanner.extension.setClipboard
import ptit.vietpq.qr_scanner.extension.shareText
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.presentation.main.QrAppViewModel
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale
import ptit.vietpq.qr_scanner.presentation.resultscan.component.ActionOptionModel
import ptit.vietpq.qr_scanner.presentation.resultscan.component.ActionOptionModel.Companion.calendarListAction
import ptit.vietpq.qr_scanner.presentation.resultscan.component.ActionOptionModel.Companion.contactsListAction
import ptit.vietpq.qr_scanner.presentation.resultscan.component.ActionOptionModel.Companion.emailListAction
import ptit.vietpq.qr_scanner.presentation.resultscan.component.ActionOptionModel.Companion.geoPointListAction
import ptit.vietpq.qr_scanner.presentation.resultscan.component.ActionOptionModel.Companion.phoneListAction
import ptit.vietpq.qr_scanner.presentation.resultscan.component.ActionOptionModel.Companion.smsListAction
import ptit.vietpq.qr_scanner.presentation.resultscan.component.ActionOptionModel.Companion.textListAction
import ptit.vietpq.qr_scanner.presentation.resultscan.component.ActionOptionModel.Companion.urlBookmarkAction
import ptit.vietpq.qr_scanner.presentation.resultscan.component.ActionOptionModel.Companion.wifiListAction
import ptit.vietpq.qr_scanner.presentation.resultscan.component.ActionScan
import ptit.vietpq.qr_scanner.presentation.resultscan.component.MenuOptionIndex
import ptit.vietpq.qr_scanner.presentation.resultscan.component.content.CalendarContentView
import ptit.vietpq.qr_scanner.presentation.resultscan.component.content.ContactsContentView
import ptit.vietpq.qr_scanner.presentation.resultscan.component.content.EmailContentView
import ptit.vietpq.qr_scanner.presentation.resultscan.component.content.MapContentView
import ptit.vietpq.qr_scanner.presentation.resultscan.component.content.MessageContentView
import ptit.vietpq.qr_scanner.presentation.resultscan.component.content.PhoneContentView
import ptit.vietpq.qr_scanner.presentation.resultscan.component.content.SocialContentView
import ptit.vietpq.qr_scanner.presentation.resultscan.component.content.TextContentView
import ptit.vietpq.qr_scanner.presentation.resultscan.component.content.UrlBookmarkContent
import ptit.vietpq.qr_scanner.presentation.resultscan.component.content.WifiContentView
import timber.log.Timber

@Composable
fun QrCodeResultRoute(
    mainViewModel: QrAppViewModel,
    viewModel: QrCodeResultViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
) {
    val uiState: ResultUiState by viewModel.uiStateResult.collectAsStateWithLifecycle()
    val context = LocalContext.current


    val rememberUpdateOnBackPress by rememberUpdatedState(newValue = onBackPressed)

    val loadingStateAds by mainViewModel.stateLoading.collectAsStateWithLifecycle()

    val activity = LocalContext.current as Activity

    QrResultScreen(
        loadingStateAds = loadingStateAds,
        uiState = uiState,
        context = context,
        onBackPressed = {
            rememberUpdateOnBackPress()
        },
        onAction = { actionModel: ActionOptionModel, typeSchema: BarcodeSchema, modelQrCode: BaseBarcodeModel ->

            when (actionModel.id) {
                MenuOptionIndex.OPEN_URL -> {
                    openUrlBookmark(typeSchema, modelQrCode, context, uiState)
                }

                MenuOptionIndex.COPY -> {
                    context.setClipboard(uiState.textRaw ?: "")
                }

                MenuOptionIndex.CONNECT_WIFI -> {
                    val wifiContent = modelQrCode as WifiContent
                    viewModel.wifiConnect(
                        context = context,
                        authType = wifiContent.encryptionType,
                        name = wifiContent.ssid,
                        password = wifiContent.password,
                        isHidden = wifiContent.isHidden ?: false,
                        anonymousIdentity = wifiContent.anonymousIdentity ?: "",
                        identity = wifiContent.identity ?: "",
                        eapMethod = wifiContent.eapMethod ?: "",
                        phase2Method = wifiContent.phase2Method ?: "",
                    )
                }

                MenuOptionIndex.COPY_PASSWORD_WIFI -> {
                    val wifiContent = modelQrCode as WifiContent
                    context.setClipboard(wifiContent.password)
                }

                MenuOptionIndex.ADD_CONTACT -> {
                    addContactsCase(typeSchema, modelQrCode, context)
                }

                MenuOptionIndex.CALL_PHONE -> {
                    callPhone(typeSchema, modelQrCode, context)
                }

                MenuOptionIndex.DIRECTION -> {
                    openDirection(typeSchema, modelQrCode, context)
                }

                MenuOptionIndex.SHARE -> {
                    context.shareText(uiState.textRaw ?: "")
                }

                MenuOptionIndex.SEARCH_WEB -> {
                    context.searchWeb(uiState.textRaw ?: "")
                }

                MenuOptionIndex.SEND_MAIL -> {
                    sendEmail(typeSchema, modelQrCode, context)
                }

                MenuOptionIndex.SEND_SMS -> {
                    sendSMS(modelQrCode, context)
                }

                MenuOptionIndex.SHOW_ON_MAP -> {
                    val geoPoint = modelQrCode as GeoPoint
                    context.openLocationMapWithDestination(
                        geoPoint.lat.toString(),
                        geoPoint.lng.toString()
                    )
                }

                MenuOptionIndex.ADD_CALENDAR_EVENT -> {
                    val calendarEvent = modelQrCode as CalendarEvent
                    context.addToCalendar(
                        eventSummary = calendarEvent.summary,
                        eventDescription = calendarEvent.description,
                        eventLocation = calendarEvent.location,
                        eventStartDate = calendarEvent.start,
                        eventEndDate = calendarEvent.end,
                    )
                }
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrResultScreen(
    loadingStateAds: Boolean,
    uiState: ResultUiState,
    context: Context,
    onBackPressed: () -> Unit,
    onAction: (actionModel: ActionOptionModel, typeSchema: BarcodeSchema, modelQrCode: BaseBarcodeModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        contentColor = QrCodeTheme.color.primary,
        containerColor = QrCodeTheme.color.primary,
        modifier = modifier,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = QrCodeTheme.color.primary,
                ),
                title = {
                    Text(
                        text = QrLocale.strings.resultScan,
                        color = QrCodeTheme.color.background
                    )
                },
                actions = {
                    Row(horizontalArrangement = Arrangement.End) {
                        IconButton(
                            onClick = onBackPressed,
                        ) {
                            Icon(
                                painterResource(id = R.drawable.ic_scan_single),
                                contentDescription = null,
                                tint = QrCodeTheme.color.background,
                            )
                        }
                    }
                },
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(top = innerPadding.calculateTopPadding())
                    .clip(shape = RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
                    .background(color = QrCodeTheme.color.background)
                    .fillMaxWidth()
                    .fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                ) {
                    Spacer(modifier = Modifier.height(24.dp))

                    when (uiState.barCodeTypeSchema) {
                        BarcodeSchema.TYPE_CONTACT_INFO -> {
                            val contacts = uiState.baseCodeResult as ContactInfoContent

                            ContactsContentView(contacts = contacts)

                            DividerContent()

                            ActionScan(
                                listAction = contactsListAction(context),
                                onClickAction = { actionOptionModel ->
                                    onAction(
                                        actionOptionModel,
                                        BarcodeSchema.TYPE_CONTACT_INFO,
                                        contacts
                                    )
                                },
                            )
                        }

                        BarcodeSchema.GEO -> {
                            val mapContent = uiState.baseCodeResult as GeoPoint
                            MapContentView(geoPoint = mapContent)

                            DividerContent()

                            ActionScan(
                                listAction = geoPointListAction(context),
                                onClickAction = {
                                    onAction(it, BarcodeSchema.GEO, mapContent)
                                },
                            )
                        }

                        BarcodeSchema.TYPE_CALENDAR_EVENT -> {
                            val calendarEvent = uiState.baseCodeResult as CalendarEvent

                            CalendarContentView(calendarEvent = calendarEvent)

                            DividerContent()

                            ActionScan(
                                listAction = calendarListAction(context),
                                onClickAction = {
                                    onAction(it, BarcodeSchema.TYPE_CALENDAR_EVENT, calendarEvent)
                                },
                            )
                        }

                        BarcodeSchema.EMAIL -> {
                            val emailContent = uiState.baseCodeResult as EmailContent

                            EmailContentView(emailContent = emailContent)

                            DividerContent()

                            ActionScan(
                                listAction = emailListAction(context),
                                onClickAction = {
                                    onAction(it, BarcodeSchema.EMAIL, emailContent)
                                },
                            )
                        }

                        BarcodeSchema.PHONE -> {
                            val phone = uiState.baseCodeResult as Phone

                            PhoneContentView(phone = phone)

                            DividerContent()

                            ActionScan(
                                listAction = phoneListAction(context),
                                onClickAction = {
                                    onAction(it, BarcodeSchema.PHONE, phone)
                                },
                            )
                        }

                        BarcodeSchema.SMS -> {
                            val smsContent = uiState.baseCodeResult as SmsContent

                            MessageContentView(smsContent = smsContent)

                            DividerContent()

                            ActionScan(
                                listAction = smsListAction(context),
                                onClickAction = {
                                    onAction(it, BarcodeSchema.SMS, smsContent)
                                },
                            )
                        }

                        BarcodeSchema.BOOKMARK_URL -> {
                            val bookmark = uiState.baseCodeResult as UrlBookmark

                            UrlBookmarkContent(textUrl = bookmark.url)

                            DividerContent()

                            ActionScan(
                                listAction = urlBookmarkAction(context),
                                onClickAction = {
                                    onAction(it, BarcodeSchema.BOOKMARK_URL, bookmark)
                                },
                            )
                        }

                        BarcodeSchema.WIFI -> {
                            val wifiContent = uiState.baseCodeResult as WifiContent

                            WifiContentView(wifiContent = wifiContent)

                            DividerContent()

                            ActionScan(
                                listAction = wifiListAction(context),
                                onClickAction = {
                                    onAction(it, BarcodeSchema.WIFI, wifiContent)
                                },
                            )
                        }

                        BarcodeSchema.TYPE_FACEBOOK -> {
                            val faceBookContent = uiState.baseCodeResult as FacebookProfile

                            SocialContentView(
                                url = faceBookContent.profileId,
                                typeSchema = BarcodeSchema.TYPE_FACEBOOK
                            )

                            DividerContent()

                            ActionScan(
                                listAction = urlBookmarkAction(context),
                                onClickAction = {
                                    onAction(it, BarcodeSchema.TYPE_FACEBOOK, faceBookContent)
                                },
                            )
                        }

                        BarcodeSchema.TYPE_INSTAGRAM -> {
                            val instagramProfile = uiState.baseCodeResult as InstagramProfile

                            SocialContentView(
                                url = instagramProfile.username,
                                typeSchema = BarcodeSchema.TYPE_INSTAGRAM
                            )

                            DividerContent()

                            ActionScan(
                                listAction = urlBookmarkAction(context),
                                onClickAction = {
                                    onAction(it, BarcodeSchema.TYPE_INSTAGRAM, instagramProfile)
                                },
                            )
                        }

                        BarcodeSchema.TYPE_SPOTIFY -> {
                            val spotifyProfile = uiState.baseCodeResult as SpotifyProfile

                            SocialContentView(
                                url = spotifyProfile.username,
                                typeSchema = BarcodeSchema.TYPE_SPOTIFY
                            )

                            DividerContent()

                            ActionScan(
                                listAction = urlBookmarkAction(context),
                                onClickAction = { actionOptionModel ->
                                    onAction(
                                        actionOptionModel,
                                        BarcodeSchema.TYPE_SPOTIFY,
                                        spotifyProfile
                                    )
                                },
                            )
                        }

                        BarcodeSchema.TYPE_TELEGRAM -> {
                            val telegramProfile = uiState.baseCodeResult as TelegramProfile

                            SocialContentView(
                                url = telegramProfile.username,
                                typeSchema = BarcodeSchema.TYPE_TELEGRAM
                            )

                            DividerContent()

                            ActionScan(
                                listAction = urlBookmarkAction(context),
                                onClickAction = { actionOptionModel ->
                                    onAction(
                                        actionOptionModel,
                                        BarcodeSchema.TYPE_TELEGRAM,
                                        telegramProfile
                                    )
                                },
                            )
                        }

                        BarcodeSchema.TYPE_TIKTOK -> {
                            val tikTokProfile = uiState.baseCodeResult as TikTokProfile

                            SocialContentView(
                                url = tikTokProfile.username,
                                typeSchema = BarcodeSchema.TYPE_TIKTOK
                            )

                            DividerContent()

                            ActionScan(
                                listAction = urlBookmarkAction(context),
                                onClickAction = { actionOptionModel ->
                                    onAction(
                                        actionOptionModel,
                                        BarcodeSchema.TYPE_TIKTOK,
                                        tikTokProfile
                                    )
                                },
                            )
                        }

                        BarcodeSchema.TYPE_TWITTER -> {
                            val twitterProfile = uiState.baseCodeResult as TwitterProfile

                            SocialContentView(
                                url = twitterProfile.username,
                                typeSchema = BarcodeSchema.TYPE_TWITTER
                            )

                            DividerContent()

                            ActionScan(
                                listAction = urlBookmarkAction(context),
                                onClickAction = { actionOptionModel ->
                                    onAction(
                                        actionOptionModel,
                                        BarcodeSchema.TYPE_TWITTER,
                                        twitterProfile
                                    )
                                },
                            )
                        }

                        BarcodeSchema.TYPE_WHATSAPP -> {
                            val whatsappProfile = uiState.baseCodeResult as WhatsappProfile

                            SocialContentView(
                                url = whatsappProfile.phoneNumber,
                                typeSchema = BarcodeSchema.TYPE_WHATSAPP
                            )

                            DividerContent()

                            ActionScan(
                                listAction = urlBookmarkAction(context),
                                onClickAction = { actionOptionModel ->
                                    onAction(
                                        actionOptionModel,
                                        BarcodeSchema.TYPE_WHATSAPP,
                                        whatsappProfile
                                    )
                                },
                            )
                        }

                        BarcodeSchema.TYPE_YOUTUBE -> {
                            val youtubeProfile = uiState.baseCodeResult as YoutubeProfile

                            SocialContentView(
                                url = youtubeProfile.username,
                                typeSchema = BarcodeSchema.TYPE_YOUTUBE
                            )

                            DividerContent()

                            ActionScan(
                                listAction = urlBookmarkAction(context),
                                onClickAction = { actionOptionModel ->
                                    onAction(
                                        actionOptionModel,
                                        BarcodeSchema.TYPE_YOUTUBE,
                                        youtubeProfile
                                    )
                                },
                            )
                        }

                        BarcodeSchema.OTHER -> {
                            val textContent = uiState.baseCodeResult as TextContent

                            TextContentView(text = textContent.content)

                            DividerContent()

                            ActionScan(
                                listAction = textListAction(context),
                                onClickAction = { actionOptionModel ->
                                    onAction(actionOptionModel, BarcodeSchema.OTHER, textContent)
                                },
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Spacer(modifier = Modifier.height(26.dp))

                    if (uiState.imageCaptureQr != null) {
                        Image(
                            modifier = Modifier
                                .size(148.dp)
                                .align(alignment = CenterHorizontally)
                                .border(5.dp, QrCodeTheme.color.backgroundImage),
                            bitmap = uiState.imageCaptureQr.asImageBitmap(),
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds,
                        )
                    }
                }
            }
        },
    )
}

private fun sendSMS(modelQrCode: BaseBarcodeModel, context: Context) {
    val smsContent = modelQrCode as SmsContent
    context.sendSmsOrMms(phone = smsContent.phoneNumber, smsBody = smsContent.message)
}

private fun callPhone(typeSchema: BarcodeSchema, modelQrCode: BaseBarcodeModel, context: Context) {
    when (typeSchema) {
        BarcodeSchema.TYPE_CONTACT_INFO -> {
            val contactInfoContent = modelQrCode as ContactInfoContent
            Timber.tag("QrResultScreen").d("Call Phone: ${contactInfoContent.phone}")
            context.callFromDailer(contactInfoContent.phone)
        }

        BarcodeSchema.PHONE -> {
            val contactInfoContent = modelQrCode as Phone
            context.callFromDailer(contactInfoContent.phone)
        }

        else -> {}
    }
}

private fun addContactsCase(
    typeSchema: BarcodeSchema,
    modelQrCode: BaseBarcodeModel,
    context: Context
) {
    when (typeSchema) {
        BarcodeSchema.TYPE_CONTACT_INFO -> {
            val contactInfoContent = modelQrCode as ContactInfoContent
            context.addToContacts(
                firstName = contactInfoContent.firstName,
                lastName = contactInfoContent.lastName,
                organization = contactInfoContent.organization,
                phone = contactInfoContent.phone,
                phoneType = contactInfoContent.phoneType,
                secondaryPhone = contactInfoContent.secondaryPhone,
                secondaryPhoneType = contactInfoContent.secondaryPhoneType,
                tertiaryPhone = contactInfoContent.tertiaryPhone,
                tertiaryPhoneType = contactInfoContent.tertiaryPhoneType,
                email = contactInfoContent.email,
                emailType = contactInfoContent.emailType,
                secondaryEmail = contactInfoContent.secondaryEmail,
                secondaryEmailType = contactInfoContent.secondaryEmailType,
                tertiaryEmail = contactInfoContent.tertiaryEmail,
                tertiaryEmailType = contactInfoContent.tertiaryEmailType,
            )
        }

        BarcodeSchema.PHONE -> {
            val contactInfoContent = modelQrCode as Phone
            context.addToContacts(
                phone = contactInfoContent.phone,
            )
        }

        else -> {
            val emailContent = modelQrCode as EmailContent
            context.addToContacts(
                email = emailContent.address,
            )
        }
    }
}

private fun sendEmail(typeSchema: BarcodeSchema, modelQrCode: BaseBarcodeModel, context: Context) {
    when (typeSchema) {
        BarcodeSchema.EMAIL -> {
            val emailContent = modelQrCode as EmailContent
            context.sendEmail(
                email = emailContent.address,
                emailSubject = emailContent.subject,
                emailBody = emailContent.body,
            )
        }

        BarcodeSchema.TYPE_CALENDAR_EVENT -> {
            val calendarEvent = modelQrCode as CalendarEvent
            context.sendEmail(
                email = calendarEvent.organizer,
                emailSubject = calendarEvent.summary,
                emailBody = calendarEvent.description,
            )
        }

        BarcodeSchema.TYPE_CONTACT_INFO -> {
            val contactInfoContent = modelQrCode as ContactInfoContent
            context.sendEmail(
                email = contactInfoContent.email,
                emailSubject = contactInfoContent.title,
                emailBody = contactInfoContent.contentInfo,
            )
        }

        else -> {}
    }
}

private fun openUrlBookmark(
    typeSchema: BarcodeSchema,
    modelQrCode: BaseBarcodeModel,
    context: Context,
    uiState: ResultUiState,
) {
    when (typeSchema) {
        BarcodeSchema.TYPE_FACEBOOK -> {
            val faceBookContent = modelQrCode as FacebookProfile
            context.openFacebook(faceBookContent.profileId)
        }

        BarcodeSchema.TYPE_INSTAGRAM -> {
            val instagramProfile = modelQrCode as InstagramProfile
            context.openInstagram(instagramProfile.username)
        }

        BarcodeSchema.TYPE_SPOTIFY -> {
            val spotifyProfile = modelQrCode as SpotifyProfile
            context.openSpotify(spotifyProfile.username)
        }

        BarcodeSchema.TYPE_TELEGRAM -> {
            val telegramProfile = modelQrCode as TelegramProfile
            context.openTelegram(telegramProfile.username)
        }

        BarcodeSchema.TYPE_TIKTOK -> {
            val tikTokProfile = modelQrCode as TikTokProfile
            context.openTiktok(tikTokProfile.username)
        }

        BarcodeSchema.TYPE_TWITTER -> {
            val twitterProfile = modelQrCode as TwitterProfile
            context.openX(twitterProfile.username)
        }

        BarcodeSchema.TYPE_WHATSAPP -> {
            val whatsappProfile = modelQrCode as WhatsappProfile
            context.openWhatsApp(whatsappProfile.phoneNumber)
        }

        BarcodeSchema.TYPE_YOUTUBE -> {
            val youtubeProfile = modelQrCode as YoutubeProfile
            context.openYoutube(youtubeProfile.username)
        }

        else -> {
            context.openUrl(uiState.textRaw ?: "")
        }
    }
}

private fun openDirection(
    typeSchema: BarcodeSchema,
    modelQrCode: BaseBarcodeModel,
    context: Context
) {
    if (typeSchema == BarcodeSchema.GEO) {
        val address = modelQrCode as GeoPoint
        context.openDirection(
            latitude = address.lat.toString(),
            longitude = address.lng.toString(),
        )
    } else if (typeSchema == BarcodeSchema.TYPE_CONTACT_INFO) {
        val contactInfoContent = modelQrCode as ContactInfoContent
        context.openLocationMap(contactInfoContent.address)
    }
}

@Composable
private fun DividerContent() {
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp),
    )
}
