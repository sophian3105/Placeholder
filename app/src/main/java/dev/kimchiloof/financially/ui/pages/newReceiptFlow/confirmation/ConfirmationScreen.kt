package dev.kimchiloof.financially.ui.pages.newReceiptFlow.confirmation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.kimchiloof.financially.NewReceiptFlowViewModel

@Composable
fun ConfirmationScreen(navController: NavController, activityViewModel: NewReceiptFlowViewModel) {
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { ReceiptInformationInput(navController, activityViewModel) }
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            val path = activityViewModel.receiptImage?.path
            AsyncImage(
                model = ImageRequest.Builder(context).data(path).build(),
                contentDescription = "Captured image",
                modifier = Modifier.fillMaxHeight().fillMaxWidth()
            )
        }
    }

}
