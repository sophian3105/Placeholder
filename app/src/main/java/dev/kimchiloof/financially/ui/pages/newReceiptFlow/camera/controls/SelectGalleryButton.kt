package dev.kimchiloof.financially.ui.pages.newReceiptFlow.camera.controls

import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import compose.icons.FeatherIcons
import compose.icons.feathericons.FolderPlus
import dev.kimchiloof.financially.utils.GetPermission
import java.io.InputStream

@Composable
fun SelectGalleryButton() {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    // Register for activity result to pick an image from the gallery
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        uri?.let {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }
    }

    bitmap?.let {
        Image(bitmap = it.asImageBitmap(), contentDescription = "Selected image", modifier = Modifier.size(300.dp))
    }

    GetPermission(
        permission = Manifest.permission.READ_EXTERNAL_STORAGE,
        onResultCallback = { galleryLauncher.launch("image/*") }
    ) { getPermission ->
        IconButton (
            onClick = { getPermission() },
        ) { Icon(FeatherIcons.FolderPlus, "Open gallery") }
    }

}
