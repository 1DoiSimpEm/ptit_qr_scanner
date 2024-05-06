package ptit.vietpq.qr_scanner.extension

import android.app.LocaleManager
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.os.LocaleList
import android.provider.Settings
import android.util.TypedValue
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.FileProvider
import androidx.core.os.LocaleListCompat
import ptit.vietpq.qr_scanner.BuildConfig
import ptit.vietpq.qr_scanner.MainActivity
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.utils.AppConstant
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.net.URLEncoder
import java.util.Locale

val Context.settingsIntent: Intent
  get() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName, null))
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    return intent
  }

fun Context.toPx(dp: Int): Float = TypedValue.applyDimension(
  TypedValue.COMPLEX_UNIT_DIP,
  dp.toFloat(),
  resources.displayMetrics,
)

fun Context.shareBitmap(bitmap: Bitmap) {
  val bitmapWithWhiteBackground = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)
  val canvas = Canvas(bitmapWithWhiteBackground)
  canvas.drawColor(android.graphics.Color.WHITE)
  canvas.drawBitmap(bitmap, 0f, 0f, null)

  val fileName = AppConstant.IMAGE_NAME_CACHE + ".png"
  val file = File(cacheDir, fileName)
  val fileOutputStream = FileOutputStream(file)

  bitmapWithWhiteBackground.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
  fileOutputStream.close()

  val contentUri = FileProvider.getUriForFile(this, "${BuildConfig.APPLICATION_ID}.fileprovider", file)
  val shareIntent = Intent().apply {
    action = Intent.ACTION_SEND
    putExtra(Intent.EXTRA_STREAM, contentUri)
    type = "image/png"
    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
  }
  startActivity(Intent.createChooser(shareIntent, getString(R.string.share)))
}

fun Context.startAppOrBrowser(appIntent: Intent, webIntent: Intent) {
  try {
    startActivity(appIntent)
  } catch (e: ActivityNotFoundException) {
    Timber.tag("startAppOrBrowser").d("Activity not found ${e.printStackTrace()}")
    startActivity(webIntent)
  }
}

fun Context.openFacebook(url: String) {
  val facebookAppIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://$url"))
  val facebookWebIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://$url"))
  startAppOrBrowser(facebookAppIntent, facebookWebIntent)
}

fun Context.openInstagram(url: String) {
  val instagramAppIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    .setPackage("com.instagram.android")
  val instagramWebIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
  startAppOrBrowser(instagramAppIntent, instagramWebIntent)
}

fun Context.openSpotify(url: String) {
  val instagramAppIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    .setPackage("com.spotify.music")
  val instagramWebIntent =
    Intent(Intent.ACTION_VIEW, Uri.parse("https://open.spotify.com/search/${URLEncoder.encode(url, "utf-8")}"))
  startAppOrBrowser(instagramAppIntent, instagramWebIntent)
}

fun Context.openTelegram(domain: String) {
  val uri = Uri.parse("tg://resolve?phone=$domain")
  val instagramAppIntent = Intent(Intent.ACTION_VIEW, uri)
    .setPackage("org.telegram.messenger")
  val instagramWebIntent = Intent(Intent.ACTION_VIEW, uri)
  startAppOrBrowser(instagramAppIntent, instagramWebIntent)
}

fun Context.openTiktok(domain: String) {
  val uri = Uri.parse("https://www.tiktok.com/$domain")
  val instagramAppIntent = Intent(Intent.ACTION_VIEW, uri)
    .setPackage("com.ss.android.ugc.trill")
  val instagramWebIntent = Intent(Intent.ACTION_VIEW, uri)
  startAppOrBrowser(instagramAppIntent, instagramWebIntent)
}

fun Context.openX(domain: String) {
  val uri = Uri.parse("https://twitter.com/$domain")
  val appIntent = Intent(Intent.ACTION_VIEW, uri)
    .setPackage("com.twitter.android")
  val webIntent = Intent(Intent.ACTION_VIEW, uri)
  startAppOrBrowser(appIntent, webIntent)
}

