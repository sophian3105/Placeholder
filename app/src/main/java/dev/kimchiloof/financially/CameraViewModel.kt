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
import androidx.lifecycle.ViewModel
import java.io.File

class CameraViewModel : ViewModel() {
    private fun getPhotoFile(context: Context): File {
        return File(context.externalCacheDir, "${System.currentTimeMillis()}.jpg")
    }

    fun takePhoto(context: Context, imageCapture: ImageCapture?) {
        if (imageCapture == null) {
            Toast.makeText(context, "Camera not ready", Toast.LENGTH_SHORT).show()
            Log.e("CameraScreen", "Got null imageCapture")
            return
        }

        val photoFile = getPhotoFile(context)

        imageCapture.takePicture(
            OutputFileOptions.Builder(photoFile).build(),
            ContextCompat.getMainExecutor(context),
            object : OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: OutputFileResults) {
                    Log.d("CameraScreen", "Image captured: ${photoFile.absolutePath}")
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(context, "Failed to save image", Toast.LENGTH_SHORT).show()
                    Log.e("CameraScreen", "Failed to save image", exception)
                }
            }
        )
    }

    fun flipCamera() {
        TODO("Not yet implemented")
    }
}
