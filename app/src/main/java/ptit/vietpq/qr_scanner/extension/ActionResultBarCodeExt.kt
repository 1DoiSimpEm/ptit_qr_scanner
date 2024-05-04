package ptit.vietpq.qr_scanner.extension

import android.app.Activity
import android.app.SearchManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract
import android.provider.ContactsContract
import android.widget.Toast
import ptit.vietpq.qr_scanner.R
import java.util.Locale

fun Context.startActivityIfExists(intent: Intent) {
  intent.apply {
    flags = flags or Intent.FLAG_ACTIVITY_NEW_TASK
  }
  if (intent.resolveActivity(packageManager) != null) {
    startActivity(intent)
  } else {
    Toast.makeText(this, this.getString(R.string.error_occurred), Toast.LENGTH_SHORT).show()
  }
}

fun Context.startActivityIfExists(action: String, uri: String) {
  val intent = Intent(action, Uri.parse(uri))
  startActivityIfExists(intent)
}

fun Context.searchWeb(query: String) {
  val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
    putExtra(SearchManager.QUERY, query)
  }
  startActivityIfExists(intent)
}

fun Context.openUrl(url: String) = openUrl(Uri.parse(url))

fun Context.openUrl(url: Uri) {
  try {
    val browserIntent = Intent(Intent.ACTION_VIEW, url)
    if (this !is Activity) {
      browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    browserIntent.addCategory(Intent.CATEGORY_BROWSABLE)
    startActivity(browserIntent)
  } catch (e: ActivityNotFoundException) {
    Toast.makeText(this, getString(R.string.error_occurred), Toast.LENGTH_LONG).show()
  }
}

fun Context.openDirection(latitude: String?, longitude: String?) {
  val gmmIntentUri = Uri.parse("google.navigation:q=$latitude,$longitude")
  val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
    setPackage("com.google.android.apps.maps")
  }
  mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
  startActivity(mapIntent)
}

fun Context.openLocationMap(location: String?) {
  val gmmIntentUri = Uri.parse("google.navigation:q=${location.orEmpty()}")
  val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
    setPackage("com.google.android.apps.maps")
  }
  mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
  startActivity(mapIntent)
}

fun Context.openLocationMapWithDestination(latitude: String?, longitude: String?) {
  val gmmIntentUri =
    Uri.parse("geo:$latitude,$longitude")
  val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
    setPackage("com.google.android.apps.maps")
  }
  mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
  startActivity(mapIntent)
}

fun Context.addToCalendar(
  eventSummary: String?,
  eventDescription: String?,
  eventLocation: String?,
  eventStartDate: Long?,
  eventEndDate: Long?,
) {
  val intent = Intent(Intent.ACTION_INSERT).apply {
    data = CalendarContract.Events.CONTENT_URI
    putExtra(CalendarContract.Events.TITLE, eventSummary.orEmpty())
    putExtra(CalendarContract.Events.DESCRIPTION, eventDescription.orEmpty())
    putExtra(CalendarContract.Events.EVENT_LOCATION, eventLocation.orEmpty())
    putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, eventStartDate ?: 0L)
    putExtra(CalendarContract.EXTRA_EVENT_END_TIME, eventEndDate ?: 0L)
  }
  startActivityIfExists(intent)
}

fun Context.callFromDailer(number: String?) {
  try {
    val callIntent = Intent(Intent.ACTION_DIAL)
    callIntent.data = Uri.parse("tel:${number.orEmpty()}")
    this.startActivity(callIntent)
  } catch (e: Exception) {
    e.printStackTrace()
    Toast.makeText(this, "No SIM Found", Toast.LENGTH_LONG).show()
  }
}

fun Context.sendSmsOrMms(phone: String?, smsBody: String?) {
  val uri = Uri.parse("sms:${phone.orEmpty()}")
  val intent = Intent(Intent.ACTION_SENDTO, uri).apply {
    putExtra("sms_body", smsBody.orEmpty())
  }
  startActivityIfExists(intent)
}

fun Context.sendEmail(email: String?, emailSubject: String?, emailBody: String?) {
  val uri = Uri.parse("mailto:${email.orEmpty()}")
  val intent = Intent(Intent.ACTION_SEND, uri).apply {
    type = "text/plain"
    putExtra(Intent.EXTRA_EMAIL, arrayOf(email.orEmpty()))
    putExtra(Intent.EXTRA_SUBJECT, emailSubject.orEmpty())
    putExtra(Intent.EXTRA_TEXT, emailBody.orEmpty())
  }
  startActivityIfExists(intent)
}

