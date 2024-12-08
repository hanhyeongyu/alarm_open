package com.example.regionperformancemanager.performance.api

import com.example.regionperformancemanager.performance.model.Performance
import retrofit2.http.GET

internal interface PerformanceNetworkApi {
    @GET("/performance/all")
    suspend fun performance(): List<Performance>
}