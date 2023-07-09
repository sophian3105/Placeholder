package com.example.placeholder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.placeholder.databinding.ActivityCameraBinding
import com.example.placeholder.ui.camera.CameraPreviewFragment

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Open the new CameraPreviewFragment (replace the activity screen with a fragment)
        val fragment = CameraPreviewFragment()
        supportFragmentManager.beginTransaction()
            .replace(binding.drawerCameraLayout.id, fragment)
            //.addToBackStack(null) <- Uncomment if want an extra 'back' step for back button
            .commit()
    }
}