package com.example.placeholder.ui.camera

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.placeholder.data.Receipt
import com.example.placeholder.data.ReceiptDatabase
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

class CameraViewModel(private val database: ReceiptDatabase): ViewModel() {

    /**
     * Variables for receipt image function
     */
    var photosDirectory: File? = null // Init on CameraActivity load
    var tempFile: File = File.createTempFile("temp", null)
    var newReceipt: Receipt = Receipt("", tempFile, 0.00, "")

    /**
     * Create photo file
     *
     * Set newReceipt.receiptName as the current date and time
     * @return a newReceipt.receiptName file at Environment.DIRECTORY_PICTURES
     */
    fun createReceiptImageFile(): File {
        newReceipt.receiptName = SimpleDateFormat("yy-MM-dd-HH-mm-ss-SSS", Locale.getDefault()).format(System.currentTimeMillis()) + ".png"
        return File(photosDirectory, newReceipt.receiptName)
    }

    /**
     * Delete photo file
     *
     * @param photoFile to be deleted
     */
    fun deletePhotoFile(photoFile: File) {
        if (photoFile.delete()) Log.i("FUNC deletePhotoFile", "File deleted successfully")
        else Log.e("FUNC deletePhotoFile", "File does not exist or failed to delete")
    }


    /**
     * Insert receipt
     *
     * Only inserting receipts is needed in CameraActivity. Updating, querying, and deleting occur in another activity
     * @param receipt to be inserted
     */
    fun insertReceipt(receipt: Receipt) = viewModelScope.launch {
        database.receiptDao().insertReceipt(receipt)
    }
}

class CameraViewModelFactory(private val receiptDatabase: ReceiptDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CameraViewModel::class.java)) {
            return CameraViewModel(receiptDatabase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}