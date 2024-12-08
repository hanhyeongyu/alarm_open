package com.example.regionperformancemanager.foryou

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object ForYouRoute

fun NavController.navigateToForYou(navOptions: NavOptions) =
    navigate(route = ForYouRoute, navOptions)

fun NavGraphBuilder.forYouScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable<ForYouRoute> {
        ForYouRoute(onShowSnackbar)
    }
}
