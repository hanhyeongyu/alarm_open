package com.example.template.core.preference

import android.content.SharedPreferences
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton



typealias PrefKey<Value> = PreferenceStorage.Key<Value>
object PreferenceKeys {
}

@Singleton
class PreferenceStorage @Inject constructor(
    val preferences: SharedPreferences,
    val encrypted: SharedPreferences
){

    private val editor by lazy { preferences.edit() }

    data class Key<Value>(
        val name: String,
        val defaultValue: Value,
        val encrypted: Boolean
    )


    fun clear() {
        editor.clear()
        editor.apply()
    }


    operator fun get(key: Key<String>): String {
        return getString(key) ?: key.defaultValue
    }

    private fun getString(key: Key<String>): String? {
        return if (key.encrypted) {
            encrypted.getString(key.name, key.defaultValue)
        } else {
            preferences.getString(key.name, key.defaultValue)
        }
    }


    inline operator fun<reified T> get(key: Key<T>): T{
        return getObject(key) ?: key.defaultValue
    }


    inline fun<reified T> getObject(key: Key<T>): T? {
        val json = if (key.encrypted) {
            encrypted.getString(key.name, null)
        } else {
            preferences.getString(key.name, null)
        }

        if(json == null){
            return null
        }else{
            return Json.decodeFromString<T>(json)
        }
    }



    operator fun set(key: Key<String>, value: String) {
        val json = Json.encodeToString(value)

        if (key.encrypted) {
            encrypted.edit().putString(key.name, json).apply()
        } else {
            preferences.edit().putString(key.name, json).apply()
        }
    }

    inline operator fun<reified T> set(key: Key<T>, value: T) {
        val json = Json.encodeToString(value)

        if (key.encrypted) {
            encrypted.edit().putString(key.name, json).apply()
        } else {
            preferences.edit().putString(key.name, json).apply()
        }
    }




    companion object {
        internal const val PREFERENCES_NAME = "com.example.template"
        internal const val ENCRYPTED_FILE_NAME = "encrypted_settings"
    }
}

@Serializable
data class Test(
    val data: Int
)