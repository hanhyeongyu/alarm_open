package com.example.regionperformancemanager.region.repository

import com.example.regionperformancemanager.region.model.Region
import kotlinx.coroutines.flow.Flow

interface RegionRepository{
    val regions: Flow<List<Region>>
}