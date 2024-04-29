package com.qrcode.qrscanner.barcode.barcodescan.qrreader.logger

import coil.EventListener
import coil.request.ErrorResult
import coil.request.ImageRequest
import javax.inject.Inject
import timber.log.Timber

class CoilEventListenerLogger
@Inject
constructor() : EventListener {
  override fun onError(request: ImageRequest, result: ErrorResult) {
    Timber
      .tag("CoilEventListenerLogger")
      .e(result.throwable, "onError: request.data=${request.data}")
  }
}
