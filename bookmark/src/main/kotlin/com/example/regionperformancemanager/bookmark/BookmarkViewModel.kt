package com.example.regionperformancemanager.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.regionperformancemanager.favorite.application.FavoriteApplication
import com.example.regionperformancemanager.performance.model.Performance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val favoriteApplication: FavoriteApplication
): ViewModel(){
    val bookmarkUiState: StateFlow<BookmarkUiState> = favoriteApplication.favorites()
        .map(BookmarkUiState::Success)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = BookmarkUiState.Loading,
        )

    fun setBookmark(performance: Performance, isBookmark: Boolean) {
        viewModelScope.launch {
            if(isBookmark){
                favoriteApplication.add(performance.id)
            }else{
                favoriteApplication.remove(performance.id)
            }
        }
    }
}

sealed interface BookmarkUiState{
    data object Loading: BookmarkUiState
    data class Success(val performances: List<Performance>): BookmarkUiState
}