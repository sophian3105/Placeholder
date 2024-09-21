package dev.kimchiloof.financially.ui.pages.newReceiptFlow.confirmation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import dev.kimchiloof.financially.NewReceiptFlowViewModel

@Composable
fun ConfirmationScreen(navController: NavController, viewModel: NewReceiptFlowViewModel = viewModel()) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text("cofirmation screen")
            viewModel.selectedImage.value?.let { Text(it.path) }
        }
    }

}
