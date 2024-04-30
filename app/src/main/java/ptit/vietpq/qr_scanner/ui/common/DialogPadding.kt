package com.qrcode.qrscanner.barcode.barcodescan.qrreader.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.designsystem.QrCodeTheme
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.designsystem.common.TwButton
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.designsystem.common.TwTextButton
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.designsystem.locale.QrLocale
import kotlinx.coroutines.launch

internal val DialogPadding = 24.dp
private val IconPadding = PaddingValues(bottom = 16.dp)
private val TitlePadding = PaddingValues(bottom = 16.dp)
private val TextPadding = PaddingValues(bottom = 24.dp)

private val MinWidth = 280.dp
private val MaxWidth = 560.dp

@Composable
fun BaseDialog(
  onDismissRequest: () -> Unit,
  modifier: Modifier = Modifier,
  title: String? = null,
  body: String? = null,
  bodyAnnotated: AnnotatedString? = null,
  positive: String? = null,
  negative: String? = null,
  onBodyClick: ((Int) -> Unit)? = null,
  onPositiveClick: (() -> Unit)? = null,
  onNegativeClick: (() -> Unit)? = null,
  positiveEnabled: Boolean = true,
  negativeEnabled: Boolean = true,
  showActionRow: Boolean = false,
  shape: Shape = QrCodeTheme.shape.dialog,
  containerColor: Color = QrCodeTheme.color.surface,
  properties: DialogProperties = DialogProperties(usePlatformDefaultWidth = false),
  buttonColorsPositive: ButtonColors = ButtonDefaults.buttonColors(
    containerColor = QrCodeTheme.color.neutral4,
    contentColor = QrCodeTheme.color.neutral3,
  ),
  buttonColorsNegative: ButtonColors = ButtonDefaults.buttonColors(
    containerColor = QrCodeTheme.color.button,
    contentColor = QrCodeTheme.color.onButton,
  ),
  content: @Composable () -> Unit = {},
) {
  val scope = rememberCoroutineScope()
  val showActions = positive != null || negative != null

  Dialog(
    onDismissRequest = onDismissRequest,
    properties = properties,
  ) {
    Surface(
      modifier = modifier
        .padding(start = 35.dp, end = 35.dp),
      shape = shape,
      color = containerColor,
    ) {
      Column(
        modifier = Modifier
          .sizeIn(minWidth = MinWidth, maxWidth = MaxWidth)
          .padding(
            top = DialogPadding,
            bottom = if (showActions) {
              8.dp
            } else {
              DialogPadding
            },
          ),
      ) {
        if (title != null) {
          Title(text = title)
        }

        if (body != null || bodyAnnotated != null) {
          Body(
            text = body,
            textAnnotated = bodyAnnotated,
            onBodyClick = onBodyClick,
          )
        }

        content()

        Spacer(modifier = Modifier.height(6.dp))

        if (showActions && !showActionRow) {
          Actions(
            positive = positive,
            negative = negative,
            onPositiveClick = {
              scope.launch {
                onPositiveClick?.invoke()
                onDismissRequest()
              }
            },
            onNegativeClick = {
              scope.launch {
                onNegativeClick?.invoke()
                onDismissRequest()
              }
            },
            positiveEnabled = positiveEnabled,
            negativeEnabled = negativeEnabled,
          )
        } else {
          ActionsRow(
            positive = positive,
            negative = negative,
            buttonColorsNegative = buttonColorsNegative,
            buttonColorsPositive = buttonColorsPositive,
            onPositiveClick = {
              scope.launch {
                onPositiveClick?.invoke()
                onDismissRequest()
              }
            },
            onNegativeClick = {
              scope.launch {
                onNegativeClick?.invoke()
                onDismissRequest()
              }
            },
            positiveEnabled = positiveEnabled,
            negativeEnabled = negativeEnabled,
          )
        }
        Spacer(modifier = Modifier.height(8.dp))
      }
    }
  }
}

