package dev.kimchiloof.financially.ui.pages.gallery

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.kimchiloof.financially.Greeting

@Composable
fun GalleryScreen() {
    Greeting(
        name = "Gallery",
        modifier = Modifier.padding(16.dp)
    )
}