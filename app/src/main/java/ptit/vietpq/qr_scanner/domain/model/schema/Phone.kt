package com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema

import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarcodeSchema
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BaseBarcodeModel
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.Schema

data class Phone(val phone: String) :
  BaseBarcodeModel(null),
  Schema {
  companion object {
    private const val PREFIX = "tel:"

    fun parse(text: String): Phone? {
      if (text.startsWith(PREFIX).not()) {
        return null
      }

      val phone = text.removePrefix(PREFIX)
      return Phone(phone)
    }
  }

  override val schema = BarcodeSchema.PHONE
  override fun toFormattedText(): String = "Phone"
  override fun toBarcodeText(): String = "$PREFIX$phone"
}
