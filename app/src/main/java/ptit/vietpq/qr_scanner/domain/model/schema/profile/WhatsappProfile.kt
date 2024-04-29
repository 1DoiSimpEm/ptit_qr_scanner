package com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema.profile

import androidx.compose.runtime.Immutable
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarcodeSchema
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BaseBarcodeModel
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.Schema
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.extension.removePrefixIgnoreCase
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.extension.startsWithIgnoreCase

@Immutable
data class WhatsappProfile(val phoneNumber: String) :
  BaseBarcodeModel(null),
  Schema {

  companion object {
    private const val PREFIX = "whatsapp://"
    private const val TITLE = "Whatsapp: "
    private const val SEPARATOR = "/"

    fun parse(text: String): WhatsappProfile? {
      if (!text.startsWithIgnoreCase(PREFIX)) {
        return null
      }

      val phoneNumber = text.removePrefixIgnoreCase(PREFIX).split(SEPARATOR).first()
      return WhatsappProfile(phoneNumber)
    }
  }

  override val schema: BarcodeSchema
    get() = BarcodeSchema.TYPE_WHATSAPP

  override fun toFormattedText(): String = "Whatsapp"

  override fun toBarcodeText(): String = PREFIX + phoneNumber
}
