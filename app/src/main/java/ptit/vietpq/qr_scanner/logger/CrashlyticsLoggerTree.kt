package com.qrcode.qrscanner.barcode.barcodescan.qrreader.logger

import android.util.Log
import timber.log.Timber

class CrashlyticsLoggerTree : Timber.Tree() {
  override fun isLoggable(tag: String?, priority: Int): Boolean = priority >= Log.INFO

  override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
    // TODO record to FirebaseCrashlytics
  }
}
