package ptit.vietpq.qr_scanner.data.datalocal.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ptit.vietpq.qr_scanner.data.entity.BarCodeResultEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BarCodeResultDao {
  @Query("SELECT * FROM BarCodeResultEntity ORDER BY date DESC")
  fun getHistory(): Flow<List<BarCodeResultEntity>>

  @Insert
  suspend fun insertHistory(barCodeResultEntity: BarCodeResultEntity)

  @Update
  suspend fun updateHistory(barCodeResultEntity: BarCodeResultEntity)

  @Delete
  suspend fun deleteHistories(barCodeResultEntity: BarCodeResultEntity)

  @Query("DELETE FROM BarCodeResultEntity where isCreated = :isCreated")
  suspend fun deleteAll(isCreated: Boolean)
}