fun Context.openWhatsApp(phoneNumber: String) {
  val uri = Uri.parse("whatsapp://send?phone=$phoneNumber")
  val url = Uri.parse("https://api.whatsapp.com/send?phone=+$phoneNumber&text=")
  val appIntent = Intent(Intent.ACTION_VIEW, uri)
    .setPackage("com.whatsapp")
  val webIntent = Intent(Intent.ACTION_VIEW, url)
  startAppOrBrowser(appIntent, webIntent)
}

fun Context.openYoutube(url: String) {
  val intentApp = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$url"))
  val intentBrowser = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=$url"))
  startAppOrBrowser(intentApp, intentBrowser)
}

fun Context.setClipboard(text: String) {
  val clipboard =
    this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
  val clip = ClipData.newPlainText("Copy", text)
  clipboard.setPrimaryClip(clip)
}

fun Context.copyToClipboard(label: String, text: String) {
  val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
  val clip = ClipData.newPlainText(label, text)
  clipboard.setPrimaryClip(clip)
  Toast.makeText(this, this.getString(R.string.copied_to_clipboard), Toast.LENGTH_SHORT).show()
}

fun Context.restartApp() {
  val intent = Intent(this, MainActivity::class.java)
  intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
  startActivity(intent)
}

fun Context.shareText(text: String) {
  val sendIntent = Intent()
  sendIntent.action = Intent.ACTION_SEND
  sendIntent.putExtra(Intent.EXTRA_TEXT, text)
  sendIntent.type = "text/plain"
  startActivity(Intent.createChooser(sendIntent, null))
}

fun Context.showToast(message: String) {
  Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.openBrowser(url: String) {
  runCatching {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
  }
}

fun Context.setCurrentLanguage(code: String) {
  val config = resources.configuration
  val locale = Locale(code)
  Locale.setDefault(locale)
  config.setLocale(locale)
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    createConfigurationContext(config)
  }
  resources.updateConfiguration(config, resources.displayMetrics)
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    getSystemService(LocaleManager::class.java).applicationLocales = LocaleList.forLanguageTags(code)
  } else {
    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(code))
  }
}

fun Context.isInternetAvailable(): String {
  val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
  val network = connectivityManager.activeNetwork ?: return 0.toString()
  val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return 0.toString()
  return when {
    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> 1.toString()
    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> 1.toString()
    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> 1.toString()
    else -> 0.toString()
  }
}

fun Context.startFeedback(content: String = "") {
  val toEmail = "info@begamob.com"
  val release = Build.VERSION.RELEASE
  val sdk = Build.VERSION.SDK_INT
  val yourName = "customer"
  val phoneName = Build.MODEL
  val manager = packageManager
  var info: PackageInfo? = null
  try {
    info = manager.getPackageInfo(
      packageName, 0,
    )
  } catch (e: PackageManager.NameNotFoundException) {
    e.printStackTrace()
  }
  val version1 = info?.versionName
  val subject = "${getString(R.string.app_name)} feedback:"
  val message =
    """
        --------------------
        Device information:

        Phone name: $phoneName
        API Level: $sdk
        Version: $release
        App version: $version1
        Username: $yourName
        --------------------

        Content:
        $content
    """.trimIndent()
  val sendTo = Intent(Intent.ACTION_SENDTO)
  val uriText = "mailto:" + Uri.encode(toEmail) +
    "?subject=" + Uri.encode(subject) +
    "&body=" + Uri.encode(message)
  val uri = Uri.parse(uriText)
  sendTo.data = uri
  val resolveInfo =
    packageManager.queryIntentActivities(sendTo, 0)

  if (resolveInfo.isNotEmpty()) {
    val emailClientPackageName = resolveInfo[0].activityInfo.packageName
    sendTo.setPackage(emailClientPackageName)
    startActivity(sendTo)
  } else {
    val send = Intent(Intent.ACTION_SEND)
    send.type = "text/plain"
    send.putExtra(Intent.EXTRA_EMAIL, arrayOf(toEmail))
    send.putExtra(Intent.EXTRA_SUBJECT, subject)
    send.putExtra(Intent.EXTRA_TEXT, message)
    startActivity(send)
  }
}

fun Context.hideKeyboard() {
  val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
  imm.toggleSoftInput(android.view.inputmethod.InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
}

val Context.wifiManager: WifiManager?
  get() = applicationContext.getSystemService(Context.WIFI_SERVICE) as? WifiManager
