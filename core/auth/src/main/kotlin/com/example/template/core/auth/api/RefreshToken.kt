package com.example.template.core.auth.api

import kotlinx.serialization.Serializable

@Serializable
data class RefreshToken(
    val refreshToken: String
)