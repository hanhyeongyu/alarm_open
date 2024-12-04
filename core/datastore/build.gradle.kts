plugins{
    alias(libs.plugins.template.android.library)
    alias(libs.plugins.template.hilt)
}

android{
    defaultConfig {
        consumerProguardFiles("consumer-proguard-rules.pro")
    }

    namespace = "com.example.template.core.datastore"
}

dependencies{
    implementation(projects.core.common)
    implementation(projects.core.datastoreProto)

    implementation(libs.androidx.dataStore)
}