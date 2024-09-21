package dev.kimchiloof.financially.ui.pages.newReceiptFlow.camera.controls

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import compose.icons.FeatherIcons
import compose.icons.feathericons.RefreshCw

@Composable
fun FlipCameraButton(flipCameraCallback: () -> Unit) {
    IconButton (
        onClick = { flipCameraCallback() },
    ) { Icon(FeatherIcons.RefreshCw, "Flip camera") }
}
