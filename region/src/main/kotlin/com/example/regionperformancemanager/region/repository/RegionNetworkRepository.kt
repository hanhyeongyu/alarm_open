package com.example.regionperformancemanager.region.repository

import androidx.tracing.trace
import com.example.regionperformancemanager.region.api.RegionNetworkApi
import com.example.regionperformancemanager.region.model.Region
import com.example.template.core.network.BuildConfig
import com.example.template.core.network.di.Api
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Inject

private const val REGION_BASE_URL = BuildConfig.REGION_URL

class RegionNetworkRepository @Inject constructor(
    networkJson: Json,
    @Api okhttpCallFactory: dagger.Lazy<Call.Factory>,
): RegionRepository {
    private val regionApi = trace("RegionAPI") {
        Retrofit.Builder()
            .baseUrl(REGION_BASE_URL)
            .callFactory { okhttpCallFactory.get().newCall(it) }
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(RegionNetworkApi::class.java)
    }

    private val regionsFlow: MutableStateFlow<List<Region>> = MutableStateFlow(
        emptyList()
    )

    init {
        GlobalScope.launch {
            try {
                val regionsList = regions()
                regionsFlow.emit(regionsList)
            } catch (e: Exception) {
                // Handle exceptions appropriately
                e.printStackTrace()
            }
        }
    }

    override val regions: Flow<List<Region>> = regionsFlow

    private suspend fun regions(): List<Region> = regionApi.region()
}