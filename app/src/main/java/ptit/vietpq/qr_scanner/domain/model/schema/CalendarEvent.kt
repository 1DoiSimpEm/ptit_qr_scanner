package com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema

import android.annotation.SuppressLint
import androidx.compose.runtime.Immutable
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarcodeSchema
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BaseBarcodeModel
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.Schema
import ptit.vietpq.qr_scanner.extension.parseOrNull
import ptit.vietpq.qr_scanner.extension.removePrefixIgnoreCase
import ptit.vietpq.qr_scanner.extension.startsWithIgnoreCase
import java.text.SimpleDateFormat
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class CalendarEvent(
  val description: String? = null,
  val location: String? = null,
  val organizer: String? = null,
  val start: Long? = null,
  val end: Long? = null,
  val stamp: String? = null,
  val summary: String? = null,
) : BaseBarcodeModel(null),
  Schema {

  @SuppressLint("SimpleDateFormat")
  companion object {
    private const val SCHEMA_PREFIX = "BEGIN:VEVENT"
    private const val SCHEMA_SUFFIX = "END:VEVENT"
    private const val PARAMETERS_SEPARATOR_1 = "\n"
    private const val PARAMETERS_SEPARATOR_2 = "\r"
    private const val UID_PREFIX = "UID:"
    private const val STAMP_PREFIX = "DTSTAMP:"
    private const val ORGANIZER_PREFIX = "ORGANIZER:"
    private const val DESCRIPTION_PREFIX = "DESCRIPTION:"
    private const val LOCATION_PREFIX = "LOCATION:"
    private const val START_PREFIX = "DTSTART:"
    private const val END_PREFIX = "DTEND:"
    private const val SUMMARY_PREFIX = "SUMMARY:"

    private val DATE_PARSERS by lazy {
      persistentListOf(
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"),
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"),
        SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'"),
        SimpleDateFormat("yyyyMMdd'T'HHmmss"),
        SimpleDateFormat("yyyy-MM-dd"),
        SimpleDateFormat("yyyyMMdd"),
      )
    }

    fun parse(text: String): CalendarEvent? {
      if (text.startsWithIgnoreCase(SCHEMA_PREFIX).not()) {
        return null
      }
      var uid: String? = null
      var stamp: String? = null
      var organizer: String? = null
      var description: String? = null
      var location: String? = null
      var startDate: Long? = null
      var endDate: Long? = null
      var summary: String? = null

      text.removePrefixIgnoreCase(SCHEMA_PREFIX).split(PARAMETERS_SEPARATOR_1, PARAMETERS_SEPARATOR_2).forEach { part ->
        if (part.startsWithIgnoreCase(UID_PREFIX)) {
          uid = part.removePrefixIgnoreCase(UID_PREFIX)
          return@forEach
        }

        if (part.startsWithIgnoreCase(STAMP_PREFIX)) {
          stamp = part.removePrefixIgnoreCase(STAMP_PREFIX)
          return@forEach
        }

        if (part.startsWithIgnoreCase(ORGANIZER_PREFIX)) {
          organizer = part.removePrefixIgnoreCase(ORGANIZER_PREFIX)
          return@forEach
        }

        if (part.startsWithIgnoreCase(DESCRIPTION_PREFIX)) {
          description = part.removePrefixIgnoreCase(DESCRIPTION_PREFIX)
          return@forEach
        }

        if (part.startsWithIgnoreCase(LOCATION_PREFIX)) {
          location = part.removePrefixIgnoreCase(LOCATION_PREFIX)
          return@forEach
        }

        if (part.startsWithIgnoreCase(START_PREFIX)) {
          val startDateOriginal = part.removePrefix(START_PREFIX)
          startDate = DATE_PARSERS.parseOrNull(startDateOriginal)?.time
          return@forEach
        }

        if (part.startsWithIgnoreCase(END_PREFIX)) {
          val endDateOriginal = part.removePrefix(END_PREFIX)
          endDate = DATE_PARSERS.parseOrNull(endDateOriginal)?.time
          return@forEach
        }

        if (part.startsWithIgnoreCase(SUMMARY_PREFIX)) {
          summary = part.removePrefixIgnoreCase(SUMMARY_PREFIX)
          return@forEach
        }
      }

      return CalendarEvent(
        description = description.orEmpty(),
        location = location.orEmpty(),
        organizer = organizer.orEmpty(),
        start = startDate ?: 0L,
        end = endDate ?: 0L,
        stamp = stamp.orEmpty(),
        summary = summary.orEmpty(),
      )
    }
  }

  override val schema: BarcodeSchema
    get() = BarcodeSchema.TYPE_CALENDAR_EVENT

  override fun toFormattedText(): String = "Calendar Event"

  override fun toBarcodeText(): String = buildString {
    append("BEGIN:VEVENT\n")
    append(summary?.let { "SUMMARY:$it\n" } ?: "")
    append(description?.let { "DESCRIPTION:$it\n" } ?: "")
    append(location?.let { "LOCATION:$it\n" } ?: "")
    append(organizer?.let { "ORGANIZER:$it\n" } ?: "")
    append(start?.let { "DTSTART:$it\n" } ?: "")
    append(end?.let { "DTEND:$it\n" } ?: "")
    append(stamp?.let { "Stamp:$it\n" } ?: "")
    append("END:VEVENT")
  }
}
