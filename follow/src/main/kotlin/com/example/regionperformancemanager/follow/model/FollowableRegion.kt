package com.example.regionperformancemanager.follow.model

import com.example.regionperformancemanager.region.model.Region

data class FollowableRegion(
    val isFollowing: Boolean,
    val region: Region
)