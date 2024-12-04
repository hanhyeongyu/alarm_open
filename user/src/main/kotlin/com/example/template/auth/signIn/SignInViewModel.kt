package com.example.template.auth.signIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.template.core.auth.model.Auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val auth : Auth
): ViewModel(){

    private val _signInUIState: MutableStateFlow<SignInUIState> = MutableStateFlow(SignInUIState.Initialized)
    val signInUIState: StateFlow<SignInUIState> = _signInUIState

    fun signInOnClick(email: String, password: String){
        viewModelScope.launch {
            try{
                _signInUIState.update { SignInUIState.Loading }
                auth.signIn(email, password)
                _signInUIState.update { SignInUIState.Success }
            }catch (e: Exception){
                _signInUIState.update { SignInUIState.Error }
            }

        }
    }

}


sealed interface SignInUIState{
    data object Initialized: SignInUIState
    data object Loading: SignInUIState
    data object Error: SignInUIState
    data object Success: SignInUIState
}