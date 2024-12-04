plugins{
    alias(libs.plugins.template.android.library)
    alias(libs.plugins.template.room)
    alias(libs.plugins.template.hilt)
}

android{
    namespace = "com.example.template.core.database"
}


dependencies {
    implementation(libs.kotlinx.datetime)
}
