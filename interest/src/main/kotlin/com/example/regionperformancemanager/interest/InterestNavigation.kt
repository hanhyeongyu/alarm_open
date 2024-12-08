package com.example.regionperformancemanager.interest

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable



@Serializable object InterestRoute

fun NavController.navigateToInterest(navOptions: NavOptions) =
    navigate(route = InterestRoute, navOptions)

fun NavGraphBuilder.interestScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable<InterestRoute> {
        InterestRoute(onShowSnackbar)
    }
}
