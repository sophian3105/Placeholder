package com.example.placeholder.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.io.File

@Entity(tableName = "receipts")
data class Receipt(
    @PrimaryKey val receiptName: String,
    val receiptPath: String,
    val receiptAmount: Double,
    val receiptCategory: String
)

@ProvidedTypeConverter
class ReceiptConverters {
    @TypeConverter
    fun stringToFile(pathAsString: String): File {
        return File(pathAsString)
    }

    @TypeConverter
    fun fileToString(file: File): String {
        return file.absolutePath
    }
}
