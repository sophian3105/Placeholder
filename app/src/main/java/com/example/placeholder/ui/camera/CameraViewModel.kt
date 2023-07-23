package com.example.placeholder.ui.camera

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

class CameraViewModel : ViewModel() {

    /**
     * Variables for receipt image function
     */
    var newPhotoName: String = "default_new_camera_capture_name"
    var newPhotoUri: Uri? = null
    var photosDirectory: File? = null

    /**
     * New photo file
     *
     * @return a File in Environment.DIRECTORY_PICTURES with a name that includes the time
     */
    fun newPhotoFile(): File {
        newPhotoName = SimpleDateFormat("yy-MM-dd-HH-mm-ss-SSS", Locale.getDefault()).format(System.currentTimeMillis()) + ".png"
        return File(photosDirectory, newPhotoName)
    }

    /**
     * Delete photo file
     *
     * @param photoName name of the photo in Environment.DIRECTORY_PICTURES to be deleted
     */
    fun deletePhotoFile(photoName: String) {
        val photoToDelete = File(photosDirectory, photoName)

        if (photoToDelete.delete()) Log.i("FUN deletePhotoFile", "$photoName deleted successfully")
        else Log.e("FUN deletePhotoFile", "File does not exist or failed to delete")
    }


    /**
     * Variables for other receipt functions
     */
    var amountSpent: Double = 0.00
    var receiptCategory: String = "default_receipt_category"
}
