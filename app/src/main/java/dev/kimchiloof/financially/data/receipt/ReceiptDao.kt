package dev.kimchiloof.financially.data.receipt

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface ReceiptDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(receipt: Receipt)

    @Update
    suspend fun update(receipt: Receipt)

    @Delete
    suspend fun delete(receipt: Receipt)

    @Query("SELECT * FROM receipts")
    fun getAllReceipts(): Flow<List<Receipt?>>

    @Query("SELECT * from receipts WHERE category = :category")
    fun getReceiptsByCategory(category: String): Flow<List<Receipt?>>

    @Query("SELECT * from receipts WHERE date = :date")
    fun getReceipt(date: LocalDate): Flow<Receipt?>
}
