package com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.repository

import android.graphics.Bitmap

interface FileCacheBitmapRepository {
  suspend fun fetchCachedBitmap(fileName: String): Result<Bitmap>
  suspend fun cacheBitmap(bitmap: Bitmap): Result<String>
}
