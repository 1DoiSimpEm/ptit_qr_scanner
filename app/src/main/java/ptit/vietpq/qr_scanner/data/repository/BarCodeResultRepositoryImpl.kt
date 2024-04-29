package ptit.vietpq.qr_scanner.data.repository

import ptit.vietpq.qr_scanner.core.AppCoroutineDispatchers
import ptit.vietpq.qr_scanner.data.datalocal.AppQrDataBase
import ptit.vietpq.qr_scanner.data.mapper.toBarCodeResult
import ptit.vietpq.qr_scanner.data.mapper.toBarCodeResultEntity
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarCodeResult
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.repository.BarCodeResultRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ptit.vietpq.qr_scanner.utils.asResultFlow
import ptit.vietpq.qr_scanner.utils.runSuspendCatching

class BarCodeResultRepositoryImpl @Inject constructor(
  private val qrAppDataBase: AppQrDataBase,
  private val coroutineDispatchers: AppCoroutineDispatchers,
) : BarCodeResultRepository {
  override fun getHistory(): Flow<Result<List<BarCodeResult>>> = qrAppDataBase
    .barCodeResultDao()
    .getHistory()
    .map { entity -> entity.map { it.toBarCodeResult() } }
    .asResultFlow()
    .flowOn(coroutineDispatchers.io)

  override suspend fun insertHistory(barCodeResult: BarCodeResult) {
    runSuspendCatching {
      withContext(coroutineDispatchers.io) {
        qrAppDataBase
          .barCodeResultDao()
          .insertHistory(barCodeResult.toBarCodeResultEntity())
      }
    }
  }

  override suspend fun updateHistory(barCodeResult: BarCodeResult) {
    runSuspendCatching {
      withContext(coroutineDispatchers.io) {
        qrAppDataBase.barCodeResultDao().updateHistory(barCodeResult.toBarCodeResultEntity())
      }
    }
  }

  override suspend fun deleteHistory(barCodeResult: BarCodeResult) {
    runSuspendCatching {
      withContext(coroutineDispatchers.io) {
        qrAppDataBase.barCodeResultDao().deleteHistories(barCodeResult.toBarCodeResultEntity())
      }
    }
  }

  override suspend fun deleteAll(isCreated: Boolean) {
    runSuspendCatching {
      withContext(coroutineDispatchers.io) {
        qrAppDataBase.barCodeResultDao().deleteAll(isCreated)
      }
    }
  }
}
