package ptit.vietpq.qr_scanner.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BarCodeResultEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Int = 0,
  val text: String,
  val formattedText: String,
  val format: Int,
  val typeSchema: Int,
  val date: Long = System.currentTimeMillis(),
  val isCreated: Boolean = false,
  val isFavorite: Boolean = false,
)
