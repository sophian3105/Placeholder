package com.example.placeholder

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.placeholder.databinding.ActivityCameraBinding
import com.example.placeholder.ui.camera.CameraPreviewFragment
import java.lang.Exception
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding

    // CameraX Variables
    private  lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // CameraX Operations
        cameraExecutor = Executors.newSingleThreadExecutor()
        requestPermissions()

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

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    previewUseCase
                )
            } catch (e: Exception) {
                Log.d("ERROR", e.message.toString())
            }
        }, ContextCompat.getMainExecutor(this))
    }

    // Helper function to build new CameraX previewView
    private fun buildCameraPreviewUseCase(): Preview {
        return Preview.Builder().build().also {it.setSurfaceProvider(binding.cameraPreviewView.surfaceProvider) }
    }
}