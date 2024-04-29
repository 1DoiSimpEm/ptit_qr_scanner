package com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.repository

import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarCodeResult
import kotlinx.coroutines.flow.Flow

interface BarCodeResultRepository {

  fun getHistory(): Flow<Result<List<BarCodeResult>>>

  suspend fun insertHistory(barCodeResult: BarCodeResult)

  suspend fun updateHistory(barCodeResult: BarCodeResult)

  suspend fun deleteHistory(barCodeResult: BarCodeResult)

  suspend fun deleteAll(isCreated: Boolean)
}
