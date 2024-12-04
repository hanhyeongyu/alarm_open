package com.example.template.auth.signIn

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.template.auth.component.AccountForm
import com.example.template.core.designsystem.component.AppBackground
import com.example.template.core.designsystem.component.AppButton
import com.example.template.core.designsystem.component.AppLoadingWheel
import com.example.template.core.designsystem.theme.AppTheme


@Composable
fun SignInScreen(
    onSignupClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel()
){
    val signInUIState by viewModel.signInUIState.collectAsStateWithLifecycle()

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    SignInScreen(
        signInUIState = signInUIState,
        email = email,
        password = password,
        emailChanged = { email = it},
        passwordChanged = { password = it},
        onSignInClick = { viewModel.signInOnClick(email, password)},
        onSignupClick = onSignupClick,
        modifier = modifier
    )
}

@Composable
fun SignInScreen(
    signInUIState: SignInUIState,
    email: String,
    password: String,
    emailChanged: (String) -> Unit,
    passwordChanged: (String) -> Unit,
    onSignInClick: () -> Unit,
    onSignupClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = Modifier.fillMaxSize()){
        Text(
            text = "Login",
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            modifier = Modifier
                .align(Alignment.TopCenter) // 맨 위 중앙 정렬
                .padding(top = 16.dp) // 여백 추가
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            AccountForm(
                email = email,
                password = password,
                emailChanged = emailChanged,
                passwordChanged = passwordChanged,
                onDoneTriggered = onSignInClick
            )

            Spacer(Modifier.height(16.dp))

            AppButton(
                onClick = onSignInClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("SignIn")
            }
        }


        AppButton(
            onClick = onSignupClick,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp) // 하단 여백 추가
        ) {
            Text("Signup")
        }

        if (signInUIState is SignInUIState.Loading){
            AppLoadingWheel(
                modifier = modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .wrapContentSize(),
                contentDesc = "Loading signIn…",
            )
        }
    }
}


@Preview
@Composable
fun SignInPreview(){
    AppTheme {
        AppBackground {
            SignInScreen(
                signInUIState = SignInUIState.Loading,
                email = "",
                password = "",
                emailChanged = {},
                passwordChanged = {},
                onSignInClick = { },
                onSignupClick = { },
                modifier = Modifier
            )
        } 
    }
}
