package ptit.vietpq.qr_scanner.extension

private val escapedRegex = """\\([\\;,":])""".toRegex()

val String.Companion.EMPTY
  get() = ""

fun String.unescape(): String = replace(escapedRegex) { escaped ->
  escaped.groupValues[1]
}

fun String.removePrefixIgnoreCase(prefix: String): String = substring(prefix.length)

fun String.startsWithIgnoreCase(prefix: String): Boolean = startsWith(prefix, true)

fun String.startsWithAnyIgnoreCase(prefixes: List<String>): Boolean {
  prefixes.forEach { prefix ->
    if (startsWith(prefix, true)) {
      return true
    }
  }
  return false
}

fun List<String?>.joinToStringNotNullOrBlank(separator: String): String = filter {
  it.isNullOrBlank().not()
}.joinToString(separator)
