package com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema.profile
import androidx.compose.runtime.Immutable
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarcodeSchema
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BaseBarcodeModel
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.Schema
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.extension.removePrefixIgnoreCase
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.extension.startsWithIgnoreCase

@Immutable
data class YoutubeProfile(val username: String) :
  BaseBarcodeModel(null),
  Schema {

  companion object {
    private const val PREFIX = "https://www.youtube.com/channel/"
    private const val TITLE = "Youtube: "

    fun parse(text: String): YoutubeProfile? {
      if (!text.startsWithIgnoreCase(PREFIX)) {
        return null
      }

      val username = text.removePrefixIgnoreCase(PREFIX)
      return YoutubeProfile(username)
    }
  }

  override val schema: BarcodeSchema
    get() = BarcodeSchema.TYPE_YOUTUBE

  override fun toFormattedText(): String = "Youtube"

  override fun toBarcodeText(): String = PREFIX + username
}
