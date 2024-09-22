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
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.kimchiloof.financially.navigation.Destination
import dev.kimchiloof.financially.navigation.main.MainNavBar
import dev.kimchiloof.financially.navigation.main.MainNavGraph
import dev.kimchiloof.financially.ui.pages.finances.FinancesScreen
import dev.kimchiloof.financially.ui.pages.gallery.GalleryScreen
import dev.kimchiloof.financially.ui.pages.home.HomeScreen
import dev.kimchiloof.financially.ui.pages.newReceiptFlow.camera.CameraButton
import dev.kimchiloof.financially.ui.theme.FinanciallyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinanciallyTheme {
                val initialPageIndex = 1
                val navScreens: Map<Destination.DestinationWithDisplay, @Composable () -> Unit> = mapOf(
                    Destination.Gallery to { GalleryScreen() },
                    Destination.Home to { HomeScreen() },
                    Destination.Finances to { FinancesScreen() },
                )

                val pagerState = rememberPagerState (initialPageIndex) { navScreens.size }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { MainNavBar(pagerState, navScreens.keys.toList()) },
                    floatingActionButton = { CameraButton { granted ->
                        if (granted) {
                            startActivity(
                                Intent(this@MainActivity, NewReceiptFlowActivity::class.java)
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
                    ) {
                        MainNavGraph(pagerState, navScreens, initialPageIndex)
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
