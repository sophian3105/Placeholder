package dev.kimchiloof.financially.ui.pages.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kimchiloof.financially.MainViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.kimchiloof.financially.ui.components.HalfCircleTracker

@Composable
fun HomeScreen(viewModel: MainViewModel = viewModel()) {
    var showDialog by remember { mutableStateOf(false) }

    // Fetch all receipts from the ViewModel
    val allReceipts by viewModel.getAllReceipts().collectAsState(initial = emptyList())

    // Calculate the total amount spent
    LaunchedEffect(allReceipts) {
        val totalAmountSpent = allReceipts.sumOf { it?.amount ?: 0.0 }
        viewModel.saveAmountSpent(totalAmountSpent)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { HomeHeader() }

        item { Spacer(modifier = Modifier.height(50.dp)) } // Add spacing here

        item {
            val spendingGoal by viewModel.spendingGoal.observeAsState("")
            val amountSpent by viewModel.amountSpent.observeAsState(0.0)

            HalfCircleTracker(
                spendingGoal = spendingGoal.toDoubleOrNull() ?: 0.0,
                amountSpent = amountSpent
            )
        }

        item {
            val spendingGoal by viewModel.spendingGoal.observeAsState("")
            val amountSpent by viewModel.amountSpent.observeAsState(0.0)

            Text(
                text = "Total Amount Spent: \$${amountSpent}",
            )
            Text(
                text = "Money Left: \$${spendingGoal.toDoubleOrNull()?.minus(amountSpent) ?: 0.0}",
                modifier = Modifier.padding(top = 8.dp) // Reduced top padding
            )
            Button(
                onClick = { showDialog = true },
                modifier = Modifier.padding(top = 8.dp) // Reduced top padding
            ) {
                Text("Set Monthly Spending Goal")
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Monthly Spending Goal") },
            text = {
                Column {
                    Text("Enter your monthly spending goal:")
                    val spendingGoal by viewModel.spendingGoal.observeAsState("")
                    TextField(
                        value = spendingGoal,
                        onValueChange = { viewModel.saveSpendingGoal(it) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    // Handle the spending goal input
                    showDialog = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun HomeHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 48.dp, 16.dp, 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text("Home", fontSize = 32.sp, fontWeight = FontWeight.Bold)
    }
}