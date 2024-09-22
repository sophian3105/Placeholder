package dev.kimchiloof.financially.ui.pages.gallery

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.kimchiloof.financially.data.receipt.Receipt
import dev.kimchiloof.financially.utils.Constants

@Composable
fun GalleryCard(receipt: Receipt) {
    val context = LocalContext.current

    var openDetailModal by remember { mutableStateOf(false) }
    if (openDetailModal) {
        DetailModal(receipt.id, onDismiss = { openDetailModal = false })
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp)
            .clickable { openDetailModal = true },
        shape = RoundedCornerShape(Constants.UI.RADIUS.L.dp),
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
                modifier = Modifier.fillMaxWidth(0.5f).clip(RoundedCornerShape(Constants.UI.RADIUS.M.dp))
            )
            Column (
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = CardDefaults.elevatedCardColors(),
                    shape = RoundedCornerShape(Constants.UI.RADIUS.S.dp),
                ) {
                    Column (
                        modifier = Modifier.padding(12.dp),
                    ) {
                        Text(text = receipt.name, fontSize = 28.sp, fontWeight = FontWeight.Bold, lineHeight = 32.sp)
                        HorizontalDivider(thickness = 1.dp, color = Color.White, modifier = Modifier.padding(0.dp, 4.dp))
                        Text(text = receipt.category)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Text(text = "Total: ")
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "$ " + receipt.amount.toString())
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = receipt.date.format(Constants.DATE_FORMAT), textAlign = TextAlign.End, maxLines = 1)
            }
        }
    }
}
