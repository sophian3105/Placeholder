package dev.kimchiloof.financially.navigation.main

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import dev.kimchiloof.financially.R

enum class MainDestination(
    val visible: Boolean,               // Whether to display in navigation bar
    val index: Int,                     // Order to display in navigation bar
    val route: String,                  // Unique route
    @StringRes private val title: Int?,  // Title resource
    private val icon: ImageVector?       // Icon to display
) {
    Gallery(
        true, -1, "gallery",
        R.string.navDestinationTitleGallery, Icons.Default.Favorite
    ),
    Home(
        true, 0, "home",
        R.string.navDestinationTitleHome, Icons.Default.Home
    ),
    Finances(
        true,1, "finances",
        R.string.navDestinationTitleFinances, Icons.Default.ShoppingCart
    );

    fun getTitle(context: Context): String {
        return title?.let { context.getString(it) } ?: ""
    }

    @Composable
    fun GetIcon(context: Context) {
        if (icon != null) {
            Icon(imageVector = icon, contentDescription = getTitle(context))
        }
    }
}
