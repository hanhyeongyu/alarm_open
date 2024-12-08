package com.example.regionperformancemanager.interest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.regionperformancemanager.follow.application.FollowApplication
import com.example.regionperformancemanager.follow.model.FollowableRegion
import com.example.regionperformancemanager.region.model.Region
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InterestViewModel @Inject constructor(
    private val followApplication: FollowApplication
): ViewModel(){

    val interestUiState: StateFlow<InterestUiState> = followApplication.followableRegion()
        .map(InterestUiState::Success)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = InterestUiState.Loading,
        )

    fun setFollowing(region: Region, isFollowing: Boolean){
        viewModelScope.launch {
            if(isFollowing){
                followApplication.follow(region)
            }else{
                followApplication.unFollow(region)
            }
        }
    }
}


sealed interface InterestUiState{
    data object Loading: InterestUiState
    data class Success(val regions: List<FollowableRegion>): InterestUiState
}



