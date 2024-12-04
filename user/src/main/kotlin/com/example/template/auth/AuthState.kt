package com.example.template.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberAuthState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): AuthState{
    return remember(navController, coroutineScope){
        AuthState(
            navController = navController,
            coroutineScope = coroutineScope
        )
    }
}


@Stable
class AuthState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope
){
}