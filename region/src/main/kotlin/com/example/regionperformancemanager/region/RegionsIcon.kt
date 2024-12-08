package com.example.regionperformancemanager.region

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.template.core.designsystem.component.DynamicAsyncImage

@Composable
fun RegionsIcon(imageUrl: String, modifier: Modifier = Modifier) {
    DynamicAsyncImage(
        imageUrl = imageUrl,
        contentDescription = null,
        modifier = modifier,
    )
}
