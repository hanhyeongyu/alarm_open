package com.example.regionperformancemanager.region.repository

import android.content.Context
import android.content.res.AssetManager
import com.example.regionperformancemanager.region.model.Region
import com.example.template.core.utils.AppJson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

internal class RegionTestRepository @Inject constructor(
    @ApplicationContext
    private val context: Context
): RegionRepository {
    private val regionsFlow: MutableStateFlow<List<Region>> = MutableStateFlow(
        all()
    )

    override val regions: Flow<List<Region>> = regionsFlow

    private fun all(): List<Region> {
        val assetManager: AssetManager = context.resources.assets
        val inputStream= assetManager.open("regions.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        return AppJson.fromJson<List<Region>>(jsonString)
    }
}