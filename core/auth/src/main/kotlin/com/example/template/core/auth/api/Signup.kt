package com.example.template.core.auth.api

import kotlinx.serialization.Serializable

@Serializable
data class Signup(
    val email: String,
    val password: String
)