package com.example.template.auth.signup

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object SignupRoute

fun NavController.navigateToSignup(navOptions: NavOptions? = null) =
    navigate(route = SignupRoute, navOptions)


fun NavGraphBuilder.signupScreen(
    onBackClick: () -> Unit,
    onSignUp: () -> Unit
) {
    composable<SignupRoute> {
        SignupScreen(
            onBackClick = onBackClick,
            onSignup = onSignUp
        )
    }
}