package com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
@JsonClass(generateAdapter = true)
open class BaseBarcodeModel(
  @Json(name = "rawValue")
  val rawValue: String?,
) : Parcelable {

  override fun toString(): String = "Barcode Data \n" +
    "Value : $rawValue"
}
