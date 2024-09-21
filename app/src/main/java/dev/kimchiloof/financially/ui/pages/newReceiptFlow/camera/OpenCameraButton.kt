package dev.kimchiloof.financially.ui.pages.newReceiptFlow.camera

import android.Manifest
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.icons.FeatherIcons
import compose.icons.feathericons.Plus
import dev.kimchiloof.financially.utils.GetPermission

@Composable
fun CameraButton(
    onResultCallback: (Boolean) -> Unit
) {
    GetPermission(Manifest.permission.CAMERA, onResultCallback) { getPermission ->
        Button (
            modifier = Modifier.size(56.dp),
            contentPadding = PaddingValues(0.dp),
            shape = CircleShape,
            onClick = { getPermission() }
        ) { Icon(imageVector = FeatherIcons.Plus, contentDescription = "Open Camera") }
    }
}
