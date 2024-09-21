package dev.kimchiloof.financially.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.kimchiloof.financially.ui.pages.finances.FinancesScreen
import dev.kimchiloof.financially.ui.pages.newReceiptFlow.camera.CameraScreen

@Composable
fun NewReceiptFlowNavGraph (
    navHostController: NavHostController,
    startDestination: Destination = Destination.Home
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination.route
    ) {
        composable(
            route = Destination.NewReceiptCamera.route
        ) {
            BackHandler { resetToHome(navHostController) }
            CameraScreen()
        }
        composable(
            route = Destination.NewReceiptConfirmation.route
        ) {
            BackHandler {
                // TODO: Delete the image file
                resetToHome(navHostController)
            }
            FinancesScreen()
        }
    }
}
