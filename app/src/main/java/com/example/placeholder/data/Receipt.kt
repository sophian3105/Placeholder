package com.example.placeholder.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.io.File

@Entity(tableName = "receipts")
data class Receipt(
    @PrimaryKey @ColumnInfo(name = "receiptName") var receiptName: String,
    @ColumnInfo(name = "receiptImage", typeAffinity = ColumnInfo.TEXT) var receiptImage: File,
    @ColumnInfo(name = "receiptAmount") var receiptAmount: Double,
    @ColumnInfo(name = "receiptCategory") var receiptCategory: String
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
