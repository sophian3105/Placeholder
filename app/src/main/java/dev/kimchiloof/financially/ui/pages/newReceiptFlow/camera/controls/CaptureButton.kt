package dev.kimchiloof.financially.ui.pages.newReceiptFlow.camera.controls

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.icons.FeatherIcons
import compose.icons.feathericons.Aperture

@Composable
fun CaptureButton(captureCameraCallback: () -> Unit) {
    Button(
        modifier = Modifier.size(56.dp),
        contentPadding = PaddingValues(0.dp),
        shape = CircleShape,
        onClick = { captureCameraCallback() },
    ) { Icon(FeatherIcons.Aperture, "Capture photo") }
}
