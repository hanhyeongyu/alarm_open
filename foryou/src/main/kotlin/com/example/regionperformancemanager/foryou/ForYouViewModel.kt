package com.example.regionperformancemanager.foryou

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.regionperformancemanager.favorite.application.FavoriteApplication
import com.example.regionperformancemanager.follow.application.FollowApplication
import com.example.regionperformancemanager.foryou.model.Feed
import com.example.regionperformancemanager.foryou.onboarding.OnboardingUiState
import com.example.regionperformancemanager.performance.model.Performance
import com.example.regionperformancemanager.region.model.Region
import com.example.template.core.datastore.UserPreferencesDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ForYouViewModel @Inject constructor(
    private val userPreferencesDataSource: UserPreferencesDataSource,
    private val feedApplication: FeedApplication,
    private val followApplication: FollowApplication,
    private val favoriteApplication: FavoriteApplication
): ViewModel(){
    val feed: StateFlow<List<Feed>> = feedApplication.feeds()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    val onboardingUiState: StateFlow<OnboardingUiState> =
        combine(
            shouldShowOnboarding(),
            followApplication.followableRegion(),
        ) { shouldShowOnboarding, regions ->
            if (shouldShowOnboarding) {
                OnboardingUiState.Shown(regions = regions)
            } else {
                OnboardingUiState.NotShown
            }
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = OnboardingUiState.Loading,
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

    fun setBookmark(performance: Performance, isBookmark: Boolean) {
        viewModelScope.launch {
            if(isBookmark){
                favoriteApplication.add(performance.id)
            }else{
                favoriteApplication.remove(performance.id)
            }
        }
    }


    fun dismissOnboarding() {
        viewModelScope.launch {
            userPreferencesDataSource.setShouldHideOnboarding(true)
        }
    }


    private fun shouldShowOnboarding(): Flow<Boolean> = userPreferencesDataSource.userData.map {
        !it.shouldHideOnboarding
    }


}