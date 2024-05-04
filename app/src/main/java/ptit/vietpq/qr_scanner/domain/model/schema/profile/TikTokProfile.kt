package com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema.profile
import androidx.compose.runtime.Immutable
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarcodeSchema
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BaseBarcodeModel
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.Schema
import ptit.vietpq.qr_scanner.extension.removePrefixIgnoreCase
import ptit.vietpq.qr_scanner.extension.startsWithIgnoreCase

@Immutable
data class TikTokProfile(val username: String) :
  BaseBarcodeModel(null),
  Schema {

  companion object {
    private const val PREFIX = "https://www.tiktok.com/@"
    private const val TITLE = "TikTok: "

    fun parse(text: String): TikTokProfile? {
      if (!text.startsWithIgnoreCase(PREFIX)) {
        return null
      }

      val username = text.removePrefixIgnoreCase(PREFIX)
      return TikTokProfile(username)
    }
  }

  override val schema: BarcodeSchema
    get() = BarcodeSchema.TYPE_TIKTOK

  override fun toFormattedText(): String = "Tiktok"

  override fun toBarcodeText(): String = PREFIX + username
}
