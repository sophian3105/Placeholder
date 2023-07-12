package com.example.placeholder.ui.camera


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.placeholder.databinding.FragmentCameraPreviewBinding

class CameraPreviewFragment : Fragment() {
    private var _binding: FragmentCameraPreviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val cameraPreviewViewModel =
            ViewModelProvider(this)[CameraPreviewViewModel::class.java]
        _binding = FragmentCameraPreviewBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }








}