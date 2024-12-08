package com.example.regionperformancemanager.performance.repository

import com.example.regionperformancemanager.performance.model.Performance
import kotlinx.coroutines.flow.Flow

interface PerformanceRepository{
    val performances: Flow<List<Performance>>
    fun performances(performanceIds: Set<String>): Flow<List<Performance>>
    fun performancesByRegionIds(regionIds: Set<String>): Flow<List<Performance>>
}