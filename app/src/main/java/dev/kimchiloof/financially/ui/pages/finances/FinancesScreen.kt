package dev.kimchiloof.financially.ui.pages.finances

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import dev.kimchiloof.financially.utils.DatePickerContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinancesScreen() {
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = { showDatePicker = true }) {
            Text(text = "Select Date")
        }

        selectedDate?.let {
            Text(text = "Selected Date: ${it.format(DateTimeFormatter.ISO_LOCAL_DATE)}")
        }

        if (showDatePicker) {
            Dialog(onDismissRequest = { showDatePicker = false }) {
                Surface {
                    DatePickerContent(
                        context = context,
                        onDateSelected = { date ->
                            selectedDate = date
                            showDatePicker = false
                        },
                        onCancel = { showDatePicker = false }
                    )
                }
            }
        }
    }
}