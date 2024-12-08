package com.example.regionperformancemanager.favorite.repository

import com.example.template.core.datastore.UserPreferencesDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepository @Inject constructor(
    private val userPreferencesDataSource: UserPreferencesDataSource
){
    fun favorites(): Flow<Set<String>> =
        userPreferencesDataSource.userData.map {
            it.bookmarkedPerformances
        }

    suspend fun add(performanceId: Long){
        userPreferencesDataSource.setPerformanceBookmarked(performanceId.toString(), true)
    }

    suspend fun remove(performanceId: Long){
        userPreferencesDataSource.setPerformanceBookmarked(performanceId.toString(), false)
    }
}