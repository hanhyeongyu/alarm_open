package com.example.template.auth

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.template.auth.navigation.AuthNavHost
import com.example.template.core.designsystem.component.AppBackground
import com.example.template.core.designsystem.theme.AppTheme

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
){

    val authState = rememberAuthState()

    AppBackground{
        Scaffold(
            modifier = modifier,
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            contentWindowInsets = WindowInsets(0, 0, 0, 0)
        ){ padding ->
            AuthNavHost(
                authState = authState,
                modifier = modifier.padding(padding)
            )
        }
    }



}

@Composable
@Preview
fun AuthScreenPreview(){
    AppTheme {
        AuthScreen(Modifier)
    }
}
