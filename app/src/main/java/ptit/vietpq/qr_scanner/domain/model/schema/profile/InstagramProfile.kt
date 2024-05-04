package com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema.profile
import androidx.compose.runtime.Immutable
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarcodeSchema
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BaseBarcodeModel
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.Schema
import ptit.vietpq.qr_scanner.extension.removePrefixIgnoreCase
import ptit.vietpq.qr_scanner.extension.startsWithIgnoreCase

@Immutable
data class InstagramProfile(val username: String) :
  BaseBarcodeModel(null),
  Schema {

  companion object {
    private const val PREFIX = "https://www.instagram.com/"
    private const val TITLE = "Instagram: "
    private const val SEPARATOR = "/"

    fun parse(text: String): InstagramProfile? {
      if (!text.startsWithIgnoreCase(PREFIX)) {
        return null
      }

      val username = text.removePrefixIgnoreCase(PREFIX).split(SEPARATOR).first()
      return InstagramProfile(username)
    }
  }

  override val schema: BarcodeSchema
    get() = BarcodeSchema.TYPE_INSTAGRAM

  override fun toFormattedText(): String = "Instagram"

  override fun toBarcodeText(): String = PREFIX + username
}
