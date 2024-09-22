package dev.kimchiloof.financially.ui.pages.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.kimchiloof.financially.Greeting

@Composable
fun HomeScreen() {
    var showDialog by remember { mutableStateOf(false) }
    var spendingGoal by remember { mutableStateOf("") }
    var amountSpent by remember { mutableStateOf(0.0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Money Left: \$${spendingGoal.toDoubleOrNull()?.minus(amountSpent) ?: 0.0}",
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Button(onClick = { showDialog = true }) {
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
                    TextField(
                        value = spendingGoal,
                        onValueChange = { spendingGoal = it },
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