package dev.kimchiloof.financially.ui.pages.gallery

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.kimchiloof.financially.Greeting
import dev.kimchiloof.financially.MainViewModel

@Composable
fun GalleryScreen(viewModel: MainViewModel = viewModel()) {
    // TODO chunk fetching to avoid loading all receipts at once
    val allReceipts by viewModel.getAllReceipts().collectAsState(initial = emptyList())


        LazyColumn () {
            item {
                Greeting(name = "Gallery")
            }
            allReceipts.forEach { receipt ->
                if (receipt != null) {
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .defaultMinSize(minHeight = 400.dp)
                                .padding(8.dp)
                                .border(1.dp, color = Color.Red)
                        ) {
                            Greeting(name = receipt.name)
                        }
                    }
                }
            }
        }

}
