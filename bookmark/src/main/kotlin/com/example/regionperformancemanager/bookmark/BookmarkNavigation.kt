package com.example.regionperformancemanager.bookmark

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable object BookmarkRoute

fun NavController.navigateToBookmarks(navOptions: NavOptions) =
    navigate(route = BookmarkRoute, navOptions)

fun NavGraphBuilder.bookmarksScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    composable<BookmarkRoute> {
        BookmarkRoute(
            onShowSnackbar = onShowSnackbar
        )
    }
}
