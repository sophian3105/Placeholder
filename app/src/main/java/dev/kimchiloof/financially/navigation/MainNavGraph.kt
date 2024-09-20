package dev.kimchiloof.financially.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.kimchiloof.financially.ui.pages.finances.FinancesScreen
import dev.kimchiloof.financially.ui.pages.gallery.GalleryScreen
import dev.kimchiloof.financially.ui.pages.home.HomeScreen

@Composable
fun MainNavGraph (
    navHostController: NavHostController,
    startDestination: Destination = Destination.Home
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination.route
    ) {
        composable(Destination.Home.route) {
            HomeScreen()
        }
        composable(Destination.Finances.route) {
            FinancesScreen()
        }
        composable(Destination.Gallery.route) {
            GalleryScreen()
        }
    }
}