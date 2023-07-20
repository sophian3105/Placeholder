package com.example.placeholder

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.placeholder.databinding.ActivityCameraBinding
import com.example.placeholder.ui.camera.ImageConfirmFragment
import com.example.placeholder.ui.camera.CameraViewModel
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    private val cameraViewModel: CameraViewModel by viewModels()

    // CameraX variables
    private  lateinit var cameraExecutor: ExecutorService
    private lateinit var outputDirectory: File
    private var imageCapture: ImageCapture? = null
    // Gallery launcher
    private lateinit var galleryLauncher: ActivityResultLauncher<String?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // CameraX Operations
        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()
        // Gallery Operations
        galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri ->
            if (imageUri != null) {
                // TODO - add ability to save selected image to app's own files
                openImageConfirmFragment(imageUri)
            }
        }

        // Start camera with necessary permissions
        requestPermissionsForCamera()

        // On click, take and store a picture
        binding.captureButton.setOnClickListener{
            captureImage()
        }
        binding.openGalleryButton.setOnClickListener{
            galleryLauncher.launch("image/*")
        }


    }

    /**
     * To request permissions for the camera
     * If received, open camera
     */
    private fun requestPermissionsForCamera() {
        requestPermissionsIfMissing { granted ->
            if (granted) startCamera()
            else Toast.makeText(this, "Please allow the necessary permissions!", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Actually requests the necessary permissions from user
     */
    private fun requestPermissionsIfMissing(onResult: ((Boolean) -> Unit)) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) onResult(true)
        else registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            onResult(it)
        }.launch(Manifest.permission.CAMERA)
    }

    /**
     * Get the intended output directory to store images
     */
    private fun getOutputDirectory(): File{
        val mediaDir = externalMediaDirs.firstOrNull()?.let{mFile ->
            File(mFile, resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }

    /**
     * Starts new camera
     */
    private fun startCamera(){
        val processCameraProvider = ProcessCameraProvider.getInstance(this)
        processCameraProvider.addListener({
            try {
                val cameraProvider = processCameraProvider.get()

                val previewUseCase = buildCameraPreviewUseCase()
                imageCapture = ImageCapture.Builder().build()

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    previewUseCase,
                    imageCapture
                )
            } catch (e: Exception) {
                Log.d("ERROR", e.message.toString())
            }

        }, ContextCompat.getMainExecutor(this))
    }

    /**
     * To store images to storage
     * Once image taken, open ImageConfirmFragment to confirm the taken image
     */
    private fun captureImage() {
        val imageCapture = imageCapture?: return

        cameraViewModel.newPhotoName = SimpleDateFormat("yy-MM-dd-HH-mm-ss-SSS", Locale.getDefault()).format(System.currentTimeMillis()) + ".png"
        val photoFile = File(outputDirectory, cameraViewModel.newPhotoName)

        val outputOption = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        
        imageCapture.takePicture(
            outputOption, ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback{
                /**
                 * On image captured and saved, send the Uri location of the image to the viewModel
                 * Then open the ImageConfirmFragment to confirm the taken image
                 */
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    openImageConfirmFragment(Uri.fromFile(photoFile))
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("cameraX", "onError: ${exception.message}", exception)
                }
            }
        )
    }

    /**
     * Accepts a Uri to send to the ImageConfirmFragment
     * Moves to the image confirm fragment to confirm with user the image taken
     */
    private fun openImageConfirmFragment(imageUri: Uri) {
        cameraViewModel.newPhotoUri = imageUri

        val fragment = ImageConfirmFragment()
        supportFragmentManager.beginTransaction()
            .replace(binding.drawerCameraLayout.id, fragment)
            .addToBackStack(null)
            .commit()
    }

    /**
     * Helper function to build new CameraX previewView
     */
    private fun buildCameraPreviewUseCase(): Preview {
        return Preview.Builder().build().also {it.setSurfaceProvider(binding.cameraPreviewView.surfaceProvider) }
    }

    /**
     * Destroy unused resources when closed
     */
    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}