package dev.kimchiloof.financially.navigation.newReceipt

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.kimchiloof.financially.navigation.main.resetToHome
import dev.kimchiloof.financially.ui.pages.newReceiptFlow.camera.CameraScreen
import dev.kimchiloof.financially.ui.pages.newReceiptFlow.confirmation.ConfirmationScreen

@Composable
fun NewReceiptFlowNavGraph (
    navHostController: NavHostController,
    startDestination: NewReceiptDestination = NewReceiptDestination.NewReceiptCamera
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination.route
    ) {
        composable(route = NewReceiptDestination.NewReceiptCamera.route) {
            BackHandler { resetToHome(navHostController) }
            CameraScreen()
        }
        composable(route = NewReceiptDestination.NewReceiptConfirmation.route) {
            BackHandler {
                // TODO: Delete the image file
                resetToHome(navHostController)
            }
            ConfirmationScreen()
        }
    }
}
