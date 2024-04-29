package ptit.vietpq.qr_scanner.data.datalocal

import android.content.Context
import androidx.annotation.AnyThread
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ptit.vietpq.qr_scanner.data.datalocal.dao.BarCodeResultDao
import ptit.vietpq.qr_scanner.data.entity.BarCodeResultEntity
import com.squareup.moshi.Moshi
import java.util.concurrent.Executor
import java.util.concurrent.Executors

private const val databaseVersion = 1
private const val databaseName = "QrCode-DataBase.db"

@Database(
  version = databaseVersion,
  entities = [BarCodeResultEntity::class],
)
abstract class AppQrDataBase : RoomDatabase() {
  abstract fun barCodeResultDao(): BarCodeResultDao

  companion object {
    // Use a separate thread for Room transactions to avoid deadlocks. This means that tests that run Room
    // transactions can't use testCoroutines.scope.runBlockingTest, and have to simply use runBlocking instead.
    @Volatile
    private var INSTANCE: AppQrDataBase? = null

    // Double-checked locking pattern, to avoid the overhead of synchronization once the instance has been initialized.
    // See https://en.wikipedia.org/wiki/Double-checked_locking#Usage_in_Java
    // Executors.newSingleThreadExecutor() is used to run the database operations asynchronously on a background thread.
    // See https://developer.android.com/training/data-storage/room/async-queries#kotlin
    @AnyThread
    @JvmStatic
    fun getInstance(context: Context, moshi: Moshi, queryExecutor: Executor): AppQrDataBase =
      INSTANCE ?: synchronized(this) {
        INSTANCE ?: Room.databaseBuilder(
          context.applicationContext,
          AppQrDataBase::class.java,
          databaseName,
        )
          .setTransactionExecutor(Executors.newSingleThreadExecutor())
          .setQueryExecutor(queryExecutor)
          .build()
          .also { INSTANCE = it }
      }
  }
}
