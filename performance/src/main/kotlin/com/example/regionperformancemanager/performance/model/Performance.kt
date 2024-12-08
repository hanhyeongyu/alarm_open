package com.example.regionperformancemanager.performance.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Performance(
    val id: Long,
    val eventName: String,

    @SerialName("region")
    val regionId: Long,
    val address: String,
    val location: String,

    val startDate: String,
    val startTime: String,
    val endDate: String,
    val endTime: String,

    val homePageUrl: String,

    val admissionFee: String?,
    val ageLimit: String?,
    val chargeInfo: String?,

    val latitude: Double?,
    val longitude: Double?,
    val seatNumber: String?,
)