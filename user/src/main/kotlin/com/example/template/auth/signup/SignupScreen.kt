package com.example.template.auth.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.template.core.designsystem.icon.AppIcons
import com.example.template.core.designsystem.theme.AppTheme


@Composable
fun SignupScreen(
    onBackClick: () -> Unit,
    onSignup: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignupViewModel = hiltViewModel(),
){
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val signupUIState by viewModel.signupUIState.collectAsStateWithLifecycle()


    LaunchedEffect(signupUIState) {
        if (signupUIState is SignupUIState.Success) {
            onSignup()
        }
    }

    SignupScreen(
        signupUIState = signupUIState,
        email = email,
        password = password,
        emailChanged = { email = it },
        passwordChanged = { password = it},
        onBackClick = onBackClick,
        onSignupClick = {
            viewModel.signup(email, password)
        },
        modifier = modifier
    )
}

@Composable
fun SignupScreen(
    signupUIState: SignupUIState,
    email: String,
    password: String,
    emailChanged: (String) -> Unit,
    passwordChanged: (String) -> Unit,
    onBackClick: () -> Unit,
    onSignupClick: () -> Unit,
    modifier: Modifier = Modifier,
){
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))

            SignupToolbar(
                modifier = modifier,
                onBackClick = onBackClick,
            )

            Text(
                text = "Signup",
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
            )
        }


        AccountForm(
            email = email,
            password = password,
            emailChanged = emailChanged,
            passwordChanged = passwordChanged,
            onDoneTriggered = onSignupClick,
            modifier =  Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(16.dp),
        )


        AppButton(
            onClick = onSignupClick,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
        ) {
            Text("Signup")
        }

        if (signupUIState is SignupUIState.Loading){
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






@Composable
private fun SignupToolbar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        IconButton(onClick = { onBackClick() }) {
            Icon(
                imageVector = AppIcons.ArrowBack,
                contentDescription = null,
            )
        }
    }
}


@Preview
@Composable
fun SignUpPreview(){
    AppTheme {
        AppBackground {
            SignupScreen(
                signupUIState = SignupUIState.Loading,
                email = "",
                password = "",
                emailChanged = {},
                passwordChanged = {},
                onBackClick = {},
                onSignupClick = {}
            )
        }
    }
}
