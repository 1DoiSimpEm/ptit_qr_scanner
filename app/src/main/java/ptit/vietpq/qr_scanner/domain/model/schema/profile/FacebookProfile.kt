package com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema.profile

import androidx.compose.runtime.Immutable
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarcodeSchema
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BaseBarcodeModel
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.Schema
import ptit.vietpq.qr_scanner.extension.startsWithAnyIgnoreCase

@Immutable
data class FacebookProfile(val profileId: String) :
  BaseBarcodeModel(null),
  Schema {

  companion object {
    private const val PREFIX1 = "https://www.facebook.com/"
    private const val PREFIX2 = "facebook.com/"
    private const val PREFIX3 = "https://facebook.com/"
    private val PREFIXES = listOf(PREFIX1, PREFIX2, PREFIX3)
    private const val TITLE = "Facebook: "
    private const val SEPARATOR = "/"

    fun parse(text: String): FacebookProfile? {
      if (!text.startsWithAnyIgnoreCase(PREFIXES)) {
        return null
      }

      return FacebookProfile(text)
    }
  }

  override val schema: BarcodeSchema
    get() = BarcodeSchema.TYPE_FACEBOOK

  override fun toFormattedText(): String = "Facebook"

  override fun toBarcodeText(): String = PREFIX1 + profileId
}
