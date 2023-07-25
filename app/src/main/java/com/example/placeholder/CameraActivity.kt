package com.example.placeholder

import android.os.Bundle
import android.os.Environment
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.placeholder.databinding.ActivityCameraBinding
import com.example.placeholder.ui.camera.CameraPreviewFragment
import com.example.placeholder.ui.camera.CameraViewModel
import com.example.placeholder.ui.camera.CameraViewModelFactory

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    private val cameraViewModel: CameraViewModel by viewModels {
        CameraViewModelFactory((application as MainApplication).database)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // On camera activity creation, set the directory to store and access new receipts
        cameraViewModel.photosDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        val fragment = CameraPreviewFragment()
        supportFragmentManager.beginTransaction()
            .replace(binding.drawerCameraLayout.id, fragment)
            .commit()

    }
}