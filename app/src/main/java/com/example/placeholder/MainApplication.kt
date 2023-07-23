package com.example.placeholder

import android.app.Application
import android.os.Environment
import android.util.Log
import java.io.File

class MainApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        createNomediaIfNotExists()
    }

    /**
     * Create nomedia if not exists
     *
     * Ensure that Environment.DIRECTORY_PICTURES is not accessible by the public gallery
     */
    private fun createNomediaIfNotExists() {
        val externalStorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val nomediaFile = File(externalStorageDir, ".nomedia")

        if (externalStorageDir != null) {
            if (nomediaFile.exists()) Log.i("MainApplicationMKDIR", ".nomedia file exists")
            else if (!nomediaFile.createNewFile()) Log.e("MainApplicationMKDIR", "Error creating .nomedia file")
        } else Log.e("MainApplicationMKDIR", "Error accessing Environment.DIRECTORY_PICTURES")
    }
}