@Composable
private fun Title(text: String) {
  Text(
    text = text,
    style = QrCodeTheme.typo.heading,
    color = QrCodeTheme.color.neutral1,
    textAlign = TextAlign.Center,
    modifier = Modifier
      .padding(horizontal = DialogPadding)
      .padding(TitlePadding),
  )
}

@Composable
private fun Body(text: String?, textAnnotated: AnnotatedString?, onBodyClick: ((Int) -> Unit)? = null) {
  if (text != null) {
    Text(
      text = text,
      style = QrCodeTheme.typo.body2,
      color = QrCodeTheme.color.neutral1,
      textAlign = TextAlign.Start,
      modifier = Modifier
        .padding(horizontal = DialogPadding)
        .padding(TitlePadding)
        .verticalScroll(rememberScrollState()),

    )
  } else if (textAnnotated != null) {
    ClickableText(
      text = textAnnotated,
      onClick = { onBodyClick?.invoke(it) },
      style = MaterialTheme.typography.bodyMedium.copy(color = QrCodeTheme.color.onSurfaceTertiary),
      modifier = Modifier
        .padding(horizontal = DialogPadding)
        .padding(TitlePadding),

    )
  }
}

@Composable
private fun Actions(
  positive: String? = null,
  negative: String? = null,
  onPositiveClick: (() -> Unit)? = null,
  onNegativeClick: (() -> Unit)? = null,
  positiveEnabled: Boolean = true,
  negativeEnabled: Boolean = true,
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = DialogPadding, vertical = 8.dp),
  ) {
    if (negative != null) {
      TwButton(
        modifier = Modifier.fillMaxWidth(),
        text = negative,
        enabled = negativeEnabled,
        onClick = { onNegativeClick?.invoke() },
      )

      Spacer(modifier = Modifier.width(8.dp))
    }
    if (positive != null) {
      TwTextButton(
        modifier = Modifier.fillMaxWidth(),
        text = positive,
        enabled = positiveEnabled,
        colorText = QrCodeTheme.color.neutral3,
        onClick = { onPositiveClick?.invoke() },
      )

      Spacer(modifier = Modifier.width(16.dp))
    }
  }
}

@Composable
fun ActionsRow(
  modifier: Modifier = Modifier,
  positive: String? = null,
  negative: String? = null,
  onPositiveClick: (() -> Unit)? = null,
  onNegativeClick: (() -> Unit)? = null,
  positiveEnabled: Boolean = true,
  negativeEnabled: Boolean = true,
  buttonColorsPositive: ButtonColors = ButtonDefaults.buttonColors(
    containerColor = QrCodeTheme.color.neutral4,
    contentColor = QrCodeTheme.color.neutral3,
  ),
  buttonColorsNegative: ButtonColors = ButtonDefaults.buttonColors(
    containerColor = QrCodeTheme.color.button,
    contentColor = QrCodeTheme.color.onButton,
  ),
) {
  Row(
    modifier = modifier
      .padding(horizontal = DialogPadding, vertical = 8.dp)
      .fillMaxWidth(),
    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceAround,
  ) {
    if (positive != null) {
      TwButton(
        modifier = Modifier
          .weight(1f),
        buttonColors = buttonColorsPositive,
        text = positive,
        enabled = positiveEnabled,
        onClick = { onPositiveClick?.invoke() },
      )
      Spacer(modifier = Modifier.width(16.dp))
    }
    if (negative != null) {
      TwButton(
        modifier = Modifier
          .weight(1f),
        buttonColors = buttonColorsNegative,
        text = negative,
        enabled = negativeEnabled,
        onClick = { onNegativeClick?.invoke() },
      )

      Spacer(modifier = Modifier.width(8.dp))
    }
  }
}

@Preview
@Composable
private fun Preview() {
  BaseDialog(
    onDismissRequest = { },
    title = QrLocale.strings.placeholder,
  )
}

@Preview
@Composable
private fun PreviewButtons() {
  BaseDialog(
    onDismissRequest = { },
    title = QrLocale.strings.placeholder,
    body = QrLocale.strings.placeholder,
    positive = "Ok",
    negative = "Cancel",
    positiveEnabled = false,
    showActionRow = false,
  )
}
