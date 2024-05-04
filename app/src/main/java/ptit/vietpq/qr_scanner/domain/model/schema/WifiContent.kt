package com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema

import androidx.compose.runtime.Immutable
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarcodeSchema
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BaseBarcodeModel
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.Schema
import ptit.vietpq.qr_scanner.extension.startsWithIgnoreCase
import ptit.vietpq.qr_scanner.extension.unescape
import java.util.Locale

@Immutable
data class WifiContent(
  val encryptionType: String,
  val ssid: String,
  val password: String,
  val isHidden: Boolean? = null,
  val anonymousIdentity: String? = null,
  val identity: String? = null,
  val eapMethod: String? = null,
  val phase2Method: String? = null,
) : BaseBarcodeModel(null),
  Schema {

  companion object {
    private val WIFI_REGEX = """^WIFI:((?:.+?:(?:[^\\;]|\\.)*;)+);?$""".toRegex()
    private val PAIR_REGEX = """(.+?):((?:[^\\;]|\\.)*);""".toRegex()
    private const val SCHEMA_PREFIX = "WIFI:"
    private const val ENCRYPTION_PREFIX = "T:"
    private const val NAME_PREFIX = "S:"
    private const val PASSWORD_PREFIX = "P:"
    private const val IS_HIDDEN_PREFIX = "H:"
    private const val ANONYMOUS_IDENTITY_PREFIX = "AI:"
    private const val IDENTITY_PREFIX = "I:"
    private const val EAP_PREFIX = "E:"
    private const val PHASE2_PREFIX = "PH2:"
    private const val SEPARATOR = ";"

    fun parse(text: String): WifiContent? {
      if (text.startsWithIgnoreCase(SCHEMA_PREFIX).not()) {
        return null
      }

      val keysAndValuesSubstring = WIFI_REGEX.matchEntire(text)?.groupValues?.get(1) ?: return null
      val keysAndValues = PAIR_REGEX
        .findAll(keysAndValuesSubstring)
        .map { pair ->
          "${pair.groupValues[1].uppercase(Locale.US)}:" to pair.groupValues[2]
        }
        .toMap()

      return WifiContent(
        encryptionType = keysAndValues[ENCRYPTION_PREFIX].orEmpty(),
        ssid = keysAndValues[NAME_PREFIX]?.unescape().orEmpty(),
        password = keysAndValues[PASSWORD_PREFIX].orEmpty(),
        isHidden = keysAndValues[IS_HIDDEN_PREFIX].toBoolean(),
        anonymousIdentity = keysAndValues[ANONYMOUS_IDENTITY_PREFIX]?.unescape(),
        identity = keysAndValues[IDENTITY_PREFIX]?.unescape(),
        eapMethod = keysAndValues[EAP_PREFIX],
        phase2Method = keysAndValues[PHASE2_PREFIX],
      )
    }
  }

  override val schema: BarcodeSchema
    get() = BarcodeSchema.WIFI

  override fun toFormattedText(): String = "WIFI"

  override fun toBarcodeText(): String = "WIFI:S:$ssid;T:$encryptionType;P:$password;;"
}
