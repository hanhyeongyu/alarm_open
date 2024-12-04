package com.example.template.core.datastore

data class UserData(
    val showCompleted: Boolean,
    val themeBrand: ThemeBrand,
    val darkTheme: DarkTheme,
    val useDynamicColor: Boolean,
    val authenticated: Boolean
)

enum class ThemeBrand {
    DEFAULT,
    ANDROID,
}

enum class DarkTheme {
    FOLLOW_SYSTEM,
    LIGHT,
    DARK,
}
