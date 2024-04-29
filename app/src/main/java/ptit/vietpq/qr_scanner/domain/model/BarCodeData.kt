package com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model

import com.google.mlkit.vision.barcode.common.Barcode

data class BarCodeResult(
  val id: Int = 0,
  val text: String,
  val formattedText: String,
  val format: BarcodeFormat,
  val typeSchema: BarcodeSchema,
  val date: Long = System.currentTimeMillis(),
  val isGenerated: Boolean = false,
  val cropImage: Boolean = false,
  val isFavorite: Boolean = false,
  val isSelected: Boolean = false,
) {
  companion object {
    val INITIAL = BarCodeResult(
      text = "TITLE",
      formattedText = "FORMATED TEXT",
      format = BarcodeFormat.FORMAT_ALL_FORMATS,
      typeSchema = BarcodeSchema.TYPE_CONTACT_INFO,
    )
  }

  val identifyBarCodeResult get() = text + formattedText + format + typeSchema.value
}

object BarcodeTypeExt {
  const val TYPE_FACEBOOK = 13
  const val TYPE_INSTAGRAM = 14
  const val TYPE_SPOTIFY = 15
  const val TYPE_TELEGRAM = 16
  const val TYPE_TIKTOK = 17
  const val TYPE_TWITTER = 18
  const val TYPE_WHATSAPP = 19
  const val TYPE_YOUTUBE = 20
}

/**
 * Wrapper class to access the ML Kit BarcodeFormat constants.
 */
enum class BarcodeFormat(val value: Int) {
  FORMAT_UNKNOWN(Barcode.FORMAT_UNKNOWN),
  FORMAT_ALL_FORMATS(Barcode.FORMAT_ALL_FORMATS),
  FORMAT_CODE_128(Barcode.FORMAT_CODE_128),
  FORMAT_CODE_39(Barcode.FORMAT_CODE_39),
  FORMAT_CODE_93(Barcode.FORMAT_CODE_93),
  FORMAT_CODABAR(Barcode.FORMAT_CODABAR),
  FORMAT_DATA_MATRIX(Barcode.FORMAT_DATA_MATRIX),
  FORMAT_EAN_13(Barcode.FORMAT_EAN_13),
  FORMAT_EAN_8(Barcode.FORMAT_EAN_8),
  FORMAT_ITF(Barcode.FORMAT_ITF),
  FORMAT_QR_CODE(Barcode.FORMAT_QR_CODE),
  FORMAT_UPC_A(Barcode.FORMAT_UPC_A),
  FORMAT_UPC_E(Barcode.FORMAT_UPC_E),
  FORMAT_PDF417(Barcode.FORMAT_PDF417),
  FORMAT_AZTEC(Barcode.FORMAT_AZTEC),
}

fun BarcodeSchema.toTrackingName(): String = when (this) {
  // this format "qr_" + trackingName
  BarcodeSchema.TYPE_FACEBOOK -> "qr_facebook"
  BarcodeSchema.TYPE_INSTAGRAM -> "qr_instagram"
  BarcodeSchema.TYPE_SPOTIFY -> "qr_spotify"
  BarcodeSchema.TYPE_TELEGRAM -> "qr_telegram"
  BarcodeSchema.TYPE_TIKTOK -> "qr_tiktok"
  BarcodeSchema.TYPE_TWITTER -> "qr_twitter"
  BarcodeSchema.TYPE_WHATSAPP -> "qr_whatsapp"
  BarcodeSchema.TYPE_YOUTUBE -> "qr_youtube"
  BarcodeSchema.TYPE_CONTACT_INFO -> "qr_contact_info"
  BarcodeSchema.EMAIL -> "qr_email"
  BarcodeSchema.PHONE -> "qr_phone"
  BarcodeSchema.SMS -> "qr_sms"
  BarcodeSchema.BOOKMARK_URL -> "qr_bookmark_url"
  BarcodeSchema.WIFI -> "qr_wifi"
  BarcodeSchema.GEO -> "qr_geo"
  BarcodeSchema.TYPE_CALENDAR_EVENT -> "qr_calendar"
  BarcodeSchema.OTHER -> "qr_other"
}

/**
 * Wrapper class to access the ML Kit BarcodeSchema constants.
 */
enum class BarcodeSchema(val value: Int) {
  TYPE_CONTACT_INFO(Barcode.TYPE_CONTACT_INFO),
  EMAIL(Barcode.TYPE_EMAIL),
  PHONE(Barcode.TYPE_PHONE),
  SMS(Barcode.TYPE_SMS),
  BOOKMARK_URL(Barcode.TYPE_URL),
  WIFI(Barcode.TYPE_WIFI),
  GEO(Barcode.TYPE_GEO),
  TYPE_CALENDAR_EVENT(Barcode.TYPE_CALENDAR_EVENT),
  TYPE_FACEBOOK(BarcodeTypeExt.TYPE_FACEBOOK),
  TYPE_INSTAGRAM(BarcodeTypeExt.TYPE_INSTAGRAM),
  TYPE_SPOTIFY(BarcodeTypeExt.TYPE_SPOTIFY),
  TYPE_TELEGRAM(BarcodeTypeExt.TYPE_TELEGRAM),
  TYPE_TIKTOK(BarcodeTypeExt.TYPE_TIKTOK),
  TYPE_TWITTER(BarcodeTypeExt.TYPE_TWITTER),
  TYPE_WHATSAPP(BarcodeTypeExt.TYPE_WHATSAPP),
  TYPE_YOUTUBE(BarcodeTypeExt.TYPE_YOUTUBE),
  OTHER(Barcode.TYPE_UNKNOWN),
}

/**
 * Interface to represent the schema of a barcode.
 */
interface Schema {
  val schema: BarcodeSchema
  fun toFormattedText(): String
  fun toBarcodeText(): String
}
