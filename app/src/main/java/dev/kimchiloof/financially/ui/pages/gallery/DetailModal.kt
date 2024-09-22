package dev.kimchiloof.financially.ui.pages.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.kimchiloof.financially.MainViewModel
import dev.kimchiloof.financially.data.receipt.Receipt
import dev.kimchiloof.financially.ui.pages.newReceiptFlow.confirmation.EditReceipt
import dev.kimchiloof.financially.utils.Constants
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun DetailModal(id: Int, onDismiss: () -> Unit, viewModel: MainViewModel = viewModel()) {
    val context = LocalContext.current
    val receipt = viewModel.getReceipt(id).collectAsState(initial = null).value ?: return

    var editMode by remember { mutableStateOf(false) }
    var saveNow by remember { mutableStateOf(false) }

    Dialog (
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(0.85f)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        Color.DarkGray,
                        shape = RoundedCornerShape(Constants.UI.RADIUS.L.dp)
                    )
                    .padding(16.dp)
            ) {
                Column {
                    AsyncImage(
                        model = ImageRequest.Builder(context).data(receipt.image).build(),
                        contentDescription = "Captured image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(Constants.UI.RADIUS.M.dp))
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    ViewAndEditDetailsSection(viewModel, receipt, editMode, saveNow, { editMode = false; saveNow = false })
                }
            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FilledTonalButton(
                    onClick = { if (!editMode) onDismiss() else { editMode = false; saveNow = false; }},
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f)
                ) { Text(if (!editMode) "Dismiss" else if (saveNow) "Done" else "Cancel") }
                Spacer(modifier = Modifier.weight(0.5f))
                Button(
                    onClick = {
                        if (!editMode) { editMode = true }
                        else { saveNow = true } },
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f)
                ) { Text(if (!editMode) "Edit" else "Save") }
            }
        }
    }
}

@Composable
fun ViewAndEditDetailsSection(viewModel: MainViewModel, receipt: Receipt, edit: Boolean, saveNow: Boolean, onSave: () -> Unit) {
    if (edit) { EditDetailsSection(viewModel, receipt, saveNow, onSave) }
    else { ViewDetailsSection(receipt) }
}

@Composable
fun ViewDetailsSection(receipt: Receipt) {
    Column {
        Text(text = receipt.name, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        HorizontalDivider(thickness = 1.dp, color = Color.White, modifier = Modifier.padding(0.dp, 4.dp))
        Text(text = receipt.category)
        Text(text = "$ " + receipt.amount.toString())
        Text(text = receipt.date.format(Constants.DATE_FORMAT))
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Added on: " + receipt.dateCreated.format(Constants.DATE_FORMAT))
    }
}

@Composable
fun EditDetailsSection(viewModel: MainViewModel, receipt: Receipt, saveNow: Boolean, onSave: () -> Unit) {
    var name by rememberSaveable { mutableStateOf(receipt.name) }
    var amount by rememberSaveable { mutableDoubleStateOf(receipt.amount) }
    var date by rememberSaveable { mutableStateOf(receipt.date) }
    var category by rememberSaveable { mutableStateOf(receipt.category) }

    EditReceipt(
        oldName = receipt.name,
        oldAmount = receipt.amount,
        oldDate = receipt.date,
        oldCategory = receipt.category,
        onNameChange = { name = it },
        onAmountChange = { amount = it },
        onDateChange = { date = it },
        onCategoryChange = { category = it }
    )

    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(saveNow) {
        if (saveNow) {
            coroutineScope.launch {
                saveUpdatedReceipt(
                    viewModel,
                    receipt,
                    name,
                    amount,
                    date,
                    category
                )
            }
            onSave()
        }
    }
}

suspend fun saveUpdatedReceipt(
    viewModel: MainViewModel,
    receipt: Receipt,
    newName: String,
    newAmount: Double,
    newDate: LocalDate,
    newCategory: String
) {
    receipt.name = newName
    receipt.amount = newAmount
    receipt.date = newDate
    receipt.category = newCategory
    viewModel.updateReceipt(receipt)
}
