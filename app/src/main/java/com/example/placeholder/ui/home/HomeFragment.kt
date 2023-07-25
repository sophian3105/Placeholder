package com.example.placeholder.ui.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.placeholder.databinding.FragmentHomeBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import com.example.placeholder.HalfCircleTrackerView
import com.example.placeholder.R


class HomeFragment : Fragment() {
    private lateinit var dialog: AlertDialog

    private fun showCustomDialog() {
        // Inflate the custom dialog layout
        val dialogView = layoutInflater.inflate(R.layout.custom_dialog_layout, null)

        // Create the dialog using the class-level variable
        dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        // Find the views in the custom dialog layout
        val inputEditText: EditText = dialogView.findViewById(R.id.totalAmountSpentTextField)
        val submitButton: Button = dialogView.findViewById(R.id.submitButton)
        val cancelButton: Button = dialogView.findViewById(R.id.cancelButton)

        // Set click listeners for the buttons
        submitButton.setOnClickListener {
            // Handle "Submit" button click
            // USER INPUT SO WE CAN USE
            val userInput = inputEditText.text.toString() // Get the user's input
            dialog.dismiss() // Close the dialog

            // Perform action with the user's input
            // For example: display a toast with the input
            Toast.makeText(requireContext(), "User Input: $userInput", Toast.LENGTH_SHORT).show()
        }

        cancelButton.setOnClickListener {
            // Handle "Cancel" button click
            dialog.dismiss() // Close the dialog
            // Perform action for "Cancel" response (if needed)
        }

        // Show the dialog
        dialog.show()
    }
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // The following replaces the manually placed text box 'android:id="@+id/textHome"' with the text from HomeViewModel.kt
        // If this is removed, then HomeViewModel.kt does nothing
        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        val buttonOpenDialog: Button = binding.buttonOpenDialog
        buttonOpenDialog.setOnClickListener {
            showCustomDialog()
        }
        val halfCircleTrackerView: HalfCircleTrackerView = root.findViewById(R.id.halfCircleTracker)
        //Adjust the fill up of the circle colour
        halfCircleTrackerView.setProgress(0.7f)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}