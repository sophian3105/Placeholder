package dev.kimchiloof.financially.ui.pages.newReceiptFlow.confirmation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.kimchiloof.financially.NewReceiptFlowViewModel
import dev.kimchiloof.financially.navigation.Destination
import dev.kimchiloof.financially.utils.DateSelector
import dev.kimchiloof.financially.utils.endActivity
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Composable
fun ReceiptInformationInput(navController: NavController, activityViewModel: NewReceiptFlowViewModel) {
    val context = LocalContext.current

    var name by rememberSaveable { mutableStateOf("") }
    var amount by rememberSaveable { mutableDoubleStateOf(0.0) }
    var date by rememberSaveable { mutableLongStateOf(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)) }
    var category by rememberSaveable { mutableStateOf("") }

    var showDatePicker by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier.padding(24.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = name,
                label = { Text("Name") },
                onValueChange = { name = it },
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Category:")
            OutlinedTextField(
                value = category,
                label = { Text("Category") },
                onValueChange = { category = it },
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Amount:")
            OutlinedTextField(
                value = amount.toString(),
                onValueChange = { amount = it.toDouble() },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Date:")
            OutlinedTextField(
                value = LocalDateTime.ofEpochSecond(date, 0, ZoneOffset.UTC).format(DateTimeFormatter.ISO_DATE),
                onValueChange = { },
                label = { Text("Date") },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = !showDatePicker }) {
                        Icon(imageVector = Icons.Default.DateRange, contentDescription = "Select date")
                    }
                }
            )
            if (showDatePicker) {
                DateSelector(
                    originalDateSeconds = date,
                    onDateSelected = { date = it },
                    onDismiss = { showDatePicker = false }
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextButton(
                onClick = {
                    activityViewModel.deleteImage()
                    navController.navigate(Destination.Camera.route)
                }
            ) {
                Text("Retake Image")
            }
            Button(
                onClick = { navController.endActivity(context) }
            ) {
                Text("Save")
            }
        }
    }
}
