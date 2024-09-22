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

    @Query("SELECT * FROM receipts WHERE id = :id")
    fun getReceipt(id: Int): Flow<Receipt?>

    @Query("""
        SELECT * FROM receipts
        WHERE (:name IS NULL OR name LIKE '%' || :name || '%')
        AND (:category IS NULL OR category LIKE '%' || :category || '%')
        AND (:min IS NULL OR amount >= :min)
        AND (:max IS NULL OR amount <= :max)
        AND (:start IS NULL OR date >= :start)
        AND (:end IS NULL OR date <= :end)
    """)
    fun getFilteredReceipts(
        name: String?,
        start: LocalDate?,
        end: LocalDate?,
        min: Double?,
        max: Double?,
        category: String?
    ): Flow<List<Receipt?>>
}
