package com.example.regionperformancemanager.foryou

import android.net.Uri
import android.util.Log
import android.webkit.URLUtil
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.regionperformancemanager.foryou.model.Feed
import com.example.regionperformancemanager.foryou.onboarding.OnBoarding
import com.example.regionperformancemanager.foryou.onboarding.OnboardingUiState
import com.example.regionperformancemanager.performance.launchCustomChromeTab
import com.example.regionperformancemanager.performance.model.Performance
import com.example.regionperformancemanager.region.model.Region


@Composable
internal fun ForYouRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    viewModel: ForYouViewModel = hiltViewModel()
){
    val onboardingUiState by viewModel.onboardingUiState.collectAsStateWithLifecycle()
    val forYouFeeds by viewModel.feed.collectAsStateWithLifecycle()

    ForYouScreen(
        feeds = forYouFeeds,
        onboardingUiState = onboardingUiState,
        onFollowButtonClick = viewModel::setFollowing,
        onBookmarkChange = viewModel::setBookmark,
        saveFollowedRegions = viewModel::dismissOnboarding,
        modifier = modifier
    )
}


@Composable
fun ForYouScreen(
    feeds: List<Feed>,
    onboardingUiState: OnboardingUiState,
    onFollowButtonClick: (Region, Boolean) -> Unit,
    onBookmarkChange: (Performance, Boolean) -> Unit,
    saveFollowedRegions: () -> Unit,
    modifier: Modifier = Modifier
){
    val state = rememberLazyStaggeredGridState()


    //for launch Chrome
    val context = LocalContext.current
    val backgroundColor = MaterialTheme.colorScheme.background.toArgb()

    Column(modifier = modifier.fillMaxSize()){
        Box{
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(300.dp),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalItemSpacing = 24.dp,
                modifier = Modifier
                    .testTag("forYou:feed"),
                state = state,
            ){
                onboarding(
                    onboardingUiState = onboardingUiState,
                    onFollowClick = onFollowButtonClick,
                    saveFollowedRegions = saveFollowedRegions,
                    interestsItemModifier = Modifier.layout { measurable, constraints ->
                        val placeable = measurable.measure(
                            constraints.copy(
                                maxWidth = constraints.maxWidth + 32.dp.roundToPx(),
                            ),
                        )
                        layout(placeable.width, placeable.height) {
                            placeable.place(0, 0)
                        }
                    },
                )

                feeds(
                    feeds = feeds,
                    onFeedClick = { performance ->
                        if(URLUtil.isValidUrl(performance.homePageUrl)){
                            Log.i("hompageURL", "homepageUrl ${performance.homePageUrl})")
                            launchCustomChromeTab(
                                context = context,
                                uri = Uri.parse(performance.homePageUrl),
                                toolbarColor = backgroundColor,
                            )
                        }
                    },
                    onBookmarkChange = onBookmarkChange
                )


            }

        }


    }
}



private fun LazyStaggeredGridScope.onboarding(
    onboardingUiState: OnboardingUiState,
    onFollowClick: (Region, Boolean) -> Unit,
    saveFollowedRegions: () -> Unit,
    interestsItemModifier: Modifier = Modifier,
) {
    when (onboardingUiState) {
        OnboardingUiState.Loading,
        OnboardingUiState.LoadFailed,
        OnboardingUiState.NotShown,
            -> Unit
        is OnboardingUiState.Shown -> {
            item(span = StaggeredGridItemSpan.FullLine, contentType = "onboarding") {
                OnBoarding(
                    onboardingUiState = onboardingUiState,
                    onFollowButtonClick = onFollowClick,
                    saveFollowedRegions, interestsItemModifier
                )
            }
        }
    }
}

fun LazyStaggeredGridScope.feeds(
    feeds: List<Feed>,
    onFeedClick: (Performance) -> Unit,
    onBookmarkChange: (Performance, Boolean) -> Unit
){
    items(
        items = feeds,
        key = { it.id },
        contentType = { "performanceItem" },
    ){ feed ->
        val performance = feed.performance

        FeedRow(
            feed = feed,
            onClick = { onFeedClick(performance) },
            onBookmarkChange = { onBookmarkChange(performance, it) },
            modifier = Modifier.padding(horizontal = 8.dp),
        )
    }
}