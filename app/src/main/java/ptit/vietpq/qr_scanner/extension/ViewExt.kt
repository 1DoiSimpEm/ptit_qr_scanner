package ptit.vietpq.qr_scanner.extension

import android.view.View

inline fun View.visible() {
  if (visibility != View.VISIBLE) visibility = View.VISIBLE
}

inline fun View.gone() {
  if (visibility != View.GONE) visibility = View.GONE
}

inline fun View.invisible() {
  if (visibility != View.INVISIBLE) visibility = View.INVISIBLE
}

inline fun View.visibleIf(condition: Boolean, gone: Boolean = true) = if (condition) {
  visible()
} else {
  if (gone) gone() else invisible()
}
