package ptit.vietpq.qr_scanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import ptit.vietpq.qr_scanner.presentation.main.QrCodeApp
import dagger.hilt.android.AndroidEntryPoint
import ptit.vietpq.qr_scanner.designsystem.internal.QRCodeScannerScanBarcodeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations, and go edge-to-edge
        // This also sets up the initial system bar style based on the platform theme
        enableEdgeToEdge()
        setContent {
            QrCodeApp()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {
    QRCodeScannerScanBarcodeTheme {
        QrCodeApp()
    }
}
