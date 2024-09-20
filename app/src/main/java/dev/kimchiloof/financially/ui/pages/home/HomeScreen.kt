package dev.kimchiloof.financially.ui.pages.home

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.kimchiloof.financially.Greeting

@Composable
fun HomeScreen() {
    Greeting(
        name = "Home",
        modifier = Modifier.padding(16.dp)
    )
}