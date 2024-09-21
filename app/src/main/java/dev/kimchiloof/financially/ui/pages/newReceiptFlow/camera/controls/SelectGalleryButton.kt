package dev.kimchiloof.financially.ui.pages.newReceiptFlow.camera.controls

import android.Manifest
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import compose.icons.FeatherIcons
import compose.icons.feathericons.FolderPlus
import dev.kimchiloof.financially.NewReceiptFlowViewModel
import dev.kimchiloof.financially.utils.GetPermission
import java.io.InputStream
import java.io.OutputStream

@Composable
fun SelectGalleryButton(activityViewModel: NewReceiptFlowViewModel, onSuccess: () -> Unit, onFail: () -> Unit) {
    val context = LocalContext.current

    // Register for activity result to pick an image from the gallery
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri == null) {
            Log.e("SelectGalleryButton", "Failed to get image from gallery")
        } else {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val outputFile = activityViewModel.getNewPhotoFile(context)

            if (inputStream == null) {
                Toast.makeText(context, "Failed to open image from gallery", Toast.LENGTH_SHORT).show()
                Log.e("SelectGalleryButton", "Input stream is null")
                onFail()
            } else {
                try {
                    val outputStream: OutputStream = outputFile.outputStream()
                    inputStream.copyTo(outputStream)
                } catch (e: Exception) {
                    Toast.makeText(context, "Failed to save image to file", Toast.LENGTH_SHORT).show()
                    Log.e("SelectGalleryButton", "Failed to save image to file", e)
                    inputStream.close()
                    onFail()
                }
                activityViewModel.receiptImage = outputFile
                inputStream.close()
                onSuccess()
            }
        }
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
