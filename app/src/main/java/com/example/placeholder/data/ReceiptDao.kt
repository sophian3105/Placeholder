package com.example.placeholder.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ReceiptDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReceipt(receipt: Receipt)

    @Update
    suspend fun updateReceipt(receipt: Receipt)

    @Delete
    suspend fun deleteReceipt(receipt: Receipt)

    @Query("SELECT * FROM receipts")
    suspend fun getAllReceipts(): List<Receipt>

    @Query("SELECT receiptAmount FROM receipts")
    suspend fun getAllReceiptAmounts(): List<Double>

    @Query("SELECT * FROM receipts WHERE receiptCategory LIKE :queryReceiptCategory")
    suspend fun getAllReceiptsFromCategory(queryReceiptCategory: String): List<Receipt>
}