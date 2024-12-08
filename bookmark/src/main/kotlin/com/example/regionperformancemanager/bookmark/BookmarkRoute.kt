package com.example.regionperformancemanager.bookmark

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.regionperformancemanager.performance.PerformanceItem
import com.example.regionperformancemanager.performance.launchCustomChromeTab
import com.example.regionperformancemanager.performance.model.Performance
import com.example.template.core.designsystem.component.AppBackground
import com.example.template.core.designsystem.component.AppLoadingWheel
import com.example.template.core.designsystem.component.scrollbar.DraggableScrollbar
import com.example.template.core.designsystem.component.scrollbar.rememberDraggableScroller
import com.example.template.core.designsystem.component.scrollbar.scrollbarState
import com.example.template.core.designsystem.icon.AppIcons
import com.example.template.core.designsystem.theme.AppTheme

@Composable
fun BookmarkRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    viewModel: BookmarkViewModel = hiltViewModel()
){
    val bookmarkUiState by viewModel.bookmarkUiState.collectAsStateWithLifecycle()

    BookmarkScreen(
        bookmarkUiState = bookmarkUiState,
        onBookmarkChange = viewModel::setBookmark,
        modifier = modifier
    )
}


@Composable
fun BookmarkScreen(
    bookmarkUiState: BookmarkUiState,
    onBookmarkChange: (Performance, Boolean) -> Unit,
    modifier: Modifier = Modifier
){

    when(bookmarkUiState){
        BookmarkUiState.Loading -> {
            AppLoadingWheel(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentSize(),
                contentDesc = "Loading bookmarks…"
            )
        }
        is BookmarkUiState.Success -> {
            val performances = bookmarkUiState.performances
            if (performances.isEmpty()){
                EmptyState(modifier = modifier)
            }else{
                BookmarksGrid(
                    performances = performances,
                    onBookmarkChange = onBookmarkChange,
                    modifier = modifier
                )
            }
        }
    }


}

@Composable
fun BookmarksGrid(
    performances: List<Performance>,
    onBookmarkChange: (Performance, Boolean) -> Unit,
    modifier: Modifier = Modifier
){
    val scrollableState = rememberLazyStaggeredGridState()


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
                state = scrollableState,
            ){
                performances(
                    performances = performances,
                    onPerformanceClick = { performance ->
                        if (performance.homePageUrl.isNotEmpty()){
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

            val itemsAvailable = performances.count()
            val scrollbarState = scrollableState.scrollbarState(
                itemsAvailable = itemsAvailable,
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
                    itemsAvailable = itemsAvailable,
                ),
            )
        }
    }
}

fun LazyStaggeredGridScope.performances(
    performances: List<Performance>,
    onPerformanceClick: (Performance) -> Unit,
    onBookmarkChange: (Performance, Boolean) -> Unit
){
    items(
        items = performances,
        key = { it.id },
        contentType = { "performanceItem" },
    ){ performance ->
        PerformanceItem(
            performance = performance,
            onClick = { onPerformanceClick(performance) },
            onBookmarkChange = { onBookmarkChange(performance, it) },
            modifier = Modifier.padding(horizontal = 8.dp),
        )
    }
}

@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            imageVector = AppIcons.Bookmark,
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = stringResource(R.string.bookmark_empty),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.bookmark_need_save),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}


@Preview
@Composable
private fun BookmarksLoadingPreview(
) {
    AppTheme {
        AppBackground {
            BookmarkScreen(
                bookmarkUiState = BookmarkUiState.Loading,
                onBookmarkChange = {  _, _ ->},
            )
        }
    }
}


@Preview
@Composable
private fun EmptyStatePreview() {
    AppTheme(){
        AppBackground {
            EmptyState()
        }
    }
}
