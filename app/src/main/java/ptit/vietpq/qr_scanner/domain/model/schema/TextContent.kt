package com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema

import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarcodeSchema
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BaseBarcodeModel
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.Schema

class TextContent(val content: String) :
  BaseBarcodeModel(null),
  Schema {

  companion object {
    fun parse(text: String): TextContent = TextContent(text)
  }

  override val schema: BarcodeSchema
    get() = BarcodeSchema.OTHER

  override fun toFormattedText(): String = "Text"

  override fun toBarcodeText(): String = content
}
