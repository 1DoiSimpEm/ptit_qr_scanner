package ptit.vietpq.qr_scanner.presentation.create_detail

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarCodeResult
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarcodeFormat
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarcodeSchema
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BaseBarcodeModel
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.Schema
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import ptit.vietpq.qr_scanner.R

@Immutable
@Stable
sealed interface CategoryType {
  data object MyBusinessCard : CategoryType
  data object Personal : CategoryType
  data object Social : CategoryType
  data object Utility : CategoryType
}

@Immutable
@Stable
sealed interface CreateQrType {
  val categoryType: CategoryType
  val titleResId: Int
  val imageResId: Int
  val trackingName: String
  val schema: BarcodeSchema
  val format: BarcodeFormat

  data object MyBusinessCard : CreateQrType {
    override val categoryType: CategoryType = CategoryType.MyBusinessCard
    override val titleResId: Int = R.string.my_business_card
    override val imageResId: Int = R.drawable.ic_my_business_card
    override val trackingName: String = "qr_my_business_card"
    override val schema: BarcodeSchema = BarcodeSchema.TYPE_CONTACT_INFO
    override val format: BarcodeFormat = BarcodeFormat.FORMAT_QR_CODE
  }

  data object Email : CreateQrType {
    override val categoryType: CategoryType = CategoryType.Personal
    override val titleResId: Int = R.string.email_address
    override val imageResId: Int = R.drawable.ic_email
    override val trackingName: String = "qr_email"
    override val schema: BarcodeSchema = BarcodeSchema.EMAIL
    override val format: BarcodeFormat = BarcodeFormat.FORMAT_QR_CODE
  }

  data object Website : CreateQrType {
    override val categoryType: CategoryType = CategoryType.Personal
    override val titleResId: Int = R.string.website
    override val imageResId: Int = R.drawable.ic_website
    override val trackingName: String = "qr_website"
    override val schema: BarcodeSchema = BarcodeSchema.BOOKMARK_URL
    override val format: BarcodeFormat = BarcodeFormat.FORMAT_QR_CODE
  }
  data object AdsNativeMedium : CreateQrType {
    override val categoryType: CategoryType = CategoryType.Personal
    override val titleResId: Int = R.string.next
    override val imageResId: Int = R.drawable.ic_website
    override val trackingName: String = "AdsNativeMedium"
    override val schema: BarcodeSchema = BarcodeSchema.OTHER
    override val format: BarcodeFormat = BarcodeFormat.FORMAT_QR_CODE
  }

  data object ContactInfo : CreateQrType {
    override val categoryType: CategoryType = CategoryType.Personal
    override val titleResId: Int = R.string.contact_info
    override val imageResId: Int = R.drawable.ic_contact_infor
    override val trackingName: String = "qr_contact_info"
    override val schema: BarcodeSchema = BarcodeSchema.TYPE_CONTACT_INFO
    override val format: BarcodeFormat = BarcodeFormat.FORMAT_QR_CODE
  }

  data object Telephone : CreateQrType {
    override val categoryType: CategoryType = CategoryType.Personal
    override val titleResId: Int = R.string.telephone
    override val imageResId: Int = R.drawable.ic_category_tel
    override val trackingName: String = "qr_telephone"
    override val schema: BarcodeSchema = BarcodeSchema.PHONE
    override val format: BarcodeFormat = BarcodeFormat.FORMAT_QR_CODE
  }

  data object Message : CreateQrType {
    override val categoryType: CategoryType = CategoryType.Personal
    override val titleResId: Int = R.string.message
    override val imageResId: Int = R.drawable.ic_message
    override val trackingName: String = "qr_message"
    override val schema: BarcodeSchema = BarcodeSchema.SMS
    override val format: BarcodeFormat = BarcodeFormat.FORMAT_QR_CODE
  }

  data object WhatsApp : CreateQrType {
    override val categoryType: CategoryType = CategoryType.Social
    override val titleResId: Int = R.string.whatsapp
    override val imageResId: Int = R.drawable.ic_whatsapp
    override val trackingName: String = "qr_whatsapp"
    override val schema: BarcodeSchema = BarcodeSchema.TYPE_WHATSAPP
    override val format: BarcodeFormat = BarcodeFormat.FORMAT_QR_CODE
  }

  data object Facebook : CreateQrType {
    override val categoryType: CategoryType = CategoryType.Social
    override val titleResId: Int = R.string.facebook
    override val imageResId: Int = R.drawable.ic_facebook
    override val trackingName: String = "qr_facebook"
    override val schema: BarcodeSchema = BarcodeSchema.TYPE_FACEBOOK
    override val format: BarcodeFormat = BarcodeFormat.FORMAT_QR_CODE
  }

  data object Twitter : CreateQrType {
    override val categoryType: CategoryType = CategoryType.Social
    override val titleResId: Int = R.string.twitter
    override val imageResId: Int = R.drawable.ic_x
    override val trackingName: String = "qr_twitter"
    override val schema: BarcodeSchema = BarcodeSchema.TYPE_TWITTER
    override val format: BarcodeFormat = BarcodeFormat.FORMAT_QR_CODE
  }

