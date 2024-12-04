plugins {
    alias(libs.plugins.template.android.library)
    alias(libs.plugins.template.android.library.compose)
}

android{
    namespace = "com.example.template.core.ui"
}

dependencies{
    implementation(projects.core.log)
    api(projects.core.designsystem)


    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)
//    api(libs.androidx.compose.foundation)
//    api(libs.androidx.compose.foundation.layout)
//    api(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.compose.material3)
//    api(libs.androidx.compose.material3.adaptive)
//    api(libs.androidx.compose.material3.navigationSuite)
//    api(libs.androidx.compose.runtime)
//    api(libs.androidx.compose.ui.util)

    implementation(libs.kotlinx.datetime)
}