package com.example.regionperformancemanager.performance

import com.example.regionperformancemanager.performance.repository.PerformanceNetworkRepository
import com.example.regionperformancemanager.performance.repository.PerformanceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class PerformanceModule {
    @Binds
    abstract fun bindsRepository(performanceNetworkRepository: PerformanceNetworkRepository): PerformanceRepository
}
