package com.example.placeholder.ui.camera


import android.os.Bundle
import android.util.Log
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

        //binding.amountSpentTextField <- the amount spent on the receipt. User editable and TODO autofill by OCR
        //binding.categoryDropdownSpinner <- the category selection of the receipt and TODO allow user to add new categories

        // Delete the photo to be retaken (on retake the name will be overwritten)
        binding.retakeImageButton.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
            cameraViewModel.deletePhotoFile(cameraViewModel.newReceipt.receiptImage)
        }
        // Attempt to get user input, if valid, save the receipt and finish the activity
        binding.confirmImageButton.setOnClickListener {
            val userInputAmount: String = binding.totalAmountSpentTextField.text.toString()
            val userInputCategory: String = binding.receiptCategoryDropdownSpinner.selectedItem.toString()

            if (userInputAmount.isNotEmpty()) {
                cameraViewModel.newReceipt.receiptAmount = userInputAmount.toDouble()
                cameraViewModel.newReceipt.receiptCategory = userInputCategory

                cameraViewModel.insertReceipt(cameraViewModel.newReceipt)

                Log.i("DATABASE db", "Inserted ${cameraViewModel.newReceipt.receiptName}: \$${cameraViewModel.newReceipt.receiptAmount}")
                cameraViewModel.tempFile.delete()
                requireActivity().finish()
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
            .load(cameraViewModel.newReceipt.receiptImage)
            .into(binding.prevCapturedImage)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}