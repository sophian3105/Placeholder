package dev.kimchiloof.financially.ui.pages.finances

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.kimchiloof.financially.Greeting

@Composable
fun FinancesScreen() {
    Greeting(
        name = "Finances",
        modifier = Modifier.padding(16.dp)
    )
}