package com.example.placeholder.ui.camera

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
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
import com.example.placeholder.CameraActivity
import com.example.placeholder.databinding.FragmentCameraPreviewBinding
import java.io.InputStream
import java.io.OutputStream
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraPreviewFragment : Fragment() {
    private var _binding: FragmentCameraPreviewBinding? = null
    private val binding get() = _binding ?: error("Trying to access binding when it's null.")
    private val cameraViewModel: CameraViewModel by activityViewModels()
    private lateinit var activity: CameraActivity

    // CameraX variables
    private lateinit var cameraExecutor: ExecutorService
    private var imageCapture: ImageCapture? = null
    private lateinit var galleryLauncher: ActivityResultLauncher<String?>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CameraActivity) {
            activity = context
        }
    }

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
                cameraViewModel.newReceipt.receiptImage = cameraViewModel.createReceiptImageFile()

                try {
                    val outputStream: OutputStream = cameraViewModel.newReceipt.receiptImage.outputStream()
                    inputStream?.use { input -> outputStream.use { output ->
                        input.copyTo(output)
                    } }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    openImageConfirmFragment()
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
     * Request permissions for camera
     *
     * To request permissions for the camera and if received, open camera
     */
    private fun requestPermissionsForCamera() {
        requestPermissionsIfMissing { granted ->
            if (granted) startCamera()
            else Toast.makeText(requireContext(), "Please allow the necessary permissions!", Toast.LENGTH_LONG).show()
        }
    }


    /**
     * Request permissions if missing
     *
     * @param onResult returns boolean true if permissions given, otherwise attempt to get permissions
     * @receiver requestPermissionsForCamera()
     */
    private fun requestPermissionsIfMissing(onResult: ((Boolean) -> Unit)) {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) onResult(true)
        else registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            onResult(it)
        }.launch(Manifest.permission.CAMERA)
    }

    /**
     * Start camera
     *
     * Opens and binds DEFAULT_BACK_CAMERA to the fragment
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
     * Capture image
     *
     * To store images to storage
     * Once image taken, open ImageConfirmFragment to confirm the taken image
     */
    private fun captureImage() {
        val imageCapture = imageCapture?: return
        cameraViewModel.newReceipt.receiptImage = cameraViewModel.createReceiptImageFile()
        val outputOption = ImageCapture.OutputFileOptions.Builder(cameraViewModel.newReceipt.receiptImage).build()

        imageCapture.takePicture(
            outputOption, ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback{
                /**
                 * On image saved
                 *
                 * Send the Uri of the image to the cameraViewModel
                 * Then open the ImageConfirmFragment to confirm the taken image
                 *
                 * @param outputFileResults is the resulting File containing the captured image
                 */
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    openImageConfirmFragment()
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("cameraX", "errors: ${exception.message}", exception)
                }
            }
        )
    }

    /**
     * Open image confirm fragment
     *
     */
    private fun openImageConfirmFragment() {
        val fragment = ImageConfirmFragment()
        activity.replaceFragment(fragment, true)
    }

    /**
     * On destroy
     *
     * Ensure to clean up system resources from cameraExecutor
     */
    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
        _binding = null
    }
}