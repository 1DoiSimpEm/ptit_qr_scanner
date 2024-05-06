package ptit.vietpq.qr_scanner.presentation.create_result

import android.graphics.Bitmap
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarCodeResult
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarcodeSchema
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
import ptit.vietpq.qr_scanner.extension.shareBitmap
import ptit.vietpq.qr_scanner.extension.shareText
import ptit.vietpq.qr_scanner.extension.showToast
import ptit.vietpq.qr_scanner.extension.toCustomFormatted
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.presentation.main.QrAppViewModel
import io.github.alexzhirkevich.qrose.options.QrBallShape
import io.github.alexzhirkevich.qrose.options.QrBrush
import io.github.alexzhirkevich.qrose.options.QrCodeShape
import io.github.alexzhirkevich.qrose.options.QrColors
import io.github.alexzhirkevich.qrose.options.QrFrameShape
import io.github.alexzhirkevich.qrose.options.QrLogo
import io.github.alexzhirkevich.qrose.options.QrOptions
import io.github.alexzhirkevich.qrose.options.QrShapes
import io.github.alexzhirkevich.qrose.options.brush
import io.github.alexzhirkevich.qrose.options.solid
import io.github.alexzhirkevich.qrose.options.square
import io.github.alexzhirkevich.qrose.rememberQrCodePainter
import io.github.alexzhirkevich.qrose.toImageBitmap
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.data.mapper.imageDrawerByType
import ptit.vietpq.qr_scanner.data.mapper.toBarCodeModel
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale

@Composable
internal fun CreateQrResultRoute(
    mainViewModel: QrAppViewModel,
    onBackPressed: () -> Unit,
    onDoneButtonClicked: () -> Unit,
    onEditClicked: () -> Unit,
    sharedResultViewModel: CreateQrResultViewModel = viewModel(LocalContext.current as ComponentActivity),
) {
    val uiState: CreateQrResultUiState by sharedResultViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    CreateQrResultScreen(
        uiState = uiState,
        onBackPressed = {
            onBackPressed.invoke()
        },
        onDoneButtonClicked = {
            onDoneButtonClicked.invoke()
        },
        onEditClicked = {
            onEditClicked.invoke()
        },
        onSaveButtonPressed = {
            context.showToast(context.getString(R.string.saved_to_gallery))
            sharedResultViewModel.saveQrCode(it)
        },
        onShareClicked = {
            context.shareText(it)
        },
        onQrShareClicked = {
            context.shareBitmap(it)
        },
    )
}

