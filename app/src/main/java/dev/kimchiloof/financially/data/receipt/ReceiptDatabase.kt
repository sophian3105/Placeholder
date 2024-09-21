package dev.kimchiloof.financially.data.receipt

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Receipt::class], version = 1)
@TypeConverters(ReceiptConverters::class)
abstract class ReceiptDatabase : RoomDatabase() {

    // Receipt Data Access Object to interact with the database
    abstract fun receiptDao(): ReceiptDao

    companion object {
        @Volatile
        private var Instance: ReceiptDatabase? = null

        fun getDatabase(context: Context): ReceiptDatabase {
            // Maintain single database instance
            return Instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, ReceiptDatabase::class.java, "receiptDatabase")
                    .fallbackToDestructiveMigration()
                    .addTypeConverter(ReceiptConverters())
                    .build()

                Instance = instance
                instance
            }
        }
    }
}
