package dev.kimchiloof.financially.ui.pages.finances

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
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
        FinanceHeader() // Place the header at the top

        Spacer(modifier = Modifier.height(16.dp)) // Add spacer

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = { showDatePicker = true }) {
                Text(text = "Select Date")
            }
        }

        Spacer(modifier = Modifier.height(16.dp)) // Add spacer

        selectedDate?.let {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Selected Date: ${it.format(DateTimeFormatter.ISO_LOCAL_DATE)}")
            }
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

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Total Amount Spent: $totalAmountSpent")
            }
        }
    }
}

@Composable
fun FinanceHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 48.dp, 16.dp, 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text("Finance", fontSize = 32.sp, fontWeight = FontWeight.Bold)
    }
}