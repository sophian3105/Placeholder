package com.example.placeholder

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.placeholder.databinding.ActivityCameraBinding
import com.example.placeholder.ui.camera.CameraPreviewFragment
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding

    // CameraX Variables
    private  lateinit var cameraExecutor: ExecutorService
    private lateinit var outputDirectory: File
    private var imageCapture: ImageCapture? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // CameraX Operations
        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()
        requestPermissions()

        // On click, take and store a picture
        binding.captureButton.setOnClickListener{
            captureImage()
        }
        binding.openGalleryButton.setOnClickListener{

        }

        // Open the new CameraPreviewFragment (replace the activity screen with a fragment)
//        val fragment = CameraPreviewFragment()
//        supportFragmentManager.beginTransaction()
//            .replace(binding.drawerCameraLayout.id, fragment)
//            //.addToBackStack(null) <- Uncomment if want an extra 'back' step for back button
//            .commit()
    }

    /**
     * To request permissions for the camera
     * If received, open camera
     */
    private fun requestPermissions() {
        requestPermissionsIfMissing { granted ->
            if (granted)
                startCamera()
            else
                Toast.makeText(this, "Please allow the necessary permissions!", Toast.LENGTH_LONG).show()
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
     * Get the intended output directory to store images
     */
    private fun getOutputDirectory(): File{
        val mediaDir = externalMediaDirs.firstOrNull()?.let{mFile ->
            File(mFile, resources.getString(R.string.app_name)).apply {
                mkdirs()
            }
        }

        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }

    /**
     * To store images to storage
     */
    private fun captureImage() {
        val imageCapture = imageCapture?: return
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat("yy-MM-dd-HH-mm-ss-SSS", Locale.getDefault()).format(System.currentTimeMillis()) + ".jpg"
        )
        val outputOption = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        
        imageCapture.takePicture(
            outputOption, ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback{
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo saved"

                    Toast.makeText(this@CameraActivity, "$msg $savedUri", Toast.LENGTH_LONG).show()
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("cameraX", "onError: ${exception.message}", exception)                }
            }
        )
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