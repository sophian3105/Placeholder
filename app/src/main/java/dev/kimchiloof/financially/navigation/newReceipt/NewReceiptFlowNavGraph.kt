package dev.kimchiloof.financially.navigation.newReceipt

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.kimchiloof.financially.NewReceiptFlowViewModel
import dev.kimchiloof.financially.navigation.Destination
import dev.kimchiloof.financially.ui.pages.newReceiptFlow.camera.CameraScreen
import dev.kimchiloof.financially.ui.pages.newReceiptFlow.confirmation.ConfirmationScreen
import dev.kimchiloof.financially.utils.endActivity


@Composable
fun NewReceiptFlowNavGraph (
    navHostController: NavHostController,
    activityViewModel: NewReceiptFlowViewModel,
    startDestination: Destination = Destination.Camera
) {
    val context = LocalContext.current

    NavHost(
        navController = navHostController,
        startDestination = startDestination.route
    ) {
        composable(route = Destination.Camera.route) {
            BackHandler { navHostController.endActivity(context) }
            CameraScreen(navHostController, activityViewModel)
        }
        composable(route = Destination.Confirmation.route) {
            BackHandler {
                // TODO: Delete the image file
                navHostController.endActivity(context)
            }
            ConfirmationScreen(navHostController, activityViewModel)
        }
    }
}
