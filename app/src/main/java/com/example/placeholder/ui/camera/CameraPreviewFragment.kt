package com.example.placeholder.ui.camera

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.placeholder.databinding.FragmentCameraPreviewBinding
import java.io.InputStream
import java.io.OutputStream
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class CameraPreviewFragment : Fragment() {
    private var _binding: FragmentCameraPreviewBinding? = null
    private val binding get() = _binding ?: error("Trying to access binding when it's null.")
    private val cameraViewModel: CameraViewModel by activityViewModels()

    // CameraX variables
    private lateinit var cameraExecutor: ExecutorService
    private var imageCapture: ImageCapture? = null
    private lateinit var galleryLauncher: ActivityResultLauncher<String?>

    /**
     * Called before the UI is loaded
     * Non-UI related operations and declarations here
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // CameraX Operations
        cameraExecutor = Executors.newSingleThreadExecutor()

    }

    /**
     * Called once UI loaded
     * UI-related (including context) declarations and functions here
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraPreviewBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // CameraX Operations
        requestPermissionsForCamera()
        galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri ->
            if (imageUri != null) {
                //Make a copy of the selected image to send to ImageConfirmFragment
                val inputStream: InputStream? = requireContext().contentResolver.openInputStream(imageUri)
                val photoFileCopy = cameraViewModel.newPhotoFile(requireContext())

                try {
                    val outputStream: OutputStream = photoFileCopy.outputStream()
                    inputStream?.use { input -> outputStream.use { output ->
                        input.copyTo(output)
                    } }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    openImageConfirmFragment(Uri.fromFile(photoFileCopy))
                }
            } else Log.e("galleryLauncherImageUri", "imageUri was null")
        }

        // On click, take and store an image
        binding.captureButton.setOnClickListener{
            captureImage()
        }
        // On click, open the gallery to select image
        binding.openGalleryButton.setOnClickListener{
            galleryLauncher.launch("image/*")
        }
        // On click, exit and terminate the camera activity
        binding.returnButton.setOnClickListener{
            requireActivity().finish()
        }

        return root
    }

    /**
     * To request permissions for the camera
     * If received, open camera
     */
    private fun requestPermissionsForCamera() {
        requestPermissionsIfMissing { granted ->
            if (granted) startCamera()
            else Toast.makeText(requireContext(), "Please allow the necessary permissions!", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Actually requests the necessary permissions from user
     */
    private fun requestPermissionsIfMissing(onResult: ((Boolean) -> Unit)) {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) onResult(true)
        else registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            onResult(it)
        }.launch(Manifest.permission.CAMERA)
    }

    /**
     * Starts new camera
     */
    private fun startCamera(){
        val processCameraProvider = ProcessCameraProvider.getInstance(requireContext())
        processCameraProvider.addListener({
            try {
                val cameraProvider = processCameraProvider.get()
                val previewUseCase: Preview = Preview.Builder().build().apply { setSurfaceProvider(binding.cameraPreviewView.surfaceProvider) }
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

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    /**
     * To store images to storage
     * Once image taken, open ImageConfirmFragment to confirm the taken image
     */
    private fun captureImage() {
        val imageCapture = imageCapture?: return
        val outputOption = ImageCapture.OutputFileOptions.Builder(cameraViewModel.newPhotoFile(requireContext())).build()

        imageCapture.takePicture(
            outputOption, ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback{
                /**
                 * On image captured and saved, send the Uri location of the image to the viewModel
                 * Then open the ImageConfirmFragment to confirm the taken image
                 */
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = outputFileResults.savedUri ?: return
                    openImageConfirmFragment(savedUri)
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("cameraX", "errors: ${exception.message}", exception)
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
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(binding.drawerFragmentCameraLayout.id, fragment)
            .addToBackStack(null)
            .commit()
    }

    /**
     * Destroy unused resources when closed
     */
    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
        _binding = null
    }
}