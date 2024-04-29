package com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema

import android.net.MailTo
import android.net.Uri
import androidx.compose.runtime.Immutable
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarcodeSchema
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BaseBarcodeModel
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.Schema
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.extension.removePrefixIgnoreCase
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.extension.startsWithIgnoreCase

@Immutable
data class EmailContent(val address: String, val body: String, val subject: String) :
  BaseBarcodeModel(null),
  Schema {

  companion object {
    private const val MATMSG_SCHEMA_PREFIX = "MATMSG:"
    private const val MATMSG_EMAIL_PREFIX = "TO:"
    private const val MATMSG_SUBJECT_PREFIX = "SUB:"
    private const val MATMSG_BODY_PREFIX = "BODY:"
    private const val MATMSG_SEPARATOR = ";"

    private const val MAILTO_SCHEMA_PREFIX = "mailto:"

    fun parse(text: String): EmailContent? = when {
      text.startsWithIgnoreCase(MATMSG_SCHEMA_PREFIX) -> parseAsMatmsg(text)
      text.startsWithIgnoreCase(MAILTO_SCHEMA_PREFIX) -> parseAsMailTo(text)
      else -> null
    }

    private fun parseAsMatmsg(text: String): EmailContent {
      var email: String? = null
      var subject: String? = null
      var body: String? = null

      text.removePrefixIgnoreCase(MATMSG_SCHEMA_PREFIX).split(MATMSG_SEPARATOR).forEach { part ->
        if (part.startsWithIgnoreCase(MATMSG_EMAIL_PREFIX)) {
          email = part.removePrefixIgnoreCase(MATMSG_EMAIL_PREFIX)
          return@forEach
        }

        if (part.startsWithIgnoreCase(MATMSG_SUBJECT_PREFIX)) {
          subject = part.removePrefixIgnoreCase(MATMSG_SUBJECT_PREFIX)
          return@forEach
        }

        if (part.startsWithIgnoreCase(MATMSG_BODY_PREFIX)) {
          body = part.removePrefixIgnoreCase(MATMSG_BODY_PREFIX)
          return@forEach
        }
      }

      return EmailContent(
        address = email.orEmpty(),
        body = subject.orEmpty(),
        subject = body.orEmpty(),
      )
    }

    private fun parseAsMailTo(text: String): EmailContent? = try {
      val mailto = MailTo.parse(text)
      EmailContent(
        address = mailto.to.orEmpty(),
        body = mailto.subject.orEmpty(),
        subject = mailto.body.orEmpty(),
      )
    } catch (ex: Exception) {
      null
    }
  }

  override val schema: BarcodeSchema
    get() = BarcodeSchema.EMAIL

  override fun toFormattedText(): String = "Email"

  override fun toBarcodeText(): String = "mailto:$address?subject=${Uri.encode(subject)}&body=${Uri.encode(body)}"
}
