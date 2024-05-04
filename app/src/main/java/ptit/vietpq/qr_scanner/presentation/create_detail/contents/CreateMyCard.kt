package ptit.vietpq.qr_scanner.presentation.create_detail.contents

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale
import ptit.vietpq.qr_scanner.extension.toDateFormatted
import ptit.vietpq.qr_scanner.presentation.create_detail.components.CreateButton
import ptit.vietpq.qr_scanner.presentation.create_detail.components.InputField
import java.util.Calendar

@Composable
fun CreateMyCard(
  onCreateClicked: (
    name: String,
    phoneNumber: String,
    email: String,
    address: String,
    company: String,
    dateOfBirth: String,
    description: String,
  ) -> Unit,
  modifier: Modifier = Modifier,
) {
  val context = LocalContext.current
  var nameTextState by rememberSaveable { mutableStateOf("") }
  var phoneNumberTextState by rememberSaveable { mutableStateOf("") }
  var emailTextState by rememberSaveable { mutableStateOf("") }
  var addressTextState by rememberSaveable { mutableStateOf("") }
  var companyTextState by rememberSaveable { mutableStateOf("") }
  var descriptionTextState by rememberSaveable { mutableStateOf("") }
  var dateOfBirth by rememberSaveable { mutableLongStateOf(System.currentTimeMillis()) }

  var showDatePicker by rememberSaveable { mutableStateOf(false) }

  LaunchedEffect(showDatePicker) {
    if (showDatePicker) {
      showDatePicker(
        context,
      ) {
        dateOfBirth = it
      }
      showDatePicker = false
    }
  }

  Box(
    modifier = modifier.background(QrCodeTheme.color.backgroundCard),
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
            painter = painterResource(id = R.drawable.ic_my_business_card),
            contentDescription = null,
          )
        }
        InputField(
          title = QrLocale.strings.name,
          placeholder = QrLocale.strings.pleaseEnterEventTitle,
          value = nameTextState,
          onValueChange = {
            nameTextState = it
          },
        )
        InputField(
          title = QrLocale.strings.phoneNumber,
          placeholder = QrLocale.strings.pleaseEnterYourPhoneNumber,
          value = phoneNumberTextState,
          onValueChange = {
            phoneNumberTextState = it
          },
          keyboardOption = KeyboardOptions(
            keyboardType = KeyboardType.Phone,
          ),
        )
        InputField(
          title = QrLocale.strings.eMail,
          placeholder = QrLocale.strings.pleaseEnterYourEmail,
          value = emailTextState,
          onValueChange = {
            emailTextState = it
          },
          regex = Regex(".+@.+\\..+"),
        )
        InputField(
          title = QrLocale.strings.address,
          placeholder = QrLocale.strings.pleaseEnterYourAddress,
          value = addressTextState,
          onValueChange = {
            addressTextState = it
          },
        )
        InputField(
          title = QrLocale.strings.birthday,
          placeholder = QrLocale.strings.pleaseEnterYourBirthday,
          isEnable = false,
          value = dateOfBirth.toDateFormatted(),
          modifier = Modifier.clickable {
            showDatePicker = true
          },
        )
        InputField(
          title = QrLocale.strings.company,
          placeholder = QrLocale.strings.pleaseEnterYourCompanyAddress,
          value = companyTextState,
          onValueChange = {
            companyTextState = it
          },
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
    Box(
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .fillMaxWidth()
        .padding(bottom = QrCodeTheme.dimen.marginDefault),
    ) {
      CreateButton(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = QrCodeTheme.dimen.marginDefault),
        isEnable = nameTextState.isNotEmpty() && descriptionTextState.isNotEmpty() &&
          phoneNumberTextState.isNotEmpty() && emailTextState.isNotEmpty() && addressTextState.isNotEmpty() &&
          companyTextState.isNotEmpty() && emailTextState.matches(Regex(".+@.+\\..+")),
        onClick = {
          onCreateClicked(
            nameTextState,
            phoneNumberTextState,
            emailTextState,
            addressTextState,
            companyTextState,
            dateOfBirth.toDateFormatted(),
            descriptionTextState,
          )
        },
      )
    }
  }
}

fun showDatePicker(context: Context, onDatePicked: (Long) -> Unit) {
  val primaryColor = context.resources.getColor(R.color.primary, null)
  val datePickerDialog = DatePickerDialog(
    context,
    R.style.ThemeOverlay_QRCodeScannerScanBarcode_Dialog,
    { _, year, month, dayOfMonth ->
      val calendar = Calendar.getInstance()
      calendar.set(year, month, dayOfMonth)
      onDatePicked(calendar.timeInMillis)
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
