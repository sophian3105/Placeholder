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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import compose.icons.FeatherIcons
import compose.icons.feathericons.FolderPlus
import dev.kimchiloof.financially.NewReceiptFlowActivity
import dev.kimchiloof.financially.NewReceiptFlowViewModel
import dev.kimchiloof.financially.navigation.Destination
import dev.kimchiloof.financially.utils.GetPermission
import java.io.InputStream
import java.io.OutputStream

@Composable
fun SelectGalleryButton(navController: NavController, viewModel: NewReceiptFlowViewModel = viewModel()) {
    val context = LocalContext.current

    // Register for activity result to pick an image from the gallery
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri == null) {
            Log.e("SelectGalleryButton", "Failed to get image from gallery")
        } else {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val outputFile = viewModel.getNewPhotoFile(context)

            try {
                val outputStream: OutputStream = outputFile.outputStream()
                inputStream?.copyTo(outputStream)
            } catch (e: Exception) {
                Toast.makeText(context, "Failed to save image to file", Toast.LENGTH_SHORT).show()
                Log.e("SelectGalleryButton", "Failed to save image to file", e)
                inputStream?.close()
                (context as? NewReceiptFlowActivity)?.finish()
            } finally {
                viewModel.selectedImage = outputFile
                inputStream?.close()
                navController.navigate(Destination.Confirmation.route)
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
