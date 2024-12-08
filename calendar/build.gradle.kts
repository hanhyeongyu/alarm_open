plugins {
    alias(libs.plugins.template.android.feature)
    alias(libs.plugins.template.android.library.compose)
    alias(libs.plugins.template.hilt)
}


android{
    namespace = "com.example.regionperformancemanager.calendar"
}

dependencies{
    implementation(projects.core.common)
    implementation(projects.core.network)
    implementation(projects.core.auth)
    implementation(projects.core.datastore)

    implementation(libs.kotlinx.datetime)

    implementation("com.kizitonwose.calendar:view:2.6.0")
    implementation("com.kizitonwose.calendar:compose:2.6.0")
}