package com.example.placeholder.ui.camera

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.ViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

class CameraViewModel : ViewModel() {
    // Most recently taken photo (i.e. captured using camera in this session)
    var newPhotoName: String = "default_new_camera_capture_name"
    var newPhotoUri: Uri? = null

    /**
     * New photo file
     *
     * @param context is required to get directory
     * @return a File in Environment.DIRECTORY_PICTURES with a name that includes the time
     */
    fun newPhotoFile(context: Context): File {
        newPhotoName = SimpleDateFormat("yy-MM-dd-HH-mm-ss-SSS", Locale.getDefault()).format(System.currentTimeMillis()) + ".png"
        return File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), newPhotoName)
    }
}
