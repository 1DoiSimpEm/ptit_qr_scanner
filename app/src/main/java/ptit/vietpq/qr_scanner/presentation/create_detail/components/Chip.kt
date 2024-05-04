package ptit.vietpq.qr_scanner.presentation.create_detail.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ptit.vietpq.qr_scanner.presentation.create_detail.CreateQrType
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme

@Composable
fun Chip(chip: CreateQrType, isSelected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
  val backgroundColorState by animateColorAsState(
    targetValue =
    if (isSelected) QrCodeTheme.color.primary else QrCodeTheme.color.backgroundCard,
    animationSpec = TweenSpec(600),
    label = "",
  )
  val textColorState by animateColorAsState(
    targetValue =
    if (isSelected) QrCodeTheme.color.neutral5 else QrCodeTheme.color.neutral2,
    animationSpec = TweenSpec(600),
    label = "",
  )
  Surface(
    shape = QrCodeTheme.shape.roundedChip,
    color = backgroundColorState,
    modifier = modifier
      .clickable(onClick = onClick)
      .padding(QrCodeTheme.dimen.marginPrettySmall),
  ) {
    Text(
      text = stringResource(chip.titleResId),
      style = QrCodeTheme.typo.body,
      color = textColorState,
      modifier = Modifier.padding(
        horizontal = QrCodeTheme.dimen.marginDefault,
        vertical = QrCodeTheme.dimen.marginSmall,
      ),
    )
  }
}
