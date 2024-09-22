package dev.kimchiloof.financially.ui.pages.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.kimchiloof.financially.MainViewModel
import dev.kimchiloof.financially.utils.Constants.UI.RADIUS

@Composable
fun GalleryScreen(viewModel: MainViewModel = viewModel()) {
    val context = LocalContext.current

    // TODO chunk fetching to avoid loading all receipts at once
    val allReceipts by viewModel.getAllReceipts().collectAsState(initial = emptyList())

    LazyColumn () {
        item {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 48.dp, 16.dp, 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text("Gallery", fontSize = 32.sp, fontWeight = FontWeight.Bold)
            }
        }
        item {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Search receipts") },
                    shape = RoundedCornerShape(RADIUS.L.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Row {
                    DropdownMenu(
                        expanded = false,
                        onDismissRequest = { },
                    ) { Text("Filters") }
                    Button(
                        onClick = { },
                        modifier = Modifier.padding(8.dp)
                    ) { Text("Apply") }
                }
            }
        }

        allReceipts.forEach { receipt ->
            if (receipt != null) {
                item { GalleryCard(receipt) }
            }
        }
    }
}
