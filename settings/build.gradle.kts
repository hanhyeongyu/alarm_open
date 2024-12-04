plugins {
    alias(libs.plugins.template.android.feature)
    alias(libs.plugins.template.android.library.compose)
    alias(libs.plugins.template.hilt)
}

android{
    namespace = "com.example.template.settings"
}

dependencies{
    implementation(projects.core.datastore)

    implementation(libs.androidx.appcompat)
    implementation(libs.google.oss.licenses)
}