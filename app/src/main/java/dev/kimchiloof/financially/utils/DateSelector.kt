package dev.kimchiloof.financially.utils

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import java.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelector(originalDateSeconds: Long, onDateSelected: (Long) -> Unit, onDismiss: () -> Unit) {
    val originalDateMillis = Instant.ofEpochSecond(originalDateSeconds).toEpochMilli()
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = originalDateMillis)

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(Instant.ofEpochMilli(datePickerState.selectedDateMillis ?: originalDateMillis).epochSecond)
                onDismiss()
            }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
