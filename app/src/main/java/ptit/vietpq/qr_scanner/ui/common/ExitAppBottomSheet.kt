package com.qrcode.qrscanner.barcode.barcodescan.qrreader.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.designsystem.QrCodeTheme
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.designsystem.locale.QrLocale
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.ui.ads_view.ShowBannerNativeMediumType2
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.utils.AppConstant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExitAppBottomSheet(
  isBottomSheetVisible: Boolean,
  onExitClicked: () -> Unit,
  onDismissRequest: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val sheetState: SheetState = rememberModalBottomSheetState(
    skipPartiallyExpanded = true,
  )
  LaunchedEffect(
    key1 = isBottomSheetVisible,
    block = {
      if (isBottomSheetVisible) {
        sheetState.show()
      } else {
        sheetState.hide()
      }
    },
  )

  if (isBottomSheetVisible) {
    ModalBottomSheet(
      modifier = modifier,
      onDismissRequest = onDismissRequest,
      sheetState = sheetState,
      containerColor = QrCodeTheme.color.neutral5,
      dragHandle = {},
      shape = RoundedCornerShape(
        topStart = 16.dp,
        topEnd = 16.dp,
      ),
      content = {
        Column(
          modifier = Modifier
            .padding(16.dp),
        ) {
          Text(
            text = QrLocale.strings.areYouSureYouWantToExitApp,
            style = QrCodeTheme.typo.body2,
            color = QrCodeTheme.color.neutral1,
          )
          Spacer(modifier = Modifier.size(16.dp))
          Row(
            modifier = Modifier
              .padding(vertical = 8.dp)
              .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
          ) {
            CancelButton(
              modifier = Modifier.weight(1f),
              onClick = onDismissRequest,
            )
            Spacer(modifier = Modifier.width(16.dp))
            ExitButton(
              modifier = Modifier.weight(1f),
              onClick = onExitClicked,
            )
          }
          Spacer(modifier = Modifier.size(16.dp))
          ShowBannerNativeMediumType2(
            screenName = AppConstant.NativeAds.EXIT,
            screenTracking = AppConstant.NativeAds.EXIT,
            modifier = Modifier
              .fillMaxWidth(),
          )
          Spacer(modifier = Modifier.size(16.dp))
        }
      },
    )
  }
}

@Composable
private fun ExitButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
  Button(
    modifier = modifier.size(QrCodeTheme.dimen.buttonHeight),
    colors = ButtonDefaults.buttonColors(
      containerColor = QrCodeTheme.color.button,
      contentColor = QrCodeTheme.color.onButton,
    ),
    onClick = onClick,
  ) {
    Text(text = QrLocale.strings.exit, style = QrCodeTheme.typo.button, color = QrCodeTheme.color.neutral5)
  }
}

@Composable
private fun CancelButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
  Button(
    modifier = modifier.size(QrCodeTheme.dimen.buttonHeight),
    colors = ButtonDefaults.buttonColors(
      containerColor = QrCodeTheme.color.neutral4,
      contentColor = QrCodeTheme.color.onButton,
    ),
    onClick = onClick,
  ) {
    Text(text = QrLocale.strings.cancel, style = QrCodeTheme.typo.button, color = QrCodeTheme.color.neutral3)
  }
}

@Preview
@Composable
private fun ExitAppBottomSheetPreview() {
  ExitAppBottomSheet(
    isBottomSheetVisible = true,
    onExitClicked = {},
    onDismissRequest = {},
  )
}
