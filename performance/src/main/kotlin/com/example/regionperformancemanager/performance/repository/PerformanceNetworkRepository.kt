package com.example.regionperformancemanager.performance.repository

import androidx.tracing.trace
import com.example.regionperformancemanager.performance.api.PerformanceNetworkApi
import com.example.regionperformancemanager.performance.model.Performance
import com.example.template.core.network.BuildConfig
import com.example.template.core.network.di.Api
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Inject


private const val PERFORMANCE_BASE_URL = BuildConfig.PERFORMANCE_URL


class PerformanceNetworkRepository @Inject constructor(
    networkJson: Json,
    @Api okhttpCallFactory: dagger.Lazy<Call.Factory>
): PerformanceRepository{

    private val performanceApi =  trace("PerformanceAPI"){
        Retrofit.Builder()
            .baseUrl(PERFORMANCE_BASE_URL)
            .callFactory{ okhttpCallFactory.get().newCall(it)}
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(PerformanceNetworkApi::class.java)
    }

    private val performanceFlow: MutableStateFlow<List<Performance>> =
        MutableStateFlow(emptyList())

    override val performances: Flow<List<Performance>> = performanceFlow

    init {
        GlobalScope.launch {
            try {
                val regionsList = performances()
                performanceFlow.emit(regionsList)
            } catch (e: Exception) {
                // Handle exceptions appropriately
                e.printStackTrace()
            }
        }
    }

    override fun performances(performanceIds: Set<String>): Flow<List<Performance>> =
        performances.map { list ->
            list.filter { performance ->
                performanceIds.contains(performance.id.toString())
            }
        }

    override fun performancesByRegionIds(regionIds: Set<String>): Flow<List<Performance>> =
        performances.map { list ->
            list.filter { performance ->
                regionIds.contains(performance.regionId.toString())
            }
        }

    private suspend fun performances(): List<Performance> = performanceApi.performance()
}