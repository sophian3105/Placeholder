package dev.kimchiloof.financially.data.receipt

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.kimchiloof.financially.utils.Constants
import java.io.File
import java.time.LocalDate

@Entity(tableName = Constants.DATABASE_NAME)
data class Receipt(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "date", typeAffinity = ColumnInfo.INTEGER)
    val date: LocalDate,
    @ColumnInfo(name = "date_created", typeAffinity = ColumnInfo.INTEGER)
    val dateCreated: LocalDate,
    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.TEXT)
    val image: File,
    @ColumnInfo(name = "amount")
    val amount: Double,
    @ColumnInfo(name = "category")
    val category: String
)
