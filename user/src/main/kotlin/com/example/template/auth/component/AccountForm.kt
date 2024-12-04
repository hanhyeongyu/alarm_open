package com.example.template.auth.component


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.template.core.designsystem.component.AppBackground
import com.example.template.core.designsystem.theme.AppTheme

@Composable
fun AccountForm(
    email: String,
    password: String,
    emailChanged: (String) -> Unit,
    passwordChanged: (String) -> Unit,
    onDoneTriggered: () -> Unit,
    modifier: Modifier = Modifier
){

    val emailFocusRequester = remember{ FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }

    Column(
        modifier = modifier.fillMaxWidth()
    ){
        EmailTextField(
            value = email,
            onValueChange = emailChanged,
            onNextTriggered = passwordFocusRequester::requestFocus,
            modifier = Modifier
                .focusRequester(emailFocusRequester)
        )

        Spacer(modifier = Modifier.height(8.dp))

        PasswordTextField(
            value = password,
            onValueChange = passwordChanged,
            onDoneTriggered = onDoneTriggered,
            modifier = Modifier
                .focusRequester(passwordFocusRequester)
        )

    }
}


@Composable
fun EmailTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onNextTriggered: () -> Unit,
    modifier: Modifier = Modifier
){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(text = "Email")
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                onNextTriggered()
            },
        ),
        maxLines = 1,
        singleLine = true,
        modifier = modifier.fillMaxWidth()
    )

}

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onDoneTriggered: () -> Unit,
    modifier: Modifier = Modifier
){

    var showPassword by rememberSaveable { mutableStateOf(false) }


    var visualTransformation: VisualTransformation = if (showPassword) {
        VisualTransformation.None
    } else {
        PasswordVisualTransformation()
    }


    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(text = "Password")
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        trailingIcon = {
            IconButton(onClick = { showPassword = !showPassword}) {
                PasswordVisibilityIcon(showPassword)
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onDoneTriggered()
            },
        ),
        visualTransformation = visualTransformation,
        maxLines = 1,
        singleLine = true,
        modifier = modifier.fillMaxWidth()

    )
}

@Composable
private fun PasswordVisibilityIcon(visibility: Boolean){
    if(visibility){
        Icon(
            imageVector = Icons.Filled.Visibility,
            contentDescription = "hide_password"
        )
    }else{
        Icon(
            imageVector = Icons.Filled.VisibilityOff,
            contentDescription = "hide_password"
        )
    }
}


@Preview
@Composable
fun AccountFormPreview(){
    AppTheme {
        AppBackground {
            AccountForm(
                email = "email",
                password = "password",
                emailChanged = {},
                passwordChanged = {},
                onDoneTriggered = {}
            )
        }
    }
}

@Preview
@Composable
fun EmailTextFieldPreview(){
    AppTheme {
        EmailTextField(
            value = "",
            onValueChange = {},
            onNextTriggered = {}
        )
    }
}

@Preview
@Composable
fun PasswordTextFieldPreview(){
    AppTheme {
        PasswordTextField(
            value = "",
            onValueChange = {},
            onDoneTriggered = {}
        )
    }
}