@Preview
@Composable
private fun PreviewQr() {
    val qrPainter = rememberQrCodePainter(
        data = "https://www.google.com",
        options = QrOptions(
            shapes = QrShapes(
                code = QrCodeShape.Default,
                ball = QrBallShape.square(),
                frame = QrFrameShape.square(),
            ),

            colors = QrColors(
                dark = QrBrush.brush {
                    Brush.linearGradient(
                        0f to Color.Red,
                        1f to Color.Blue,
                        end = Offset(it, it),
                    )
                },
                ball = QrBrush.brush {
                    Brush.linearGradient(
                        0f to Color.Red,
                        1f to Color.Green,
                        start = Offset(0f, 0f),
                        end = Offset(100f, 100f),
                    )
                },
                frame = QrBrush.brush {
                    Brush.linearGradient(
                        0f to Color.Red,
                        1f to Color.Green,
                        start = Offset(0f, 0f),
                        end = Offset(100f, 100f),
                    )
                },
            ),
        ),
    )
    Box(
        modifier = Modifier
          .fillMaxSize()
          .background(Color.White),
    ) {
        Image(painter = qrPainter, contentDescription = null)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateQrResultScreen(
    onBackPressed: () -> Unit,
    onDoneButtonClicked: () -> Unit,
    onEditClicked: () -> Unit,
    onShareClicked: (String) -> Unit,
    onQrShareClicked: (Bitmap) -> Unit,
    onSaveButtonPressed: (ImageBitmap) -> Unit,
    uiState: CreateQrResultUiState,
    modifier: Modifier = Modifier,
) {
    val qrPainter = rememberQrCodePainter(
        data = uiState.result.text,
        options = QrOptions(
            shapes = QrShapes(
                code = QrCodeShape.Default,
                ball = uiState.qrShape.ball,
                frame = uiState.qrShape.frame,
            ),
            colors = QrColors(
                dark = QrBrush.solid(uiState.qrForegroundColor),
                ball = QrBrush.solid(uiState.qrForegroundColor),
                frame = QrBrush.solid(uiState.qrForegroundColor),
                light = QrBrush.solid(
                    uiState.qrBackgroundColor,
                ),
            ),
            logo = QrLogo(
                if (uiState.logo != null && uiState.logo != R.drawable.ic_none) painterResource(id = uiState.logo) else null,
            ),
        ),
    )
    val contentText = remember {
        getContentText(uiState.result)
    }
    BackHandler {
        onBackPressed.invoke()
    }

    Scaffold(
        modifier = modifier,
        contentColor = QrCodeTheme.color.primary,
        containerColor = QrCodeTheme.color.backgroundCard,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = QrCodeTheme.color.neutral5,
                ),
                title = {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = QrLocale.strings.result,
                        color = QrCodeTheme.color.neutral1,
                        style = QrCodeTheme.typo.innerBoldSize20LineHeight28,
                    )
                },
                actions = {
                    DoneButton(
                        modifier = Modifier.padding(end = 16.dp),
                        onClick = onDoneButtonClicked,
                    )
                },
                navigationIcon = {
                    Image(
                        modifier = Modifier
                          .padding(
                            start = 16.dp,
                          )
                          .clickable {
                            onBackPressed()
                          },
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = null,
                    )
                },
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                  .fillMaxSize()
                  .padding(
                    top = innerPadding.calculateTopPadding(),
                  ),
            ) {
                LazyColumn(
                    modifier = Modifier
                      .fillMaxWidth()
                      .padding(
                        top = QrCodeTheme.dimen.marginDefault,
                        start = QrCodeTheme.dimen.marginDefault,
                        end = QrCodeTheme.dimen.marginDefault,
                        bottom = 72.dp,
                      )
                      .clip(QrCodeTheme.shape.roundedLarge)
                      .background(QrCodeTheme.color.neutral5),
                ) {
                    item {
                        Row(modifier = Modifier.padding(QrCodeTheme.dimen.marginDefault)) {
                            Image(
                                modifier = Modifier.align(Alignment.CenterVertically),
                                painter = painterResource(id = uiState.result.imageDrawerByType()),
                                contentDescription = null,
                            )
                            Column(
                                modifier = Modifier
                                  .align(Alignment.CenterVertically)
                                  .padding(start = QrCodeTheme.dimen.marginDefault)
                                  .fillMaxWidth(),
                                verticalArrangement = Arrangement.Top,
                            ) {
                                Text(
                                    text = uiState.result.formattedText,
                                    style = QrCodeTheme.typo.body,
                                    color = QrCodeTheme.color.neutral3,
                                )
                                Text(
                                    text = contentText,
                                    style = QrCodeTheme.typo.subTitle,
                                    color = QrCodeTheme.color.neutral1,
                                )
                            }
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Card(
                                modifier = Modifier
                                    .wrapContentSize(),
                                colors = CardColors(
                                    containerColor = Color.Transparent,
                                    contentColor = QrCodeTheme.color.primary,
                                    disabledContainerColor = Color.White,
                                    disabledContentColor = QrCodeTheme.color.neutral1,
                                ),
                                shape = RoundedCornerShape(0.dp),
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = QrCodeTheme.color.neutral4,
                                ),
                            ) {
                                Image(
                                    modifier = Modifier
                                      .size(QrCodeTheme.dimen.qrCodeSize)
                                      .align(Alignment.CenterHorizontally)
                                      .padding(QrCodeTheme.dimen.marginDefault),
                                    painter = qrPainter,
                                    contentDescription = null,
                                )
                            }

                            Button(
                                onClick = {
                                    onEditClicked()
                                },
                                modifier = Modifier
                                    .padding(top = QrCodeTheme.dimen.marginDefault),
                                colors = ButtonDefaults.buttonColors(containerColor = QrCodeTheme.color.backgroundGreen),
                            ) {
                                Image(
                                    modifier = Modifier.padding(vertical = QrCodeTheme.dimen.marginVerySmall),
                                    painter = painterResource(id = R.drawable.ic_design),
                                    contentDescription = null,
                                )
                                Text(
                                    modifier = Modifier.padding(start = QrCodeTheme.dimen.marginPrettySmall),
                                    text = QrLocale.strings.editStyle,
                                    style = QrCodeTheme.typo.body,
                                    color = QrCodeTheme.color.primary,
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                              .padding(
                                top = QrCodeTheme.dimen.marginLarge,
                                bottom = QrCodeTheme.dimen.marginDefault,
                                start = QrCodeTheme.dimen.marginDefault,
                                end = QrCodeTheme.dimen.marginDefault,
                              )
                              .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            FeaturedCardItem(
                                content = QrLocale.strings.save,
                                drawableIdRes = R.drawable.ic_create_save
                            ) {
                                onSaveButtonPressed.invoke(qrPainter.toImageBitmap(480, 480))
                            }
                            FeaturedCardItem(
                                content = QrLocale.strings.share,
                                drawableIdRes = R.drawable.ic_share
                            ) {
                                onShareClicked.invoke(uiState.result.text)
                            }
                            FeaturedCardItem(
                                content = QrLocale.strings.shareQR,
                                drawableIdRes = R.drawable.ic_share_qr
                            ) {
                                onQrShareClicked.invoke(
                                    qrPainter.toImageBitmap(480, 480).asAndroidBitmap()
                                )
                            }
                        }
                    }
                }
            }
        },
    )
}

