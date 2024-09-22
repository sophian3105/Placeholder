package dev.kimchiloof.financially

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.OnImageSavedCallback
import androidx.camera.core.ImageCapture.OutputFileOptions
import androidx.camera.core.ImageCapture.OutputFileResults
import androidx.camera.core.ImageCaptureException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import dev.kimchiloof.financially.data.receipt.Receipt
import dev.kimchiloof.financially.data.receipt.ReceiptDatabase
import java.io.File

class NewReceiptFlowViewModel(application: Application) : AndroidViewModel(application) {
    // New receipt flow
    var receiptImage by mutableStateOf<File?>(null)
    var receiptName by mutableStateOf("")
    var receiptAmount by mutableDoubleStateOf(0.0)
    var receiptDate by mutableLongStateOf(System.currentTimeMillis())
    var receiptCategory by mutableStateOf("")

    // ================

    private val database = ReceiptDatabase.getDatabase(application)
    private val dao = database.receiptDao()

    suspend fun insertReceipt(receipt: Receipt) = dao.insert(receipt)

    // Camera selector
    val useBackCamera = MutableLiveData(true)

    fun flipCamera() {
        useBackCamera.value = !(useBackCamera.value ?: true)
    }

    // Capture images
    fun getNewPhotoFile(context: Context): File {
        return File(context.externalCacheDir, "${System.currentTimeMillis()}.jpg")
    }

    fun takePhoto(context: Context, imageCapture: ImageCapture?, onSuccess: () -> Unit, onFail: () -> Unit) {
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
                    receiptImage = photoFile
                    Log.e("CameraScreen", "Selected image: ${receiptImage?.absolutePath}")
                    onSuccess()
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(context, "Failed to save image", Toast.LENGTH_SHORT).show()
                    Log.e("CameraScreen", "Failed to save image", exception)
                    onFail()
                }
            }
        )
    }

    fun deleteImage(image: File? = receiptImage) {
        if (image == null) {
            Log.w("NewReceiptFlowViewModel", "Did not delete null image")
            return
        }

        try {
            image.delete()
            if (image.absolutePath == receiptImage?.absolutePath) {
                receiptImage = null
            }
            Log.i("NewReceiptFlowViewModel", "Deleted image: ${image.absolutePath}")
        } catch (e: Exception) {
            Log.e("NewReceiptFlowViewModel", "Failed to delete image", e)
        }
    }
}
