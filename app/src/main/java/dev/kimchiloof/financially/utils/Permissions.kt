package dev.kimchiloof.financially.utils

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
fun GetPermission(
    permission: String,
    onResultCallback: (Boolean) -> Unit,
    content: @Composable (getPermission: () -> Unit) -> Unit
) {
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        onResultCallback(isGranted)
    }

    content { permissionLauncher.launch(permission) }
}
