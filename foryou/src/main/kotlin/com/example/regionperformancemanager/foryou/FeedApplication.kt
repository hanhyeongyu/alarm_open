package com.example.regionperformancemanager.foryou

import com.example.regionperformancemanager.follow.repository.FollowRepository
import com.example.regionperformancemanager.foryou.model.Feed
import com.example.regionperformancemanager.performance.repository.PerformanceRepository
import com.example.template.core.datastore.UserPreferencesDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject


class FeedApplication @Inject constructor(
    private val performanceRepository: PerformanceRepository,
    private val followRepository: FollowRepository,
    private val userPreferencesDataSource: UserPreferencesDataSource
){
    fun feeds(): Flow<List<Feed>> =
        followRepository.followedRegion()
            .distinctUntilChanged()
            .flatMapLatest { regionIds ->
                feeds(regionIds)
            }

    private fun feeds(regionIds: Set<String>): Flow<List<Feed>> =
        performanceRepository.performancesByRegionIds(regionIds)
            .combine(userPreferencesDataSource.userData){ list , userData ->
                list.map { performance ->
                    Feed(
                        performance = performance,
                        isBookmark = userData.bookmarkedPerformances.contains(performance.id.toString())
                    )
                }
            }
}