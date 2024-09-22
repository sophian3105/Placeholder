package dev.kimchiloof.financially.ui.pages.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.kimchiloof.financially.MainViewModel
import dev.kimchiloof.financially.utils.Constants.UI.RADIUS
import java.time.LocalDate

@Composable
fun GalleryScreen(viewModel: MainViewModel = viewModel()) {
    val context = LocalContext.current

    // TODO chunk fetching to avoid loading all receipts at once
    val allReceipts by viewModel.getAllReceipts().collectAsState(initial = emptyList())

    var nameQuery by remember { mutableStateOf("") }
    var categoryQuery by remember { mutableStateOf("") }
    var dateQuery by remember { mutableStateOf(Pair(LocalDate.MIN, LocalDate.MAX)) }
    var amountQuery by remember { mutableStateOf(Pair(0.0, Double.MAX_VALUE)) }

    LazyColumn () {
        item { GalleryHeader() }
        item {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Search receipts") },
                    shape = RoundedCornerShape(RADIUS.L.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton (
                        onClick = { },
                        modifier = Modifier.padding(8.dp)
                    ) { Text("Clear") }
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


@Composable
fun GalleryHeader() {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 48.dp, 16.dp, 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text("Gallery", fontSize = 32.sp, fontWeight = FontWeight.Bold)
    }
}
