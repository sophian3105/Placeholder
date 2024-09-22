package dev.kimchiloof.financially.navigation.main

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import dev.kimchiloof.financially.navigation.Destination
import kotlinx.coroutines.launch

@Composable
fun MainNavBar(pagerState: PagerState, navDestinations: List<Destination.DestinationWithDisplay>) {
    NavigationBar (
        modifier = Modifier.fillMaxWidth()
    ) {
        val coroutineScope = rememberCoroutineScope()
        val currentRoute = navDestinations[pagerState.currentPage].route

        navDestinations.forEach { destination ->
            NavigationBarItem(
                icon = { destination.icon?.let { Icon(it, destination.title) } },
                label = { Text(destination.title ?: "") },
                selected = (currentRoute == destination.route),
                onClick = {
                    if (currentRoute != destination.route) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(navDestinations.indexOf(destination))
                        }
                    }
                }
            )
        }
    }
}
