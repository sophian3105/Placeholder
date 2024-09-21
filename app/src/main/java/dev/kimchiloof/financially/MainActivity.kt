package dev.kimchiloof.financially

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import dev.kimchiloof.financially.navigation.MainNavBar
import dev.kimchiloof.financially.navigation.MainNavGraph
import dev.kimchiloof.financially.ui.pages.camera.CameraButton
import dev.kimchiloof.financially.ui.theme.FinanciallyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinanciallyTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { MainNavBar(navController) },
                    floatingActionButton = { CameraButton { granted ->
                        if (granted) {
                            startActivity(
                                Intent(this@MainActivity, CameraActivity::class.java)
                            )
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "Camera permissions required to function",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } }
                ) { paddingValues ->
                    Column (
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .verticalScroll(rememberScrollState())
                    ) {
                        MainNavGraph(navController)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FinanciallyTheme {
        Greeting("Android")
    }
}
