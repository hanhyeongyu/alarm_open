package com.example.regionperformancemanager.foryou.model

import com.example.regionperformancemanager.performance.model.Performance

class Feed(
    val performance: Performance,
    val isBookmark: Boolean
){
    var id: Long = performance.id
}