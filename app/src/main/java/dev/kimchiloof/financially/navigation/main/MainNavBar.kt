package dev.kimchiloof.financially.navigation.main

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.kimchiloof.financially.navigation.Destination

@Composable
fun MainNavBar(navController: NavController) {
    val context = LocalContext.current

    val navBarItems = listOf(
        Destination.Gallery,
        Destination.Home,
        Destination.Finances,
    )

    NavigationBar (
        modifier = Modifier.fillMaxWidth()
    ) {
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = currentBackStackEntry?.destination?.route
        if (currentRoute == null) Log.w("MainNavBar", "currentRoute is null")

        navBarItems.forEach { destination ->
            NavigationBarItem(
                icon = { destination.icon?.let { Icon(it, destination.title) } },
                label = { Text(destination.title ?: "") },
                selected = (currentRoute == destination.route),
                onClick = {
                    if (currentRoute != destination.route) {
                        navController.navigate(destination.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}
