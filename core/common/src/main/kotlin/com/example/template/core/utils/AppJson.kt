package com.example.template.core.utils

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object AppJson {

    val json = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    inline fun <reified T> toJson(model: T): String {
        return try {
            json.encodeToString(model)
        } catch (e: Exception) {
            throw IllegalArgumentException("Serialization error: ${e.message}", e)
        }
    }

    inline fun <reified T> fromJson(string: String): T {
        return try {
            json.decodeFromString(string)
        } catch (e: Exception) {
            throw IllegalArgumentException("Failed to parse JSON string: ${e.message}", e)
        }
    }
}
