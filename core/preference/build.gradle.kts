plugins{
    alias(libs.plugins.template.android.library)
    alias(libs.plugins.template.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android{
    defaultConfig {
        consumerProguardFiles("consumer-proguard-rules.pro")
    }

    namespace = "com.example.template.core.preference"
}

dependencies{
    implementation(projects.core.common)
    implementation(libs.androidx.security.crypto)
    implementation(libs.kotlinx.serialization.json)
}