package dev.kimchiloof.financially.ui.pages.newReceiptFlow.camera

import androidx.camera.core.ImageCapture
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.kimchiloof.financially.NewReceiptFlowViewModel
import dev.kimchiloof.financially.navigation.Destination
import dev.kimchiloof.financially.ui.pages.newReceiptFlow.camera.controls.CaptureButton
import dev.kimchiloof.financially.ui.pages.newReceiptFlow.camera.controls.FlipCameraButton
import dev.kimchiloof.financially.ui.pages.newReceiptFlow.camera.controls.SelectGalleryButton
import dev.kimchiloof.financially.utils.endActivity

@Composable
fun CameraScreen(navController: NavController, activityViewModel: NewReceiptFlowViewModel) {
    val context = LocalContext.current
    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }

    val onSuccessAction: () -> Unit = { navController.navigate(Destination.Confirmation.route) }
    val onFailAction: () -> Unit = { navController.endActivity(context) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SelectGalleryButton(activityViewModel, onSuccessAction, onFailAction)
                CaptureButton { activityViewModel.takePhoto(context, imageCapture, onSuccessAction, onFailAction) }
                FlipCameraButton { activityViewModel.flipCamera() }
            }
        },
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            CameraPreview(
                onImageCapturedCallback = { imageCapture = it }
            )
        }
    }
}
