package dev.kimchiloof.financially.ui.pages.camera

import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.kimchiloof.financially.CameraViewModel

@Composable
fun CameraPreview(onImageCapturedCallback: (ImageCapture) -> Unit, viewModel: CameraViewModel = viewModel()) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val useBackCamera by viewModel.useBackCamera.observeAsState(true)
    // Avoid re-creating the camera provider and preview on recomposition
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val previewView = remember { PreviewView(context).apply { scaleType = PreviewView.ScaleType.FILL_CENTER } }

    AndroidView(
        factory = { previewView },
        modifier = Modifier.fillMaxHeight()
    ) { view ->
        val cameraProvider = cameraProviderFuture.get()
        val preview = Preview.Builder().build().apply { setSurfaceProvider(view.surfaceProvider) }

        val cameraSelector = if (useBackCamera) CameraSelector.DEFAULT_BACK_CAMERA else CameraSelector.DEFAULT_FRONT_CAMERA
        val imageCapture = ImageCapture.Builder().build()

        try {
            // Unbind previous use and bind new on recomposition
            cameraProvider.unbindAll()

            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageCapture
            )

            // Callback to pass the ImageCapture instance to the caller
            onImageCapturedCallback(imageCapture)
        } catch (e: ImageCaptureException) {
            Toast.makeText(context, "Failed to start camera", Toast.LENGTH_SHORT).show()
            Log.e("CameraPreview", "Failed to start camera", e)
        }
    }
}
