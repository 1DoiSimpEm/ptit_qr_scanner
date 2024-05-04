package ptit.vietpq.qr_scanner.presentation.create_detail.contents

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale
import ptit.vietpq.qr_scanner.extension.toDateFormatted
import ptit.vietpq.qr_scanner.extension.toTimeFormatted
import ptit.vietpq.qr_scanner.presentation.create_detail.components.CreateButton
import ptit.vietpq.qr_scanner.presentation.create_detail.components.CustomSwitchButton
import ptit.vietpq.qr_scanner.presentation.create_detail.components.DayPicker
import ptit.vietpq.qr_scanner.presentation.create_detail.components.InputField
import java.util.Calendar

@Composable
fun CalendarQR(
  onCreateClicked: (title: String, start: Long, end: Long, description: String) -> Unit,
  modifier: Modifier = Modifier,
) {
  val context = LocalContext.current
  val currentTime = System.currentTimeMillis()

  var titleTextState by rememberSaveable { mutableStateOf("") }
  var descriptionTextState by rememberSaveable { mutableStateOf("") }
  var isAllDay by rememberSaveable { mutableStateOf(false) }
  var startDate by rememberSaveable {
    mutableLongStateOf(
      currentTime,
    )
  }
  var endDate by rememberSaveable {
    mutableLongStateOf(
      currentTime + 3600000L,
    )
  }

  var showStartDatePicker by rememberSaveable { mutableStateOf(false) }
  var showEndDatePicker by rememberSaveable { mutableStateOf(false) }

  LaunchedEffect(showStartDatePicker) {
    if (showStartDatePicker) {
      showDateTimePicker(
        context,
      ) {
        startDate = it
      }
      showStartDatePicker = false
    }
  }

  LaunchedEffect(showEndDatePicker) {
    if (showEndDatePicker) {
      showDateTimePicker(
        context,
      ) {
        endDate = it
      }
      showEndDatePicker = false
    }
  }

  Box(
    modifier = modifier
      .background(QrCodeTheme.color.backgroundCard)
      .fillMaxSize(),
  ) {
    LazyColumn(
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 72.dp),
    ) {
      item {
        Box(
          modifier = Modifier
            .fillMaxSize()
            .padding(top = QrCodeTheme.dimen.marginDefault),
        ) {
          Image(
            modifier = Modifier
              .size(QrCodeTheme.dimen.qrTypeImageSize)
              .align(Alignment.Center),
            painter = painterResource(id = R.drawable.ic_calendar),
            contentDescription = null,
          )
        }
        InputField(
          title = QrLocale.strings.title,
          placeholder = QrLocale.strings.pleaseEnterEventTitle,
          value = titleTextState,
          onValueChange = {
            titleTextState = it
          },
        )
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(
              start = QrCodeTheme.dimen.marginDefault,
              end = QrCodeTheme.dimen.marginDefault,
            ),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Text(
            text = QrLocale.strings.allDay,
            style = QrCodeTheme.typo.body,
            color = QrCodeTheme.color.neutral1,
          )
          Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
          ) {
            CustomSwitchButton(
              value = isAllDay,
              onSwitched = {
                isAllDay = it
              },
            )
          }
        }
        DayPicker(
          modifier = Modifier
            .padding(QrCodeTheme.dimen.marginDefault)
            .clip(QrCodeTheme.shape.roundedLarge)
            .fillMaxWidth()
            .background(QrCodeTheme.color.neutral5)
            .clickable {
              showStartDatePicker = true
            },
          content = QrLocale.strings.start,
          date = if (isAllDay) startDate.toTimeFormatted() else startDate.toDateFormatted(),
        )
        DayPicker(
          modifier = Modifier
            .padding(start = QrCodeTheme.dimen.marginDefault, end = QrCodeTheme.dimen.marginDefault)
            .clip(QrCodeTheme.shape.roundedLarge)
            .fillMaxWidth()
            .background(QrCodeTheme.color.neutral5)
            .clickable {
              showEndDatePicker = true
            },
          content = QrLocale.strings.end,
          date = if (isAllDay) endDate.toTimeFormatted() else endDate.toDateFormatted(),
        )
        InputField(
          modifier = Modifier.height(96.dp),
          title = QrLocale.strings.description,
          placeholder = QrLocale.strings.pleaseFillInTheContent,
          value = descriptionTextState,
          onValueChange = {
            descriptionTextState = it
          },
        )
      }
    }
    CreateButton(
      modifier = Modifier
        .fillMaxWidth()
        .align(Alignment.BottomCenter)
        .padding(
          start = QrCodeTheme.dimen.marginDefault,
          end = QrCodeTheme.dimen.marginDefault,
          bottom = QrCodeTheme.dimen.marginDefault,
        ),
      isEnable = titleTextState.isNotEmpty() && descriptionTextState.isNotEmpty() &&
        startDate < endDate,
      onClick = {
        onCreateClicked(
          titleTextState,
          startDate,
          endDate,
          descriptionTextState,
        )
      },
    )
  }
}

fun showDateTimePicker(context: Context, onDatePicked: (Long) -> Unit) {
  val primaryColor = context.resources.getColor(R.color.primary, null)
  val datePickerDialog = DatePickerDialog(
    context,
    R.style.ThemeOverlay_QRCodeScannerScanBarcode_Dialog,
    { _, year, month, dayOfMonth ->
      val timePickerDialog = TimePickerDialog(
        context,
        R.style.ThemeOverlay_QRCodeScannerScanBarcode_Dialog,
        { _, hourOfDay, minute ->
          onDatePicked(
            Calendar.getInstance().apply {
              set(year, month, dayOfMonth, hourOfDay, minute)
            }.timeInMillis,
          )
        },
        Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
        Calendar.getInstance().get(Calendar.MINUTE),
        true,
      )
      timePickerDialog.show()
      timePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE)
        .setTextColor(primaryColor)
      timePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE)
        .setTextColor(primaryColor)
    },
    Calendar.getInstance().get(Calendar.YEAR),
    Calendar.getInstance().get(Calendar.MONTH),
    Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
  )
  datePickerDialog.show()
  datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE)
    .setTextColor(primaryColor)
  datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE)
    .setTextColor(primaryColor)
  datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, context.getString(R.string.commons__cancel)) { _, _ -> }
}

@Preview
@Composable
private fun CalendarQRPreview() {
  CalendarQR(
    onCreateClicked = { _, _, _, _ -> },
  )
}
