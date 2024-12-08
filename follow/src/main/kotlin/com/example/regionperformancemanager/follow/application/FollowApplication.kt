package com.example.regionperformancemanager.follow.application

import com.example.regionperformancemanager.follow.model.FollowableRegion
import com.example.regionperformancemanager.follow.repository.FollowRepository
import com.example.regionperformancemanager.region.model.Region
import com.example.regionperformancemanager.region.repository.RegionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FollowApplication @Inject constructor(
    private val regionRepository: RegionRepository,
    private val followRepository: FollowRepository
){
    fun followableRegion(): Flow<List<FollowableRegion>> = combine(
        followRepository.followedRegion(),
        regionRepository.regions
    ){ ids, regions ->
        val followedRegions = regions
            .map { region ->
                FollowableRegion(
                    region = region,
                    isFollowing = region.id.toString() in ids
                )
            }
        followedRegions.sortedBy { it.region.id }
    }

    suspend fun follow(region: Region){
        followRepository.add(region.id.toString())
    }

    suspend fun unFollow(region: Region){
        followRepository.remove(region.id.toString())
    }
}