  data object Instagram : CreateQrType {
    override val categoryType: CategoryType = CategoryType.Social
    override val titleResId: Int = R.string.instagram
    override val imageResId: Int = R.drawable.ic_instagram
    override val trackingName: String = "qr_instagram"
    override val schema: BarcodeSchema = BarcodeSchema.TYPE_INSTAGRAM
    override val format: BarcodeFormat = BarcodeFormat.FORMAT_QR_CODE
  }

  data object YouTube : CreateQrType {
    override val categoryType: CategoryType = CategoryType.Social
    override val titleResId: Int = R.string.youtube
    override val imageResId: Int = R.drawable.ic_youtube
    override val trackingName: String = "qr_youtube"
    override val schema: BarcodeSchema = BarcodeSchema.TYPE_YOUTUBE
    override val format: BarcodeFormat = BarcodeFormat.FORMAT_QR_CODE
  }

  data object TikTok : CreateQrType {
    override val categoryType: CategoryType = CategoryType.Social
    override val titleResId: Int = R.string.tiktok
    override val imageResId: Int = R.drawable.ic_tiktok
    override val trackingName: String = "qr_tiktok"
    override val schema: BarcodeSchema = BarcodeSchema.TYPE_TIKTOK
    override val format: BarcodeFormat = BarcodeFormat.FORMAT_QR_CODE
  }

  data object Telegram : CreateQrType {
    override val categoryType: CategoryType = CategoryType.Social
    override val titleResId: Int = R.string.telegram
    override val imageResId: Int = R.drawable.ic_telegram
    override val trackingName: String = "qr_telegram"
    override val schema: BarcodeSchema = BarcodeSchema.TYPE_TELEGRAM
    override val format: BarcodeFormat = BarcodeFormat.FORMAT_QR_CODE
  }

  data object Spotify : CreateQrType {
    override val categoryType: CategoryType = CategoryType.Social
    override val titleResId: Int = R.string.spotify
    override val imageResId: Int = R.drawable.ic_spotify
    override val trackingName: String = "qr_spotify"
    override val schema: BarcodeSchema = BarcodeSchema.TYPE_SPOTIFY
    override val format: BarcodeFormat = BarcodeFormat.FORMAT_QR_CODE
  }

  data object Wifi : CreateQrType {
    override val categoryType: CategoryType = CategoryType.Utility
    override val titleResId: Int = R.string.wifi
    override val imageResId: Int = R.drawable.ic_wifi
    override val trackingName: String = "qr_wifi"
    override val schema: BarcodeSchema = BarcodeSchema.WIFI
    override val format: BarcodeFormat = BarcodeFormat.FORMAT_QR_CODE
  }

  data object Text : CreateQrType {
    override val categoryType: CategoryType = CategoryType.Utility
    override val titleResId: Int = R.string.text
    override val imageResId: Int = R.drawable.ic_text
    override val trackingName: String = "qr_text"
    override val schema: BarcodeSchema = BarcodeSchema.OTHER
    override val format: BarcodeFormat = BarcodeFormat.FORMAT_QR_CODE
  }

  data object Calendar : CreateQrType {
    override val categoryType: CategoryType = CategoryType.Utility
    override val titleResId: Int = R.string.calendar
    override val imageResId: Int = R.drawable.ic_calendar
    override val trackingName: String = "qr_calendar"
    override val schema: BarcodeSchema = BarcodeSchema.TYPE_CALENDAR_EVENT
    override val format: BarcodeFormat = BarcodeFormat.FORMAT_QR_CODE
  }
}

fun CreateQrType.toResult(barCodeModel: BaseBarcodeModel): BarCodeResult = BarCodeResult(
  text = (barCodeModel as? Schema)?.toBarcodeText().orEmpty(),
  formattedText = (barCodeModel as? Schema)?.toFormattedText().orEmpty(),
  format = this.format,
  typeSchema = this.schema,
  date = System.currentTimeMillis(),
  isGenerated = true,
)

@Stable
val screenTypes: PersistentList<CreateQrType> by lazy {
  persistentListOf(
    CreateQrType.MyBusinessCard,
    CreateQrType.Email,
    CreateQrType.Website,
    CreateQrType.AdsNativeMedium,
    CreateQrType.ContactInfo,
    CreateQrType.Telephone,
    CreateQrType.Message,
    CreateQrType.WhatsApp,
    CreateQrType.Facebook,
    CreateQrType.Twitter,
    CreateQrType.Instagram,
    CreateQrType.TikTok,
    CreateQrType.Telegram,
    CreateQrType.YouTube,
    CreateQrType.Spotify,
    CreateQrType.Wifi,
    CreateQrType.Text,
    CreateQrType.Calendar,
  )
}

@Stable
fun getQrType(stringIdRes: Int): CreateQrType = screenTypes.first { it.titleResId == stringIdRes }

@Stable
fun getItemsByCategory(categoryType: CategoryType): PersistentList<CreateQrType> =
  screenTypes.filter { it.categoryType == categoryType && it.titleResId != R.string.next }.toPersistentList()
