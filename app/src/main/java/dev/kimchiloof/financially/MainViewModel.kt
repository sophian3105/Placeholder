package dev.kimchiloof.financially

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import dev.kimchiloof.financially.data.receipt.Receipt
import dev.kimchiloof.financially.data.receipt.ReceiptDatabase
import java.time.LocalDate

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = ReceiptDatabase.getDatabase(application)
    private val dao = database.receiptDao()
    private val sharedPreferences = application.getSharedPreferences("financially_prefs", Context.MODE_PRIVATE)

    val spendingGoal = MutableLiveData(sharedPreferences.getString("spending_goal", "") ?: "")
    val amountSpent = MutableLiveData(sharedPreferences.getFloat("amount_spent", 0.0f).toDouble())

    suspend fun deleteReceipt(receipt: Receipt) { dao.delete(receipt) }
    suspend fun insertReceipt(receipt: Receipt) { dao.insert(receipt) }
    suspend fun updateReceipt(receipt: Receipt) { dao.update(receipt) }
    fun getAllReceipts() = dao.getAllReceipts()
    fun getReceipt(id: Int) = dao.getReceipt(id)
    fun getFilteredReceipts(
        name: String?,
        start: LocalDate?,
        end: LocalDate?,
        min: Double?,
        max: Double?,
        category: String?
    ) = dao.getFilteredReceipts(name, start, end, min, max, category)

    fun saveSpendingGoal(goal: String) {
        sharedPreferences.edit().putString("spending_goal", goal).apply()
        spendingGoal.value = goal
    }

    fun saveAmountSpent(amount: Double) {
        sharedPreferences.edit().putFloat("amount_spent", amount.toFloat()).apply()
        amountSpent.value = amount
    }
}
