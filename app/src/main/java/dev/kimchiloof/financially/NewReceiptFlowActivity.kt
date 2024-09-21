package dev.kimchiloof.financially

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dev.kimchiloof.financially.ui.pages.newReceiptFlow.camera.CameraScreen
import dev.kimchiloof.financially.ui.theme.FinanciallyTheme

class NewReceiptFlowActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinanciallyTheme {
                CameraScreen()
            }
        }
    }
}
