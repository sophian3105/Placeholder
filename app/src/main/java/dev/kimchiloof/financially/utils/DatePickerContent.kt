package dev.kimchiloof.financially.utils

import android.app.DatePickerDialog
import android.content.Context
import java.time.LocalDate

fun DatePickerContent(
    context: Context,
    onDateSelected: (LocalDate) -> Unit,
    onCancel: () -> Unit
) {
    val currentDate = LocalDate.now()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
            onDateSelected(selectedDate)
        },
        currentDate.year,
        currentDate.monthValue - 1,
        currentDate.dayOfMonth
    )
    datePickerDialog.setOnCancelListener { onCancel() }
    datePickerDialog.show()
}