package dev.kimchiloof.financially

import android.app.Application
import dev.kimchiloof.financially.data.receipt.ReceiptDatabase

class FinanciallyApplication : Application() {
    val database by lazy { ReceiptDatabase.getDatabase(this) }
}
