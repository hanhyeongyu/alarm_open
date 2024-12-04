package com.example.regionperformancemanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.regionperformancemanager.MainActivityUiState.Loading
import com.example.regionperformancemanager.MainActivityUiState.Success
import com.example.template.core.datastore.UserData
import com.example.template.core.datastore.UserPreferencesDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    preferences: UserPreferencesDataSource
) : ViewModel() {

    val uiState: StateFlow<MainActivityUiState> = preferences.userData.map {
        Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = Loading,
        started = SharingStarted.WhileSubscribed(5_000),
    )

}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Success(val userData: UserData) : MainActivityUiState
}
