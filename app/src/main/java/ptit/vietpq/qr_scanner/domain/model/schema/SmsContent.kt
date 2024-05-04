package com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema

import androidx.compose.runtime.Immutable
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarcodeSchema
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BaseBarcodeModel
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.Schema
import ptit.vietpq.qr_scanner.extension.removePrefixIgnoreCase
import ptit.vietpq.qr_scanner.extension.startsWithIgnoreCase

@Immutable
data class SmsContent(val message: String, val phoneNumber: String) :
  BaseBarcodeModel(null),
  Schema {

  companion object {
    private const val PREFIX = "smsto:"
    private const val SEPARATOR = ":"

    fun parse(text: String): SmsContent? {
      if (!text.startsWithIgnoreCase(PREFIX)) {
        return null
      }

      val parts = text.removePrefixIgnoreCase(PREFIX).split(SEPARATOR)
      return SmsContent(
        phoneNumber = parts.getOrElse(0) { "" },
        message = parts.getOrElse(1) { "" },
      )
    }
  }

  override val schema: BarcodeSchema
    get() = BarcodeSchema.SMS

  override fun toFormattedText(): String = "SMS"

  override fun toBarcodeText(): String = PREFIX +
    phoneNumber +
    "$SEPARATOR$message"
}
