package dev.kimchiloof.financially.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import dev.kimchiloof.financially.utils.toTitleCase

sealed class Destination(val route: String) {
    open class DestinationWithDisplay(
        route: String,
        val icon: ImageVector? = null,
        val title: String? = route.toTitleCase()
    ) : Destination(route)

    object Home : DestinationWithDisplay("home",  Icons.Default.Home)
    object Gallery : DestinationWithDisplay("gallery", Icons.Default.Favorite)
    object Finances : DestinationWithDisplay("finances", Icons.Default.ShoppingCart)

    object Camera : Destination("new_receipt/camera")
    object Confirmation : Destination("new_receipt/confirmation")
}
