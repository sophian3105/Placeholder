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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.kimchiloof.financially.NewReceiptFlowViewModel
import dev.kimchiloof.financially.data.receipt.Receipt
import dev.kimchiloof.financially.navigation.Destination
import dev.kimchiloof.financially.utils.Constants.Companion.DATE_FORMAT
import dev.kimchiloof.financially.utils.DateSelector
import dev.kimchiloof.financially.utils.endActivity
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun ReceiptInformationInput(navController: NavController, activityViewModel: NewReceiptFlowViewModel) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var name by rememberSaveable { mutableStateOf("") }
    var amount by rememberSaveable { mutableDoubleStateOf(0.0) }
    var date by rememberSaveable { mutableStateOf(LocalDate.now()) }
    var category by rememberSaveable { mutableStateOf("") }

    suspend fun saveReceipt() {
        val image = activityViewModel.receiptImage ?: return
        val receipt = Receipt(
            name = name,
            amount = amount,
            image = image,
            date = date,
            category = category,
            dateCreated = LocalDate.now()
        )
        activityViewModel.insertReceipt(receipt)
    }

    Column (
        modifier = Modifier.padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        EditReceipt(
            oldName = name,
            oldAmount = amount,
            oldDate = date,
            oldCategory = category,
            onNameChange = { name = it },
            onAmountChange = { amount = it },
            onDateChange = { date = it },
            onCategoryChange = { category = it }
        )
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
                onClick = { coroutineScope.launch { saveReceipt() }; navController.endActivity(context) }
            ) {
                Text("Save")
            }
        }
    }
}


@Composable
fun EditReceipt(
    oldName: String,
    oldAmount: Double,
    oldDate: LocalDate,
    oldCategory: String,
    onNameChange: (String) -> Unit,
    onAmountChange: (Double) -> Unit,
    onDateChange: (LocalDate) -> Unit,
    onCategoryChange: (String) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var validAmount by remember { mutableStateOf(true) }
    var amountText by remember { mutableStateOf(if (oldAmount == 0.0) "" else oldAmount.toString()) }

    var name by remember { mutableStateOf(oldName) }
    var date by remember { mutableStateOf(oldDate) }
    var category by remember { mutableStateOf(oldCategory) }

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = name,
            label = { Text("Name") },
            singleLine = true,
            onValueChange = { onNameChange(it); name = it },
            modifier = Modifier.fillMaxWidth()
        )
    }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = category,
            label = { Text("Category") },
            singleLine = true,
            onValueChange = { onCategoryChange(it); category = it },
            modifier = Modifier.fillMaxWidth()
        )
    }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = amountText,
            label = { Text("Amount") },
            prefix = { Text("$") },
            placeholder = { Text("0.00") },
            onValueChange = {
                try { onAmountChange(if (it == "") 0.0 else it.toDouble()); validAmount = true; amountText = it }
                catch (e: NumberFormatException) { validAmount = false } },
            isError = !validAmount,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f)
        )
        OutlinedTextField(
            value = date.format(DATE_FORMAT),
            onValueChange = { },
            label = { Text("Date") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = !showDatePicker }) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "Select date")
                }
            },
            modifier = Modifier.weight(1f)
        )
        if (showDatePicker) {
            DateSelector(
                originalDate = date,
                onDateSelected = { onDateChange(it); date = it },
                onDismiss = { showDatePicker = false }
            )
        }
    }
}
