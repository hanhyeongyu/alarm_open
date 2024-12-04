package com.example.template.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.template.core.auth.model.Auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val auth: Auth
): ViewModel(){

    private val _signupUIState: MutableStateFlow<SignupUIState> = MutableStateFlow(SignupUIState.Initialized)
    val signupUIState = this._signupUIState

    fun signup(email: String, password: String) = viewModelScope.launch {
        try{
            signupUIState.update { SignupUIState.Loading }
            auth.signup(email, password)
            signupUIState.update { SignupUIState.Success }
        }catch (e: Exception){
            signupUIState.update { SignupUIState.Error }
        }
    }

}

sealed interface SignupUIState{
    data object Initialized: SignupUIState
    data object Loading: SignupUIState
    data object Error: SignupUIState
    data object Success: SignupUIState
}