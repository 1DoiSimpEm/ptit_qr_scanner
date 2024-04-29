package com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.repository

import android.graphics.Bitmap
import android.net.Uri
import com.google.mlkit.vision.barcode.common.Barcode
import kotlinx.coroutines.flow.Flow

interface ProcessImageBarCodeRepository {
  fun observerImageBarCodeFromBitmap(bitmap: Bitmap): Flow<Barcode>
  fun observerImageBarCodeFromUri(uri: Uri): Flow<Barcode?>
}
