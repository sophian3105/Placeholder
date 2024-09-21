package dev.kimchiloof.financially.navigation.newReceipt

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.kimchiloof.financially.NewReceiptFlowActivity
import dev.kimchiloof.financially.NewReceiptFlowViewModel
import dev.kimchiloof.financially.navigation.Destination
import dev.kimchiloof.financially.ui.pages.newReceiptFlow.camera.CameraScreen
import dev.kimchiloof.financially.ui.pages.newReceiptFlow.confirmation.ConfirmationScreen


@Composable
fun NewReceiptFlowNavGraph (
    navHostController: NavHostController,
    startDestination: Destination = Destination.Camera
) {
    fun resetToHome(context: Context) {
        (context as? NewReceiptFlowActivity)?.finish()
    }

    val commonViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }
    val commonViewModel = viewModel<NewReceiptFlowViewModel>(viewModelStoreOwner = commonViewModelStoreOwner)


    val context = LocalContext.current

    NavHost(
        navController = navHostController,
        startDestination = startDestination.route
    ) {
        composable(route = Destination.Camera.route) {
            BackHandler { resetToHome(context) }
            CameraScreen(navHostController, commonViewModel)
        }
        composable(route = Destination.Confirmation.route) {
            BackHandler {
                // TODO: Delete the image file
                resetToHome(context)
            }
            ConfirmationScreen(navHostController, commonViewModel)
        }
    }
}
