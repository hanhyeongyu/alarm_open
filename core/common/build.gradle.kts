plugins{
    alias(libs.plugins.template.jvm.library)
    alias(libs.plugins.template.hilt)
    alias(libs.plugins.kotlin.serialization)
}

dependencies{
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)
    testImplementation(libs.kotlinx.coroutines.test)
}