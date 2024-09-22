package dev.kimchiloof.financially.ui.pages.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.kimchiloof.financially.Greeting
import dev.kimchiloof.financially.MainViewModel
import dev.kimchiloof.financially.utils.Constants.Companion.DATE_FORMAT
import dev.kimchiloof.financially.utils.Constants.UI.RADIUS

@Composable
fun GalleryScreen(viewModel: MainViewModel = viewModel()) {
    val context = LocalContext.current

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
                            .padding(16.dp),
                        shape = RoundedCornerShape(RADIUS.L.dp),
                        elevation = CardDefaults.elevatedCardElevation(8.dp)
                    ) {
                        Row (
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(context).data(receipt.image).build(),
                                contentDescription = "Captured image",
                                modifier = Modifier.fillMaxWidth(0.5f).clip(RoundedCornerShape(RADIUS.M.dp))
                            )
                            Column (
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(text = receipt.name, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                                HorizontalDivider(thickness = 1.dp, color = Color.White, modifier = Modifier.padding(0.dp, 4.dp))
                                Text(text = receipt.category)
                                Text(text = "$ " + receipt.amount.toString())
                                Text(text = receipt.date.format(DATE_FORMAT))
                            }
                        }
                    }
                }
            }
        }
    }
}
