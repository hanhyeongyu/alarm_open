package com.example.template.core.ui

import androidx.compose.runtime.staticCompositionLocalOf
import com.example.template.core.log.analytics.AnalyticsHelper
import com.example.template.core.log.analytics.NoOpAnalyticsHelper


/**
 * Global key used to obtain access to the AnalyticsHelper through a CompositionLocal.
 */
val LocalAnalyticsHelper = staticCompositionLocalOf<AnalyticsHelper> {
    // Provide a default AnalyticsHelper which does nothing. This is so that tests and previews
    // do not have to provide one. For real app builds provide a different implementation.
    NoOpAnalyticsHelper()
}
