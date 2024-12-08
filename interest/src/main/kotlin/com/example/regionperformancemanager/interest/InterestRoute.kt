package com.example.regionperformancemanager.interest

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.regionperformancemanager.follow.model.FollowableRegion
import com.example.regionperformancemanager.region.model.Region
import com.example.template.core.designsystem.component.AppBackground
import com.example.template.core.designsystem.component.AppLoadingWheel
import com.example.template.core.designsystem.component.scrollbar.DraggableScrollbar
import com.example.template.core.designsystem.component.scrollbar.rememberDraggableScroller
import com.example.template.core.designsystem.component.scrollbar.scrollbarState
import com.example.template.core.designsystem.theme.AppTheme

@Composable
fun InterestRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    viewModel: InterestViewModel = hiltViewModel()
){

    val interestUiState by viewModel.interestUiState.collectAsStateWithLifecycle()

    InterestScreen(
        interestUiState = interestUiState,
        onFollowClick = viewModel::setFollowing,
        modifier = modifier
    )
}


@Composable
fun InterestScreen(
    interestUiState: InterestUiState,
    onFollowClick: (region: Region, isFollowing: Boolean) -> Unit,
    modifier: Modifier = Modifier,
){
    when(interestUiState){
        InterestUiState.Loading -> {
            AppLoadingWheel(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentSize(),
                contentDesc = "Loading interests…"
            )
        }
        is InterestUiState.Success ->
            RegionsContent(
                regions = interestUiState.regions,
                onFollowClick = onFollowClick,
                modifier = modifier
            )
    }

}


@Composable
fun RegionsContent(
    regions: List<FollowableRegion>,
    onFollowClick: (Region, Boolean) -> Unit,
    modifier: Modifier
){
    Box(modifier = modifier.fillMaxWidth()){

        val scrollableState = rememberLazyListState()

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .testTag("interests:topics"),
            contentPadding = PaddingValues(vertical = 16.dp),
            state = scrollableState,
        ) {
            items(regions){ followed ->
                InterestsItem(
                    name = followed.region.name,
                    imageUrl = followed.region.imageUrl,
                    isFollowing = followed.isFollowing,
                    onClick = {
                        //nothing to do
                    },
                    onFollowButtonClick = {
                        onFollowClick(followed.region, it)
                    }
                )
            }
        }

        val scrollbarState = scrollableState.scrollbarState(
            itemsAvailable = regions.size,
        )
        scrollableState.DraggableScrollbar(
            modifier = Modifier
                .fillMaxHeight()
                .windowInsetsPadding(WindowInsets.systemBars)
                .padding(horizontal = 2.dp)
                .align(Alignment.CenterEnd),
            state = scrollbarState,
            orientation = Orientation.Vertical,
            onThumbMoved = scrollableState.rememberDraggableScroller(
                itemsAvailable = regions.size,
            ),
        )

    }
}


@Preview
@Composable
fun RegionScreenPreview(){
    AppTheme {
        AppBackground {
            InterestScreen(
                interestUiState = InterestUiState.Success(
                    listOf(
                        FollowableRegion(
                            true,
                            Region(id = 1, name = "Seoul", imageUrl = ""
                    ))
                )),
                onFollowClick = { _, _ -> }
            )
        }
    }
}

@Preview
@Composable
fun RegionScreenLoadingPreview(){
    AppTheme {
        AppBackground {
            InterestScreen(
                interestUiState = InterestUiState.Loading,
                onFollowClick = { _, _ -> }
            )
        }
    }
}