package com.example.placeholder.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Receipt::class], version = 1)
@TypeConverters(ReceiptConverters::class)
abstract class ReceiptDatabase : RoomDatabase() {
    abstract fun receiptDao(): ReceiptDao

    companion object {
        @Volatile
        private var INSTANCE: ReceiptDatabase? = null

        fun getDatabase(context: Context): ReceiptDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance

            synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    ReceiptDatabase::class.java,
                    "receipt_database"
                ).addTypeConverter(ReceiptConverters::class)
                    .build()

                INSTANCE = db
                return db
            }
        }
    }
}

@Dao
interface ReceiptDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReceipt(receipt: Receipt)

    @Delete
    suspend fun deleteReceipt(receipt: Receipt)

    @Query("SELECT * FROM receipts")
    suspend fun getAllReceipts(): List<Receipt>

    @Query("SELECT receiptAmount FROM receipts")
    suspend fun getAllReceiptAmounts(): List<Double>

    @Query("SELECT * FROM receipts WHERE receiptCategory LIKE :queryReceiptCategory")
    suspend fun getAllReceiptsFromCategory(queryReceiptCategory: String): List<Receipt>
}

