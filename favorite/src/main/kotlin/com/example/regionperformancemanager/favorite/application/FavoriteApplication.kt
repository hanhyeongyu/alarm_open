package com.example.regionperformancemanager.favorite.application

import com.example.regionperformancemanager.favorite.repository.FavoriteRepository
import com.example.regionperformancemanager.performance.model.Performance
import com.example.regionperformancemanager.performance.repository.PerformanceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class FavoriteApplication @Inject constructor(
    private val performanceRepository: PerformanceRepository,
    private val favoriteRepository: FavoriteRepository
){
    fun favorites(): Flow<List<Performance>> =
        favoriteRepository.favorites()
            .distinctUntilChanged()
            .flatMapLatest { performanceIds ->
                performanceRepository.performances(performanceIds)
            }

    suspend fun add(performanceId: Long){
        favoriteRepository.add(performanceId)
    }

    suspend fun remove(performanceId: Long){
        favoriteRepository.remove(performanceId)
    }
}