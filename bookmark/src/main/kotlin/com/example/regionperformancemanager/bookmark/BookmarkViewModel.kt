package com.example.regionperformancemanager.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.regionperformancemanager.favorite.application.FavoriteApplication
import com.example.regionperformancemanager.performance.model.Performance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val favoriteApplication: FavoriteApplication
): ViewModel(){



    val performances: StateFlow<List<Performance>> = favoriteApplication.favorites().stateIn(
        scope = viewModelScope,
        initialValue = emptyList(),
        started = SharingStarted.WhileSubscribed(5_000),
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