fun Context.addToContacts(
  firstName: String? = null,
  lastName: String? = null,
  organization: String? = null,
  jobTitle: String? = null,
  phone: String? = null,
  phoneType: String? = null,
  secondaryPhone: String? = null,
  secondaryPhoneType: String? = null,
  tertiaryPhone: String? = null,
  tertiaryPhoneType: String? = null,
  email: String? = null,
  emailType: String? = null,
  secondaryEmail: String? = null,
  secondaryEmailType: String? = null,
  tertiaryEmail: String? = null,
  tertiaryEmailType: String? = null,
  note: String? = null,
) {
  val intent = Intent(ContactsContract.Intents.Insert.ACTION).apply {
    type = ContactsContract.Contacts.CONTENT_TYPE
    val fullName = "${firstName.orEmpty()} ${lastName.orEmpty()}"
    putExtra(ContactsContract.Intents.Insert.NAME, fullName)
    putExtra(ContactsContract.Intents.Insert.COMPANY, organization.orEmpty())
    putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle.orEmpty())
    putExtra(ContactsContract.Intents.Insert.PHONE, phone.orEmpty())
    putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, phoneType.orEmpty().toPhoneType())
    putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE, secondaryPhone.orEmpty())
    putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE_TYPE, secondaryPhoneType.orEmpty().toPhoneType())
    putExtra(ContactsContract.Intents.Insert.TERTIARY_PHONE, tertiaryPhone.orEmpty())
    putExtra(ContactsContract.Intents.Insert.TERTIARY_PHONE_TYPE, tertiaryPhoneType.orEmpty().toPhoneType())
    putExtra(ContactsContract.Intents.Insert.EMAIL, email.orEmpty())
    putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE, emailType.orEmpty().toEmailType())
    putExtra(ContactsContract.Intents.Insert.SECONDARY_EMAIL, secondaryEmail.orEmpty())
    putExtra(ContactsContract.Intents.Insert.SECONDARY_EMAIL_TYPE, secondaryEmailType.orEmpty().toEmailType())
    putExtra(ContactsContract.Intents.Insert.TERTIARY_EMAIL, tertiaryEmail.orEmpty())
    putExtra(ContactsContract.Intents.Insert.TERTIARY_EMAIL_TYPE, tertiaryEmailType.orEmpty().toEmailType())
    putExtra(ContactsContract.Intents.Insert.NOTES, note.orEmpty())
  }
  startActivityIfExists(intent)
}

fun String.toPhoneType(): Int? = when (uppercase(Locale.US)) {
  "HOME" -> ContactsContract.CommonDataKinds.Phone.TYPE_HOME
  "MOBILE", "CELL" -> ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
  "WORK" -> ContactsContract.CommonDataKinds.Phone.TYPE_WORK
  "FAX_WORK" -> ContactsContract.CommonDataKinds.Phone.TYPE_FAX_WORK
  "FAX_HOME" -> ContactsContract.CommonDataKinds.Phone.TYPE_FAX_HOME
  "PAGER" -> ContactsContract.CommonDataKinds.Phone.TYPE_PAGER
  "OTHER" -> ContactsContract.CommonDataKinds.Phone.TYPE_OTHER
  "CALLBACK" -> ContactsContract.CommonDataKinds.Phone.TYPE_CALLBACK
  "CAR" -> ContactsContract.CommonDataKinds.Phone.TYPE_CAR
  "COMPANY_MAIN" -> ContactsContract.CommonDataKinds.Phone.TYPE_COMPANY_MAIN
  "ISDN" -> ContactsContract.CommonDataKinds.Phone.TYPE_ISDN
  "MAIN" -> ContactsContract.CommonDataKinds.Phone.TYPE_MAIN
  "OTHER_FAX" -> ContactsContract.CommonDataKinds.Phone.TYPE_OTHER_FAX
  "RADIO" -> ContactsContract.CommonDataKinds.Phone.TYPE_RADIO
  "TELEX" -> ContactsContract.CommonDataKinds.Phone.TYPE_TELEX
  "TTY_TDD" -> ContactsContract.CommonDataKinds.Phone.TYPE_TTY_TDD
  "WORK_MOBILE" -> ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE
  "WORK_PAGER" -> ContactsContract.CommonDataKinds.Phone.TYPE_WORK_PAGER
  "ASSISTANT" -> ContactsContract.CommonDataKinds.Phone.TYPE_ASSISTANT
  "MMS" -> ContactsContract.CommonDataKinds.Phone.TYPE_MMS
  else -> null
}

fun String.toEmailType(): Int? = when (uppercase(Locale.US)) {
  "HOME" -> ContactsContract.CommonDataKinds.Email.TYPE_HOME
  "WORK" -> ContactsContract.CommonDataKinds.Email.TYPE_WORK
  "OTHER" -> ContactsContract.CommonDataKinds.Email.TYPE_OTHER
  "MOBILE" -> ContactsContract.CommonDataKinds.Email.TYPE_MOBILE
  else -> null
}
