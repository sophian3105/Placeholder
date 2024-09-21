package dev.kimchiloof.financially.navigation.main

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.kimchiloof.financially.navigation.Destination
import dev.kimchiloof.financially.ui.pages.finances.FinancesScreen
import dev.kimchiloof.financially.ui.pages.gallery.GalleryScreen
import dev.kimchiloof.financially.ui.pages.home.HomeScreen

@Composable
fun MainNavGraph (
    navHostController: NavHostController,
    startDestination: Destination = Destination.Home
) {
    fun resetToHome() {
        // Navigate direct to the Home screen and clear the back stack
        navHostController.navigate(Destination.Home.route) {
            popUpTo(Destination.Home.route) { inclusive = true }
        }
    }

    NavHost(
        navController = navHostController,
        startDestination = startDestination.route
    ) {
        composable(
            route = Destination.Home.route
        ) {
            HomeScreen()
        }
        composable(
            route = Destination.Finances.route
        ) {
            BackHandler { resetToHome() }
            FinancesScreen()
        }
        composable(
            route = Destination.Gallery.route
        ) {
            BackHandler { resetToHome() }
            GalleryScreen()
        }
    }
}
