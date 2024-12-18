/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.regionperformancemanager.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.regionperformancemanager.bookmark.BookmarkRoute
import com.example.regionperformancemanager.foryou.ForYouRoute
import com.example.regionperformancemanager.interest.InterestRoute
import com.example.template.core.designsystem.icon.AppIcons
import kotlin.reflect.KClass
import com.example.regionperformancemanager.bookmark.R as bookmarkR
import com.example.regionperformancemanager.foryou.R as forYouR
import com.example.regionperformancemanager.interest.R as regionR

/**
 * Type for the top level destinations in the application. Contains metadata about the destination
 * that is used in the top app bar and common navigation UI.
 *
 * @param selectedIcon The icon to be displayed in the navigation UI when this destination is
 * selected.
 * @param unselectedIcon The icon to be displayed in the navigation UI when this destination is
 * not selected.
 * @param iconTextId Text that to be displayed in the navigation UI.
 * @param titleTextId Text that is displayed on the top app bar.
 * @param route The route to use when navigating to this destination.
 * @param baseRoute The highest ancestor of this destination. Defaults to [route], meaning that
 * there is a single destination in that section of the app (no nested destinations).
 */
enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val iconTextId: Int,
    @StringRes val titleTextId: Int,
    val route: KClass<*>,
    val baseRoute: KClass<*> = route,
) {
    FORYOU(
        selectedIcon = AppIcons.News,
        unselectedIcon = AppIcons.NewsBorder,
        iconTextId = forYouR.string.forYou_title,
        titleTextId = forYouR.string.forYou_title,
        route = ForYouRoute::class,
    ),

    BOOKMARK(
        selectedIcon = AppIcons.Bookmarks,
        unselectedIcon = AppIcons.BookmarksBorder,
        iconTextId = bookmarkR.string.bookmark_title,
        titleTextId = bookmarkR.string.bookmark_title,
        route = BookmarkRoute::class,
    ),

    REGION(
        selectedIcon = AppIcons.AddLocation,
        unselectedIcon = AppIcons.AddLocationBorder,
        iconTextId = regionR.string.interest_title,
        titleTextId = regionR.string.interest_title,
        route = InterestRoute::class,
    )
}