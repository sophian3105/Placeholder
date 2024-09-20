package dev.kimchiloof.financially.navigation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun MainNavBar(navController: NavController) {
    val context = LocalContext.current

    NavigationBar (
        modifier = Modifier.fillMaxWidth()
    ) {
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = currentBackStackEntry?.destination?.route
            ?: Destination.Home.route.also {
                Log.w("MainNavBar", "currentRoute is null, defaulting to '$it'")
            }

        Destination.entries
            .filter { it.visible }
            .sortedBy { it.index }
            .forEach { destination ->
                NavigationBarItem(
                    icon = { destination.GetIcon(context) },
                    label = { Text(destination.getTitle(context)) },
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
