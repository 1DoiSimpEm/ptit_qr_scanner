package ptit.vietpq.qr_scanner.designsystem.internal

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import timber.log.Timber

abstract class ThemeColors {
  abstract val background: Color
  abstract val surface: Color
  abstract val surfaceVariant: Color
  abstract val onSurfacePrimary: Color
  abstract val onSurfaceSecondary: Color
  abstract val onSurfaceTertiary: Color
  abstract val primaryIndicator: Color
  abstract val serviceBackgroundWithGroups: Color
  abstract val switchTrack: Color
  abstract val switchThumb: Color
  val primary: Color = Color(0xFF3BB868)
  val primaryDisable = Color(0xFFC5EED5)
  val primaryDark: Color = Color(0xFFD81F26)

  val button: Color = primary
  val onButton: Color = Color.White

  val divider: Color
    get() = surfaceVariant

  val iconTint: Color
    get() = onSurfaceSecondary

  val error: Color = Color(0xFFB9171E)
  val neutral1: Color = Color(0xFF161616)
  val neutral2: Color = Color(0xFF535252)
  val neutral3: Color = Color(0xFFBABABA)
  val neutral4: Color = Color(0xFFE7E7E7)
  val neutral5: Color = Color(0xFFFFFFFF)
  val neutral6: Color = Color(0xB3161616)
  val backgroundCard: Color = Color(0xFFF8F8F8)
  val backgroundImage: Color = Color(0xFFD9D9D9)
  val accentLightBlue: Color = Color(0xFF7F9CFF)
  val accentIndigo: Color = Color(0xFF5E5CE6)
  val accentPurple: Color = Color(0xFF8C49DE)
  val accentTurquoise: Color = Color(0xFF2FCFBC)
  val accentGreen: Color = Color(0xFF03BF38)
  val backgroundGreen: Color = Color(0xFFEFFCF4)
  val accentRed: Color = Color(0xFFFF4A4A)
  val accentOrange: Color = Color(0xFFFF7A00)
  val accentYellow: Color = Color(0xFFFFBA0A)
  val accentPink: Color = Color(0xFFca49de)
  val accentBrown: Color = Color(0xFFbd8857)
}

@Composable
fun isDarkTheme() = QrCodeTheme.color.background.luminance() < 0.5

val LocalThemeColors = staticCompositionLocalOf<ThemeColors> {
  ThemeColorsLight()
}

val LocalAppTheme = staticCompositionLocalOf { AppTheme.Light }

enum class AppTheme {
  Auto,
  Light,
  Dark,
}

@Composable
fun QRCodeScannerScanBarcodeTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
  val isInDarkTheme = when (LocalAppTheme.current) {
    AppTheme.Auto -> isSystemInDarkTheme()
    AppTheme.Light -> false
    AppTheme.Dark -> true
  }

  val colors: ThemeColors = when (isInDarkTheme) {
    true -> ThemeColorsDark()
    false -> ThemeColorsLight()
  }
  val window = (LocalView.current.context as Activity).window
  Timber.tag("QRCodeScannerScanBarcodeTheme").d("isInDarkTheme: $isInDarkTheme")
  val insetsController = WindowCompat.getInsetsController(window, window.decorView)

  insetsController.apply {
    hide(WindowInsetsCompat.Type.statusBars())
    systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
  }
  val colorScheme: ColorScheme = when (isInDarkTheme) {
    true -> darkColorScheme(
      primary = colors.primary,
      onPrimary = colors.onSurfacePrimary,
      background = colors.background,
      onBackground = colors.onSurfacePrimary,
      surface = colors.surface,
      onSurface = colors.onSurfacePrimary,
      surfaceVariant = colors.surface,
      onSurfaceVariant = colors.onSurfacePrimary,
    )

    else -> lightColorScheme(
      primary = colors.primary,
      onPrimary = colors.onSurfacePrimary,
      background = colors.background,
      onBackground = colors.onSurfacePrimary,
      surface = colors.surface,
      onSurface = colors.onSurfacePrimary,
      surfaceVariant = colors.surface,
      onSurfaceVariant = colors.onSurfacePrimary,
    )
  }

//  val view = LocalView.current
//  if (!view.isInEditMode) {
//    SideEffect {
//      window.statusBarColor = Color(0xFF161616).toArgb()
//      window.navigationBarColor = colorScheme.background.toArgb()
//      WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
//    }
//  }
  CompositionLocalProvider(
    LocalThemeColors provides colors,
  ) {
    MaterialTheme(
      colorScheme = colorScheme,
      content = content,
    )
  }
}
