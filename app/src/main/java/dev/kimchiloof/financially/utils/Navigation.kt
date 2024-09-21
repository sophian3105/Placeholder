package dev.kimchiloof.financially.utils

import android.app.Activity
import android.content.Context
import androidx.navigation.NavController
import dev.kimchiloof.financially.navigation.Destination

fun NavController.resetTo(destination: Destination) {
    this.navigate(destination.route) {
        popUpTo(destination.route) { inclusive = true }
    }
}

fun NavController.endActivity(context: Context) {
    (context as? Activity)?.finish()
}
