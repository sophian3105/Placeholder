package dev.kimchiloof.financially.navigation

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

enum class Destination(
    val route: String,
    @StringRes private val title: Int,
    private val icon: ImageVector
) {
    Finances("finances", R.string.navDestinationTitleFinances, Icons.Default.ShoppingCart),
    Gallery("gallery", R.string.navDestinationTitleGallery, Icons.Default.Favorite),
    Home("home", R.string.navDestinationTitleHome, Icons.Default.Home);

    fun getTitle(context: Context): String {
        return context.getString(title)
    }

    @Composable
    fun GetIcon(context: Context) {
        Icon(imageVector = icon, contentDescription = getTitle(context))
    }
}