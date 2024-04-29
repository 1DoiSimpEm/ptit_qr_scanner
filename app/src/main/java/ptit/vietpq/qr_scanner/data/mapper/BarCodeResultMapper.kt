package ptit.vietpq.qr_scanner.data.mapper

import com.google.mlkit.vision.barcode.common.Barcode
import ptit.vietpq.qr_scanner.data.entity.BarCodeResultEntity
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarCodeResult
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarcodeFormat
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarcodeSchema
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.Schema
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
import ptit.vietpq.qr_scanner.R

fun Barcode.toBarCodeResult(cropMode: Boolean): BarCodeResult {
  val schema = parseModelToSchema(this.rawValue.orEmpty())
  return BarCodeResult(
    text = this.rawValue.orEmpty(),
    formattedText = schema.toFormattedText(),
    format = this.toBarcodeFormat() ?: BarcodeFormat.FORMAT_QR_CODE,
    typeSchema = schema.schema,
    date = System.currentTimeMillis(),
    cropImage = cropMode,
  )
}

fun parseModelToSchema(item: String): Schema = ContactInfoContent.parse(item)
  ?: EmailContent.parse(item)
  ?: GeoPoint.parse(item)
  ?: CalendarEvent.parse(item)
  ?: Phone.parse(item)
  ?: SmsContent.parse(item)
  ?: WifiContent.parse(item)

  ?: FacebookProfile.parse(item)
  ?: InstagramProfile.parse(item)
  ?: SpotifyProfile.parse(item)
  ?: TelegramProfile.parse(item)
  ?: TikTokProfile.parse(item)
  ?: TwitterProfile.parse(item)
  ?: WhatsappProfile.parse(item)
  ?: YoutubeProfile.parse(item)

  ?: UrlBookmark.parse(item)
  ?: TextContent.parse(item)

fun BarCodeResult.imageDrawerByType(): Int = when (this.typeSchema) {
  BarcodeSchema.TYPE_CALENDAR_EVENT -> R.drawable.ic_calendar
  BarcodeSchema.TYPE_CONTACT_INFO -> R.drawable.ic_contact_infor
  BarcodeSchema.EMAIL -> R.drawable.ic_email
  BarcodeSchema.GEO -> R.drawable.ic_category_location
  BarcodeSchema.PHONE -> R.drawable.ic_category_tel
  BarcodeSchema.SMS -> R.drawable.ic_message
  BarcodeSchema.BOOKMARK_URL -> R.drawable.ic_website
  BarcodeSchema.WIFI -> R.drawable.ic_wifi

  BarcodeSchema.TYPE_FACEBOOK -> R.drawable.ic_facebook
  BarcodeSchema.TYPE_INSTAGRAM -> R.drawable.ic_instagram
  BarcodeSchema.TYPE_SPOTIFY -> R.drawable.ic_spotify
  BarcodeSchema.TYPE_TELEGRAM -> R.drawable.ic_telegram
  BarcodeSchema.TYPE_TIKTOK -> R.drawable.ic_tiktok
  BarcodeSchema.TYPE_TWITTER -> R.drawable.ic_x
  BarcodeSchema.TYPE_WHATSAPP -> R.drawable.ic_whatsapp
  BarcodeSchema.TYPE_YOUTUBE -> R.drawable.ic_youtube
  BarcodeSchema.OTHER -> R.drawable.ic_text
}

fun BarCodeResult.toBarCodeResultEntity() = BarCodeResultEntity(
  id = this.id,
  text = this.text,
  formattedText = this.formattedText,
  format = this.format.value,
  typeSchema = this.typeSchema.value,
  date = this.date,
  isCreated = this.isGenerated,
  isFavorite = this.isFavorite,
)

fun BarCodeResultEntity.toBarCodeResult() = BarCodeResult(
  id = this.id,
  text = this.text,
  formattedText = this.formattedText,
  format = BarcodeFormat.entries.firstOrNull { it.value == this.format } ?: BarcodeFormat.FORMAT_QR_CODE,
  typeSchema = BarcodeSchema.entries.firstOrNull { it.value == this.typeSchema } ?: BarcodeSchema.OTHER,
  date = this.date,
  isGenerated = this.isCreated,
  isFavorite = this.isFavorite,
  isSelected = false,
)

fun Barcode.toBarcodeFormat() = BarcodeFormat.entries.firstOrNull { it.value == this.format }

fun Barcode.toTypeSchema() = BarcodeSchema.entries.firstOrNull { it.value == this.valueType }

fun BarcodeFormat.mapMLKitFormatToZxing(): com.google.zxing.BarcodeFormat = when (this) {
  BarcodeFormat.FORMAT_UNKNOWN -> com.google.zxing.BarcodeFormat.QR_CODE
  BarcodeFormat.FORMAT_ALL_FORMATS -> com.google.zxing.BarcodeFormat.QR_CODE
  BarcodeFormat.FORMAT_CODE_128 -> com.google.zxing.BarcodeFormat.CODE_128
  BarcodeFormat.FORMAT_CODE_39 -> com.google.zxing.BarcodeFormat.CODE_39
  BarcodeFormat.FORMAT_CODE_93 -> com.google.zxing.BarcodeFormat.CODE_93
  BarcodeFormat.FORMAT_CODABAR -> com.google.zxing.BarcodeFormat.CODABAR
  BarcodeFormat.FORMAT_DATA_MATRIX -> com.google.zxing.BarcodeFormat.DATA_MATRIX
  BarcodeFormat.FORMAT_EAN_13 -> com.google.zxing.BarcodeFormat.EAN_13
  BarcodeFormat.FORMAT_EAN_8 -> com.google.zxing.BarcodeFormat.EAN_8
  BarcodeFormat.FORMAT_ITF -> com.google.zxing.BarcodeFormat.ITF
  BarcodeFormat.FORMAT_QR_CODE -> com.google.zxing.BarcodeFormat.QR_CODE
  BarcodeFormat.FORMAT_UPC_A -> com.google.zxing.BarcodeFormat.UPC_A
  BarcodeFormat.FORMAT_UPC_E -> com.google.zxing.BarcodeFormat.UPC_E
  BarcodeFormat.FORMAT_PDF417 -> com.google.zxing.BarcodeFormat.PDF_417
  BarcodeFormat.FORMAT_AZTEC -> com.google.zxing.BarcodeFormat.AZTEC
}
