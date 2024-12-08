package com.example.regionperformancemanager.region.api


import com.example.regionperformancemanager.region.model.Region
import retrofit2.http.GET


internal interface RegionNetworkApi {
    @GET("/performance/region_image")
    suspend fun region(): List<Region>
}