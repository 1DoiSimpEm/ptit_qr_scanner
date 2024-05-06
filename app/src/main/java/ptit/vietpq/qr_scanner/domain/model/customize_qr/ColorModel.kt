package ptit.vietpq.qr_scanner.domain.model.customize_qr

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import javax.annotation.concurrent.Immutable
import kotlinx.collections.immutable.persistentListOf
import ptit.vietpq.qr_scanner.R

@Immutable
@Stable
data class ColorModel(val color: Color? = null, val drawableResId: Int? = null) {
  companion object {
    val default = ColorModel(drawableResId = R.drawable.ic_none)
    fun getColorList() = persistentListOf(
      ColorModel(drawableResId = R.drawable.ic_none),
      ColorModel(drawableResId = R.drawable.ic_color_wheel),
      ColorModel(color = Color(0xFFE91E63)),
      ColorModel(color = Color(0xFF9C27B0)),
      ColorModel(color = Color(0xFFF29A39)),
      ColorModel(color = Color(0xFFF8CC47)),
      ColorModel(color = Color(0xFF2A7DF5)),
      ColorModel(color = Color(0xFFA35BD8)),
      ColorModel(color = Color(0xFFC4C4C4)),
    )
  }
}