@Composable
fun FeaturedCardItem(
    content: String,
    drawableIdRes: Int,
    modifier: Modifier = Modifier,
    onItemClicked: () -> Unit
) {
    Column(
        modifier = modifier
          .clickable {
            onItemClicked.invoke()
          }
          .size(height = QrCodeTheme.dimen.featureHeight, width = QrCodeTheme.dimen.featureWidth),
        verticalArrangement = Arrangement.spacedBy(QrCodeTheme.dimen.marginPrettySmall),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = drawableIdRes),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
        )
        Text(
            text = content,
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            color = QrCodeTheme.color.neutral1,
            style = QrCodeTheme.typo.body,
        )
    }
}

@Composable
fun DoneButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = QrCodeTheme.color.primary),
    ) {
        Text(
            text = QrLocale.strings.done,
            style = QrCodeTheme.typo.body,
            color = QrCodeTheme.color.neutral5,
        )
    }
}

fun getContentText(result: BarCodeResult): String = when (result.typeSchema) {
    BarcodeSchema.BOOKMARK_URL -> {
        val url = result.toBarCodeModel() as? UrlBookmark
        buildString {
            append("URL: ${url?.url}\n")
            append("Title: ${url?.title}")
        }
    }

    BarcodeSchema.TYPE_CONTACT_INFO -> {
        val contactInfo = result.toBarCodeModel() as? ContactInfoContent
        buildString {
            contactInfo?.name?.let { append("Name: $it\n") }
            contactInfo?.organization?.let { append("Organization: $it\n") }
            contactInfo?.title?.let { append("Title: $it\n") }
            contactInfo?.email?.let { append("Email: $it\n") }
            contactInfo?.phone?.let { append("Phone: $it\n") }
            contactInfo?.address?.let { append("Address: $it") }
        }
    }

    BarcodeSchema.EMAIL -> {
        val email = result.toBarCodeModel() as? EmailContent
        buildString {
            append("Email: ${email?.address}\n")
            append("Subject: ${email?.subject}\n")
            append("Body: ${email?.body}")
        }
    }

    BarcodeSchema.PHONE -> {
        val phone = result.toBarCodeModel() as? Phone
        buildString {
            append("Phone: ${phone?.phone}")
        }
    }

    BarcodeSchema.SMS -> {
        val sms = result.toBarCodeModel() as? SmsContent
        buildString {
            append("Phone: ${sms?.phoneNumber}\n")
            append("Message: ${sms?.message}")
        }
    }

    BarcodeSchema.WIFI -> {
        val wifi = result.toBarCodeModel() as? WifiContent
        buildString {
            append("SSID: ${wifi?.ssid}\n")
            append("Password: ${wifi?.password}\n")
            append("Type: ${wifi?.encryptionType}")
        }
    }

    BarcodeSchema.TYPE_CALENDAR_EVENT -> {
        val calendar = result.toBarCodeModel() as? CalendarEvent
        buildString {
            append("Title: ${calendar?.summary}\n")
            append("Description: ${calendar?.description}\n")
            append("Start: ${calendar?.start?.toCustomFormatted()}\n")
            append("End: ${calendar?.end?.toCustomFormatted()}")
        }
    }

    BarcodeSchema.TYPE_FACEBOOK -> {
        val facebook = result.toBarCodeModel() as? FacebookProfile
        buildString {
            append("Profile: ${facebook?.profileId}")
        }
    }

    BarcodeSchema.TYPE_INSTAGRAM -> {
        val instagram = result.toBarCodeModel() as? InstagramProfile
        buildString {
            append("Profile: ${instagram?.username}")
        }
    }

    BarcodeSchema.TYPE_SPOTIFY -> {
        val spotify = result.toBarCodeModel() as? SpotifyProfile
        buildString {
            append("Profile: ${spotify?.username}")
        }
    }

    BarcodeSchema.TYPE_TELEGRAM -> {
        val telegram = result.toBarCodeModel() as? TelegramProfile
        buildString {
            append("Profile: ${telegram?.username}")
        }
    }

    BarcodeSchema.TYPE_TIKTOK -> {
        val tiktok = result.toBarCodeModel() as? TikTokProfile
        buildString {
            append("Profile: ${tiktok?.username}")
        }
    }

    BarcodeSchema.TYPE_TWITTER -> {
        val twitter = result.toBarCodeModel() as? TwitterProfile
        buildString {
            append("Profile: ${twitter?.username}")
        }
    }

    BarcodeSchema.TYPE_WHATSAPP -> {
        val whatsapp = result.toBarCodeModel() as? WhatsappProfile
        buildString {
            append("Profile: ${whatsapp?.phoneNumber}")
        }
    }

    BarcodeSchema.TYPE_YOUTUBE -> {
        val youtube = result.toBarCodeModel() as? YoutubeProfile
        buildString {
            append("Profile: ${youtube?.username}")
        }
    }

    BarcodeSchema.OTHER -> {
        val text = result.toBarCodeModel() as? TextContent
        buildString {
            append(text?.content)
        }
    }

    BarcodeSchema.GEO -> {
        val geo = result.toBarCodeModel() as? GeoPoint
        buildString {
            append("Latitude: ${geo?.lat}\n")
            append("Longitude: ${geo?.lng}")
        }
    }
}
