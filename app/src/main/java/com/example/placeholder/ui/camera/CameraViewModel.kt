package com.example.placeholder.ui.camera

import android.net.Uri
import androidx.lifecycle.ViewModel

class CameraViewModel : ViewModel() {
    // Most recently taken photo (i.e. captured using camera in this session)
    var newPhotoName: String = "default_new_camera_capture_name"
    var newPhotoUri: Uri? = null
}
