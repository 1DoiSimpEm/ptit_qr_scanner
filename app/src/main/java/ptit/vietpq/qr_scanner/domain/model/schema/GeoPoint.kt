package com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema

import androidx.compose.runtime.Immutable
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarcodeSchema
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BaseBarcodeModel
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.Schema

@Immutable
data class GeoPoint(val lat: Double, val lng: Double) :
  BaseBarcodeModel(null),
  Schema {

  companion object {
    private val GEO_REGEX: Regex = """^geo:(-?\d+(?:\.\d+)?),(-?\d+(?:\.\d+)?)$""".toRegex()

    fun parse(text: String): GeoPoint? {
      val matchResult = GEO_REGEX.matchEntire(text) ?: return null
      val (lat, lng) = matchResult.destructured
      return GeoPoint(lat.toDouble(), lng.toDouble())
    }
  }

  override val schema: BarcodeSchema
    get() = BarcodeSchema.GEO

  override fun toFormattedText(): String = "Location"

  override fun toBarcodeText(): String = "geo:$lat,$lng"
}
