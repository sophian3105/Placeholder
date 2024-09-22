package dev.kimchiloof.financially.ui.pages.finances

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.kimchiloof.financially.MainViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import dev.kimchiloof.financially.utils.DatePickerContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinancesScreen(viewModel: MainViewModel = viewModel()) {
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
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

        selectedDate?.let { date ->
            val filteredReceipts by viewModel.getFilteredReceipts(
                name = null,
                start = date,
                end = date,
                min = null,
                max = null,
                category = null
            ).collectAsState(initial = emptyList())

            val totalAmountSpent = filteredReceipts.sumOf { it?.amount ?: 0.0 }

            Text(text = "Total Amount Spent: $totalAmountSpent")
        }
    }
}