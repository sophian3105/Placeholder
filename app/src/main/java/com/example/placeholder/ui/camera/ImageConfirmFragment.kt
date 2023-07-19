package com.example.placeholder.ui.camera


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.placeholder.databinding.FragmentImageConfirmBinding

class ImageConfirmFragment : Fragment() {
    private var _binding: FragmentImageConfirmBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val imageConfirmViewModel =
            ViewModelProvider(this)[ImageConfirmViewModel::class.java]
        _binding = FragmentImageConfirmBinding.inflate(inflater, container, false)
        val root: View = binding.root



        return root
    }








}