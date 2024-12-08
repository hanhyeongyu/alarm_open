package com.example.regionperformancemanager.region.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Region(
    @SerialName("regionCode")
    val id: Long,

    @SerialName("regionName")
    val name: String,

    val imageUrl: String
)