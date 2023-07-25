package com.example.placeholder

import android.os.Bundle
import android.os.Environment
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.placeholder.databinding.ActivityCameraBinding
import com.example.placeholder.ui.camera.CameraPreviewFragment
import com.example.placeholder.ui.camera.CameraViewModel
import com.example.placeholder.ui.camera.CameraViewModelFactory

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    private val cameraViewModel: CameraViewModel by viewModels {
        CameraViewModelFactory((application as MainApplication).database)
    }

    // Store the currently opened fragment tag
    private var currentFragmentTag: String? = null
    companion object {
        private const val KEY_CURRENT_FRAGMENT_TAG = "current_fragment_tag"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // On camera activity creation, set the directory to store and access new receipts
        cameraViewModel.photosDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        // Load the correct fragment on activity creation
        if (savedInstanceState == null) {
            val fragment = CameraPreviewFragment()
            replaceFragment(fragment, false)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)

        currentFragmentTag = savedInstanceState?.getString(KEY_CURRENT_FRAGMENT_TAG)
        currentFragmentTag?.let { tag ->
            val currentFragment = supportFragmentManager.findFragmentByTag(tag)
            if (currentFragment != null) replaceFragment(currentFragment, false)
            else replaceFragment(CameraPreviewFragment(), false) // default fragment fallback
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Save the tag of the currently displayed fragment in the ViewModel
        outState.putString(KEY_CURRENT_FRAGMENT_TAG, currentFragmentTag)
    }


    /**
     * Replace fragment
     *
     * @param newFragment the fragment to override the current fragment
     * @param addToBackStack whether to add the new fragment to the back stack
     */
    fun replaceFragment(newFragment: Fragment, addToBackStack: Boolean) {
        val fragmentTag = newFragment.javaClass.simpleName.replace(Regex("(?!^)([A-Z])"), "_$1").lowercase()
        currentFragmentTag = fragmentTag

        supportFragmentManager.beginTransaction().apply {
            replace(binding.drawerCameraLayout.id, newFragment, fragmentTag)
            if (addToBackStack) addToBackStack(null)
            commit()
        }
    }
}

