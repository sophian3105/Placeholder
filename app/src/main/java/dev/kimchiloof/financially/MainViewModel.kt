package dev.kimchiloof.financially

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dev.kimchiloof.financially.data.receipt.ReceiptDatabase

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = ReceiptDatabase.getDatabase(application)
    private val dao = database.receiptDao()

    fun getAllReceipts() = dao.getAllReceipts()
}
