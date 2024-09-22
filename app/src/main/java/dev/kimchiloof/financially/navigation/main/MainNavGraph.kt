package dev.kimchiloof.financially.navigation.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import dev.kimchiloof.financially.navigation.Destination
import kotlinx.coroutines.launch

@Composable
fun MainNavGraph (
    pagerState: PagerState,
    destinations: Map<Destination.DestinationWithDisplay, @Composable () -> Unit>,
    initialPageIndex: Int
) {
    val coroutineScope = rememberCoroutineScope()
    HorizontalPager(
        beyondViewportPageCount = 1,
        state = pagerState
    ) { page ->
        destinations.values.elementAt(page)()
        BackHandler (
            enabled = (destinations.keys.elementAt(page) != Destination.Home)
        ) {
            coroutineScope.launch {
                pagerState.animateScrollToPage(initialPageIndex)
            }
        }
    }
}
