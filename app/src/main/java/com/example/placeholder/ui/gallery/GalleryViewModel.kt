package com.example.placeholder.ui.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.placeholder.data.Receipt
import com.example.placeholder.data.ReceiptDatabase
import kotlinx.coroutines.launch

class GalleryViewModel(private val database: ReceiptDatabase) : ViewModel() {
    fun getAllReceipts() = database.receiptDao().getAllReceipts()
    fun deleteReceipt(receipt: Receipt) = viewModelScope.launch {
        database.receiptDao().deleteReceipt(receipt)
    }
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