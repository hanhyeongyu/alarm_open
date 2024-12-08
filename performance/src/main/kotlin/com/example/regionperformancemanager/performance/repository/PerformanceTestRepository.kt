package com.example.regionperformancemanager.performance.repository

import android.content.Context
import android.content.res.AssetManager
import com.example.regionperformancemanager.performance.model.Performance
import com.example.template.core.utils.AppJson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PerformanceTestRepository @Inject constructor(
    @ApplicationContext
    private val context: Context
): PerformanceRepository{

    private val performanceFlow: MutableStateFlow<List<Performance>> =
        MutableStateFlow(all())

    override val performances: Flow<List<Performance>> = performanceFlow

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

    private fun all(): List<Performance> {
        val assetManager: AssetManager = context.resources.assets
        val inputStream= assetManager.open("performances.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        return AppJson.fromJson<List<Performance>>(jsonString)
    }

}