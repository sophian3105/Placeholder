package dev.kimchiloof.financially.ui.pages.newReceiptFlow.camera

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.kimchiloof.financially.ui.pages.newReceiptFlow.camera.controls.CaptureButton
import dev.kimchiloof.financially.ui.pages.newReceiptFlow.camera.controls.FlipCameraButton
import dev.kimchiloof.financially.ui.pages.newReceiptFlow.camera.controls.SelectGalleryButton

@Composable
fun CameraControls(
    flipCameraCallback: () -> Unit = {},
    captureCameraCallback: () -> Unit = {},
) {
    Row (
        modifier = Modifier.fillMaxWidth().padding(24.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SelectGalleryButton()
        CaptureButton(captureCameraCallback)
        FlipCameraButton(flipCameraCallback)
    }
}
