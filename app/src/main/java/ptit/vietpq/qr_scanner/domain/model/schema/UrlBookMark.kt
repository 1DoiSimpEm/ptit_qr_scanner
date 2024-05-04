package com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema

import androidx.compose.runtime.Immutable
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarcodeSchema
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BaseBarcodeModel
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.Schema
import ptit.vietpq.qr_scanner.extension.startsWithAnyIgnoreCase
import ptit.vietpq.qr_scanner.extension.startsWithIgnoreCase

@Immutable
data class UrlBookmark(val title: String, val url: String) :
  BaseBarcodeModel(null),
  Schema {

  companion object {
    private const val HTTP_PREFIX = "http://"
    private const val HTTPS_PREFIX = "https://"
    private const val WWW_PREFIX = "www."

    private val PREFIXES = listOf(HTTP_PREFIX, HTTPS_PREFIX, WWW_PREFIX)

    fun parse(text: String): UrlBookmark? {
      if (text.startsWithAnyIgnoreCase(PREFIXES).not()) {
        return null
      }

      val url = when {
        text.startsWithIgnoreCase(WWW_PREFIX) -> "$HTTP_PREFIX$text"
        else -> text
      }

      return UrlBookmark(text, url)
    }
  }

  override val schema: BarcodeSchema
    get() = BarcodeSchema.BOOKMARK_URL

  override fun toFormattedText(): String = "Website"

  override fun toBarcodeText(): String = url
}
