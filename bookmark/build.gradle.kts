plugins {
    alias(libs.plugins.template.android.feature)
    alias(libs.plugins.template.android.library.compose)
    alias(libs.plugins.template.hilt)
}
android{
    namespace = "com.example.regionperformancemanager.bookmark"
}

dependencies{
    implementation(projects.core.common)
    implementation(projects.core.datastore)

    implementation(projects.performance)
    implementation(projects.favorite)
}