package com.example.regionperformancemanager.foryou

import android.net.Uri
import android.util.Log
import android.webkit.URLUtil
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.regionperformancemanager.foryou.onboarding.OnBoarding
import com.example.regionperformancemanager.foryou.onboarding.OnboardingUiState
import com.example.regionperformancemanager.performance.launchCustomChromeTab
import com.example.regionperformancemanager.performance.model.Performance
import com.example.regionperformancemanager.region.model.Region
import com.example.template.core.designsystem.component.AppOverlayLoadingWheel
import com.example.template.core.designsystem.component.scrollbar.DraggableScrollbar
import com.example.template.core.designsystem.component.scrollbar.rememberDraggableScroller
import com.example.template.core.designsystem.component.scrollbar.scrollbarState


@Composable
internal fun ForYouRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    viewModel: ForYouViewModel = hiltViewModel()
){
    val feedUiState by viewModel.feedUiState.collectAsStateWithLifecycle()
    val onboardingUiState by viewModel.onboardingUiState.collectAsStateWithLifecycle()

    ForYouScreen(
        feedUiState = feedUiState,
        onboardingUiState = onboardingUiState,
        onFollowButtonClick = viewModel::setFollowing,
        onBookmarkChange = viewModel::setBookmark,
        saveFollowedRegions = viewModel::dismissOnboarding,
        modifier = modifier
    )
}


@Composable
fun ForYouScreen(
    feedUiState: FeedUiState,
    onboardingUiState: OnboardingUiState,
    onFollowButtonClick: (Region, Boolean) -> Unit,
    onBookmarkChange: (Performance, Boolean) -> Unit,
    saveFollowedRegions: () -> Unit,
    modifier: Modifier = Modifier,
){
    //scrollview
    val state = rememberLazyStaggeredGridState()
    val itemsAvailable = itemsSize(feedUiState, onboardingUiState)
    val scrollbarState = state.scrollbarState(
        itemsAvailable = itemsAvailable,
    )

    //loading
    val isOnboardingLoading = onboardingUiState is OnboardingUiState.Loading
    val isFeedLoading = feedUiState is FeedUiState.Loading

    //launch Chrome
    val context = LocalContext.current
    val backgroundColor = MaterialTheme.colorScheme.background.toArgb()

    Box(modifier = modifier.fillMaxSize()){
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
                feedUIState = feedUiState,
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


        AnimatedVisibility(
            visible = isFeedLoading || isOnboardingLoading,
            enter = slideInVertically(
                initialOffsetY = { fullHeight -> -fullHeight },
            ) + fadeIn(),
            exit = slideOutVertically(
                targetOffsetY = { fullHeight -> -fullHeight },
            ) + fadeOut(),
        ) {
            val loadingContentDescription = stringResource(id = R.string.forYou_loading)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
            ) {
                AppOverlayLoadingWheel(
                    modifier = Modifier
                        .align(Alignment.Center),
                    contentDesc = loadingContentDescription,
                )
            }
        }

        state.DraggableScrollbar(
            modifier = Modifier
                .fillMaxHeight()
                .windowInsetsPadding(WindowInsets.systemBars)
                .padding(horizontal = 2.dp)
                .align(Alignment.CenterEnd),
            state = scrollbarState,
            orientation = Orientation.Vertical,
            onThumbMoved = state.rememberDraggableScroller(
                itemsAvailable = itemsAvailable,
            ),
        )
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
    feedUIState: FeedUiState,
    onFeedClick: (Performance) -> Unit,
    onBookmarkChange: (Performance, Boolean) -> Unit
){
    when(feedUIState){
        FeedUiState.Loading -> Unit
        is FeedUiState.Success -> {
            items(
                items = feedUIState.feeds,
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
    }
}


private fun itemsSize(
    feedState: FeedUiState,
    onboardingUiState: OnboardingUiState,
): Int {
    val feedSize = when (feedState) {
        FeedUiState.Loading -> 0
        is FeedUiState.Success -> feedState.feeds.size
    }
    val onboardingSize = when (onboardingUiState) {
        OnboardingUiState.Loading,
        OnboardingUiState.LoadFailed,
        OnboardingUiState.NotShown,
            -> 0

        is OnboardingUiState.Shown -> 1
    }
    return feedSize + onboardingSize
}