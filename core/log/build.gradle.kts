plugins{
    alias(libs.plugins.template.android.library)
    alias(libs.plugins.template.hilt)
}

android{
    namespace = "com.example.template.core.log"

    buildFeatures {
        buildConfig = true
    }
}


dependencies{
    api(libs.timber)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
}
