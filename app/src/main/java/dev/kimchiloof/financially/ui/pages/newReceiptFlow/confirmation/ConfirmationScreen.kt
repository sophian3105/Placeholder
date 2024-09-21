package dev.kimchiloof.financially.ui.pages.newReceiptFlow.confirmation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.kimchiloof.financially.NewReceiptFlowViewModel
import dev.kimchiloof.financially.utils.endActivity

@Composable
fun ConfirmationScreen(navController: NavController, viewModel: NewReceiptFlowViewModel = viewModel()) {
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text("cofirmation screen")
            Log.e("ConfirmationScreen", "Selected image: ${viewModel.selectedImage?.absolutePath}")
            val path = viewModel.selectedImage?.path
            if (path == null) Toast.makeText(LocalContext.current, "whoop", Toast.LENGTH_SHORT).show()
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(path).build(),
                contentDescription = "Captured image",
                modifier = Modifier.fillMaxSize()
            )
            Button(
                modifier = Modifier.border(1.dp, Color.Red),
                onClick = { navController.endActivity(context) }
            ) { Text("Done")}
        }
    }

}
