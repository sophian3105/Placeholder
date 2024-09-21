package dev.kimchiloof.financially

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.OnImageSavedCallback
import androidx.camera.core.ImageCapture.OutputFileOptions
import androidx.camera.core.ImageCapture.OutputFileResults
import androidx.camera.core.ImageCaptureException
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import dev.kimchiloof.financially.navigation.newReceipt.NewReceiptDestination
import java.io.File

class NewReceiptFlowViewModel : ViewModel() {
    // Selected or created image
    var selectedImage = MutableLiveData<File?>()

    // Camera selector
    val useBackCamera = MutableLiveData(true)

    fun flipCamera() {
        useBackCamera.value = !(useBackCamera.value ?: true)
    }

    // Capture images
    fun getNewPhotoFile(context: Context): File {
        return File(context.externalCacheDir, "${System.currentTimeMillis()}.jpg")
    }

    fun takePhoto(context: Context, imageCapture: ImageCapture?, navController: NavController) {
        if (imageCapture == null) {
            Toast.makeText(context, "Camera not ready", Toast.LENGTH_SHORT).show()
            Log.e("CameraScreen", "Got null imageCapture")
            return
        }

        val photoFile = getNewPhotoFile(context)


        imageCapture.takePicture(
            OutputFileOptions.Builder(photoFile).build(),
            ContextCompat.getMainExecutor(context),
            object : OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: OutputFileResults) {
                    Log.d("CameraScreen", "Image captured: ${photoFile.absolutePath}")
                    selectedImage.value = photoFile
                    navController.navigate(NewReceiptDestination.NewReceiptConfirmation.route)
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(context, "Failed to save image", Toast.LENGTH_SHORT).show()
                    Log.e("CameraScreen", "Failed to save image", exception)
                    (context as? NewReceiptFlowActivity)?.finish()
                }
            }
        )
    }
}
