package com.example.placeholder.ui.camera


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.placeholder.databinding.FragmentImageConfirmBinding


class ImageConfirmFragment : Fragment() {
    private var _binding: FragmentImageConfirmBinding? = null
    private val binding get() = _binding!!
    private val cameraViewModel: CameraViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageConfirmBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //binding.amountSpentTextField
        //binding.categoryDropdownSpinner
        binding.retakeImageButton.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
            cameraViewModel.deletePhotoFile(cameraViewModel.newPhotoName)
        }
        binding.confirmImageButton.setOnClickListener {
            // TODO use custom data object
            val userInputAmount: String = binding.totalAmountSpentTextField.text.toString()
            if (userInputAmount.isNotEmpty()) {
                cameraViewModel.amountSpent = userInputAmount.toDouble()
                // Deal with category selection (optional entry)
                //...

                // TODO proceed
            } else {
                Toast.makeText(requireContext(), "Please enter the amount present on receipt!", Toast.LENGTH_LONG).show()
            }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // To display recently captured image from camera or gallery
        Glide.with(requireContext())
            .load(cameraViewModel.newPhotoFile)
            .into(binding.prevCapturedImage)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}