package ptit.vietpq.qr_scanner.data.datalocal.sharepref

import android.content.SharedPreferences
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharePreferenceProvider
@Inject
constructor(val sharedPreferences: SharedPreferences, val moshi: Moshi) {
  companion object {
    const val NAME_SHARE_PREFERENCE = "QrCodeScanner"
    const val ONBOARD_LANGUAGE = "onboard_language"
    const val ONBOARD_INTRO = "onboard_intro"
    const val CURRENT_LANGUAGE_CODE = "current_language_code"
    const val SHOW_GUIDE_LINE_KEY = "show_guide_line_key"
    const val IS_BEEP_ENABLE = "is_beep_enable"
    const val IS_VIBRATE_ENABLE = "is_vibrate_enable"
    const val KEY = "key"
  }

  @ToJson
  inline fun <reified T> save(key: String, any: Any) {
    val editor = sharedPreferences.edit()
    when (any) {
      is String -> editor.putString(key, any)
      is Float -> editor.putFloat(key, any)
      is Int -> editor.putInt(key, any)
      is Long -> editor.putLong(key, any)
      is Boolean -> editor.putBoolean(key, any)
      else -> {
        val adapter = moshi.adapter(any.javaClass)
        editor.putString(key, adapter.toJson(any))
      }
    }
    editor.apply()
  }

  @FromJson
  inline fun <reified T> get(key: String): T? {
    when (T::class) {
      Float::class -> return sharedPreferences.getFloat(key, 0f) as T
      Int::class -> return sharedPreferences.getInt(key, 0) as T
      Long::class -> return sharedPreferences.getLong(key, 0) as T
      String::class -> return sharedPreferences.getString(key, "") as T
      Boolean::class -> return sharedPreferences.getBoolean(key, false) as T
      else -> {
        val any = sharedPreferences.getString(key, "")
        val adapter = moshi.adapter(T::class.java)
        if (!any.isNullOrEmpty()) {
          return adapter.fromJson(any)
        }
      }
    }
    return null
  }
}
