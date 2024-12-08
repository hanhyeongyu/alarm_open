package com.example.regionperformancemanager.interest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.regionperformancemanager.follow.application.FollowApplication
import com.example.regionperformancemanager.follow.model.FollowableRegion
import com.example.regionperformancemanager.region.model.Region
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InterestViewModel @Inject constructor(
    private val followApplication: FollowApplication
): ViewModel(){

    val regions: StateFlow<List<FollowableRegion>> = followApplication.followableRegion().stateIn(
        scope = viewModelScope,
        initialValue = emptyList(),
        started = SharingStarted.WhileSubscribed(5_000),
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


