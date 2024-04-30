package com.qrcode.qrscanner.barcode.barcodescan.qrreader.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.R
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.designsystem.QrCodeTheme
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.designsystem.locale.QrLocale

@Composable
fun AdsLoadingDialog(isDisplayed: Boolean, modifier: Modifier = Modifier) {
  val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_loading_ads))
  if (isDisplayed) {
    Dialog(onDismissRequest = { /* Dialog is not dismissible */ }) {
      Box(
        modifier = modifier
          .height(112.dp)
          .width(136.dp)
          .background(color = Color.White, shape = RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center,
      ) {
        Column(
          modifier = Modifier.fillMaxWidth(),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally,
        ) {
          LottieAnimation(
            modifier = Modifier.size(40.dp),
            composition = composition,
            iterations = LottieConstants.IterateForever,
          )
          Text(
            text = QrLocale.strings.adsLoading,
            textAlign = TextAlign.Center,
            style = QrCodeTheme.typo.body2,
            modifier = Modifier.padding(top = 20.dp),
          ) // Loading text
        }
      }
    }
  }
}

@Preview
@Composable
private fun LoadingDialogPreview() {
  AdsLoadingDialog(isDisplayed = true)
}
