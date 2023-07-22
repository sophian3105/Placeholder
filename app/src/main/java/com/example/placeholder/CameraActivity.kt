package com.example.placeholder

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.placeholder.databinding.ActivityCameraBinding
import com.example.placeholder.ui.camera.CameraPreviewFragment
import com.example.placeholder.ui.camera.CameraViewModel

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    private val cameraViewModel: CameraViewModel by viewModels() // Unnecessary if not using in the activity code. The fragment's ... by activityViewModels() already shares the viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = CameraPreviewFragment()
        supportFragmentManager.beginTransaction()
            .replace(binding.drawerCameraLayout.id, fragment)
            .commit()

    }
}