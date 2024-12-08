package com.example.regionperformancemanager.region


import com.example.regionperformancemanager.region.repository.RegionNetworkRepository
import com.example.regionperformancemanager.region.repository.RegionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RegionModule {
    @Binds
    abstract fun bindsRepository(regionNetworkRepository: RegionNetworkRepository): RegionRepository
}
