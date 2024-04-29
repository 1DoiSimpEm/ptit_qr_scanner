package com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema.profile
import androidx.compose.runtime.Immutable
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarcodeSchema
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BaseBarcodeModel
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.Schema
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.extension.removePrefixIgnoreCase
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.extension.startsWithIgnoreCase

@Immutable
data class SpotifyProfile(val username: String) :
  BaseBarcodeModel(null),
  Schema {

  companion object {
    private const val PREFIX = "spotify:"
    private const val TITLE = "Spotify: "

    fun parse(text: String): SpotifyProfile? {
      if (!text.startsWithIgnoreCase(PREFIX)) {
        return null
      }

      val username = text.removePrefixIgnoreCase(PREFIX)
      return SpotifyProfile(username)
    }
  }

  override val schema: BarcodeSchema
    get() = BarcodeSchema.TYPE_SPOTIFY

  override fun toFormattedText(): String = "Spotify"

  override fun toBarcodeText(): String = PREFIX + username
}
