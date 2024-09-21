package dev.kimchiloof.financially.navigation.newReceipt

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.kimchiloof.financially.NewReceiptFlowActivity
import dev.kimchiloof.financially.ui.pages.newReceiptFlow.camera.CameraScreen
import dev.kimchiloof.financially.ui.pages.newReceiptFlow.confirmation.ConfirmationScreen

@Composable
fun NewReceiptFlowNavGraph (
    navHostController: NavHostController,
    startDestination: NewReceiptDestination = NewReceiptDestination.NewReceiptCamera
) {
    fun resetToHome(context: Context) {
        (context as? NewReceiptFlowActivity)?.finish()
    }

    val context = LocalContext.current

    NavHost(
        navController = navHostController,
        startDestination = startDestination.route
    ) {
        composable(route = NewReceiptDestination.NewReceiptCamera.route) {
            BackHandler { resetToHome(context) }
            CameraScreen(navHostController)
        }
        composable(route = NewReceiptDestination.NewReceiptConfirmation.route) {
            BackHandler {
                // TODO: Delete the image file
                resetToHome(context)
            }
            ConfirmationScreen(navHostController)
        }
    }
}
