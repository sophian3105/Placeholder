package com.example.placeholder.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.placeholder.MainApplication
import com.example.placeholder.databinding.FragmentGalleryBinding
import kotlinx.coroutines.launch

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private val galleryViewModel: GalleryViewModel by viewModels {
        GalleryViewModelFactory((requireActivity().application as MainApplication).database)
    }

    private lateinit var receiptRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root



        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val galleryAdapter = GalleryAdapter()
        receiptRecyclerView = binding.receiptRecyclerView
        receiptRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        receiptRecyclerView.adapter = galleryAdapter

        lifecycle.coroutineScope.launch {
            galleryViewModel.getAllReceipts().collect { receipts ->
                galleryAdapter.submitList(receipts)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}