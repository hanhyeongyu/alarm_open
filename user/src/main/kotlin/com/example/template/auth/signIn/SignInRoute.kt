package com.example.template.auth.signIn

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object SignInRoute

fun NavController.navigateToSignIn(navOptions: NavOptions) =
    navigate(route = SignInRoute, navOptions)


fun NavGraphBuilder.signInScreen(
    onSignupClick: () -> Unit
) {
    composable<SignInRoute> {
        SignInScreen(onSignupClick = onSignupClick)
    }
}