package ptit.vietpq.qr_scanner.data.mapper

import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarCodeResult
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

fun BarCodeResult.toBarCodeModel(): BaseBarcodeModel? = when (this.typeSchema) {
  BarcodeSchema.TYPE_CONTACT_INFO -> {
    ContactInfoContent.parse(this.text)
  }

  BarcodeSchema.EMAIL -> {
    EmailContent.parse(this.text)
  }

  BarcodeSchema.PHONE -> {
    Phone.parse(this.text)
  }

  BarcodeSchema.SMS -> {
    SmsContent.parse(this.text)
  }

  BarcodeSchema.BOOKMARK_URL -> {
    UrlBookmark.parse(this.text)
  }

  BarcodeSchema.WIFI -> {
    WifiContent.parse(this.text)
  }

  BarcodeSchema.GEO -> {
    GeoPoint.parse(this.text)
  }

  BarcodeSchema.TYPE_CALENDAR_EVENT -> {
    CalendarEvent.parse(this.text)
  }

  BarcodeSchema.TYPE_FACEBOOK -> {
    FacebookProfile.parse(this.text)
  }

  BarcodeSchema.TYPE_INSTAGRAM -> {
    InstagramProfile.parse(this.text)
  }

  BarcodeSchema.TYPE_SPOTIFY -> {
    SpotifyProfile.parse(this.text)
  }

  BarcodeSchema.TYPE_TELEGRAM -> {
    TelegramProfile.parse(this.text)
  }

  BarcodeSchema.TYPE_TIKTOK -> {
    TikTokProfile.parse(this.text)
  }

  BarcodeSchema.TYPE_TWITTER -> {
    TwitterProfile.parse(this.text)
  }

  BarcodeSchema.TYPE_WHATSAPP -> {
    WhatsappProfile.parse(this.text)
  }

  BarcodeSchema.TYPE_YOUTUBE -> {
    YoutubeProfile.parse(this.text)
  }

  BarcodeSchema.OTHER -> {
    TextContent.parse(this.text)
  }
}
