package dev.kimchiloof.financially.data.receipt

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.io.File
import java.time.LocalDate

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

    @TypeConverter
    fun longToDate(value: Long): LocalDate {
        return LocalDate.ofEpochDay(value)
    }

    @TypeConverter
    fun dateToLong(date: LocalDate): Long {
        return date.toEpochDay()
    }
}
