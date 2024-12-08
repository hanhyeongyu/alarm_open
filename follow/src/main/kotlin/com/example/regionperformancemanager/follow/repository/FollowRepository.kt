package com.example.regionperformancemanager.follow.repository

import com.example.template.core.datastore.UserPreferencesDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FollowRepository @Inject constructor(
    private val userPreferencesDataSource: UserPreferencesDataSource
) {

    fun followedRegion(): Flow<Set<String>> =
        userPreferencesDataSource.userData.map { it.followedRegions }

    suspend fun add(regionId: String) {
        userPreferencesDataSource.setRegionIdFollowed(regionId, true)
    }

    suspend fun remove(regionId: String){
        userPreferencesDataSource.setRegionIdFollowed(regionId, false)
    }
}