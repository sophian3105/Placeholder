package com.example.placeholder.ui.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.placeholder.data.ReceiptDatabase

class GalleryViewModel(private val database: ReceiptDatabase) : ViewModel() {
    fun getAllReceipts() = database.receiptDao().getAllReceipts()
}

class GalleryViewModelFactory(private val database: ReceiptDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GalleryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GalleryViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}