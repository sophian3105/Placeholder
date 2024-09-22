package dev.kimchiloof.financially.ui.pages.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.kimchiloof.financially.MainViewModel
import dev.kimchiloof.financially.utils.Constants
import kotlinx.coroutines.launch

@Composable
fun DeleteModal(id: Int, onDismiss: () -> Unit, viewModel: MainViewModel = viewModel()) {
    val coroutineScope = rememberCoroutineScope()
    val receipt = viewModel.getReceipt(id).collectAsState(initial = null).value ?: return

    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Box(
            modifier = Modifier
                .background(
                    Color.DarkGray,
                    shape = RoundedCornerShape(Constants.UI.RADIUS.L.dp)
                )
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("Delete this receipt?", modifier = Modifier.padding(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    FilledTonalButton(
                        onClick = { onDismiss() },
                        modifier = Modifier
                            .padding(16.dp)
                            .weight(1f)
                    ) { Text("Cancel") }
                    Spacer(modifier = Modifier.weight(0.3f))
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                viewModel.deleteReceipt(receipt)
                                onDismiss()
                            }
                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .weight(1f)
                    ) { Text("Delete") }
                }

            }
        }
    }
}
