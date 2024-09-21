package dev.kimchiloof.financially.utils

import android.app.Activity
import android.content.Context
import androidx.navigation.NavController

fun NavController.resetToHome(route: String) {
    this.navigate(route) {
        popUpTo(route) { inclusive = true }
    }
}

fun NavController.endActivity(context: Context) {
    (context as? Activity)?.finish()
}
