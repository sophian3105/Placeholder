package dev.kimchiloof.financially.ui.pages.camera

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import compose.icons.FeatherIcons
import compose.icons.feathericons.Plus

@Composable
fun CameraButton(
    permission: String = Manifest.permission.CAMERA,
    onResultCallback: (Boolean) -> Unit
) {
    val context = LocalContext.current
    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, permission)
                    == PermissionChecker.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasPermission = isGranted
        onResultCallback(isGranted)
    }

    Button (
        modifier = Modifier.size(56.dp),
        contentPadding = PaddingValues(0.dp),
        shape = CircleShape,
        onClick = { permissionLauncher.launch(permission) }
    ) { Icon(imageVector = FeatherIcons.Plus, contentDescription = "Open Camera") }
}
