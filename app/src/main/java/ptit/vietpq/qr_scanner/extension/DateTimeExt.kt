package ptit.vietpq.qr_scanner.extension

import java.text.DateFormat
import java.util.Date

fun DateFormat.parseOrNull(date: String?): Date? = try {
  parse(date.orEmpty())
} catch (ex: Exception) {
  null
}

fun List<DateFormat>.parseOrNull(date: String?): Date? {
  forEach { dateParser ->
    val parsedDate = dateParser.parseOrNull(date)
    if (parsedDate != null) {
      return parsedDate
    }
  }
  return null
}

fun DateFormat.formatOrNull(time: Long?): String? = try {
  format(Date(time!!))
} catch (ex: Exception) {
  null
}
