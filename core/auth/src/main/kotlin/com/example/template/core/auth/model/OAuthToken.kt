package com.example.template.core.auth.model

import kotlinx.serialization.Serializable

@Serializable
data class OAuthToken(
   val tokenType: String,
   val accessToken: String,
   val expiresIn: Long,
   val refreshToken: String,
   val refreshExpiresIn: Long
)