package com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema.profile
import androidx.compose.runtime.Immutable
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarcodeSchema
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BaseBarcodeModel
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.Schema
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.extension.removePrefixIgnoreCase
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.extension.startsWithIgnoreCase

@Immutable
data class TelegramProfile(val username: String) :
  BaseBarcodeModel(null),
  Schema {

  companion object {
    private const val PREFIX = "https://t.me/"
    private const val TITLE = "Telegram: "

    fun parse(text: String): TelegramProfile? {
      if (!text.startsWithIgnoreCase(PREFIX)) {
        return null
      }

      val username = text.removePrefixIgnoreCase(PREFIX)
      return TelegramProfile(username)
    }
  }

  override val schema: BarcodeSchema
    get() = BarcodeSchema.TYPE_TELEGRAM

  override fun toFormattedText(): String = "Telegram"

  override fun toBarcodeText(): String = PREFIX + username
}
