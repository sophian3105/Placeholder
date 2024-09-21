package dev.kimchiloof.financially.ui.pages.camera

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.icons.FeatherIcons
import compose.icons.feathericons.Aperture
import compose.icons.feathericons.FolderPlus
import compose.icons.feathericons.RefreshCw

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

@Composable
fun CaptureButton(captureCameraCallback: () -> Unit) {
    Button(
        modifier = Modifier.size(56.dp),
        contentPadding = PaddingValues(0.dp),
        shape = CircleShape,
        onClick = { captureCameraCallback() },
    ) { Icon(FeatherIcons.Aperture, "Capture photo") }
}

@Composable
fun SelectGalleryButton() {
    IconButton (
        onClick = { },
    ) { Icon(FeatherIcons.FolderPlus, "Open gallery")}
}

@Composable
fun FlipCameraButton(flipCameraCallback: () -> Unit) {
    IconButton (
        onClick = { flipCameraCallback() },
    ) { Icon(FeatherIcons.RefreshCw, "Flip camera") }
}
