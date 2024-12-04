plugins {
    alias(libs.plugins.template.android.library)
    alias(libs.plugins.template.hilt)
}

android{
    namespace = "com.example.tempalte.core.notification"
}

dependencies{
    implementation(projects.core.common)

    compileOnly(platform(libs.androidx.compose.bom))
}