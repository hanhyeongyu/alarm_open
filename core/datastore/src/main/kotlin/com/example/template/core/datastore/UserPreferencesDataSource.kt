/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.template.core.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserPreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>,
) {

    val userData = userPreferences.data.map {
        UserData(
            showCompleted = it.showCompleted,
            themeBrand = when (it.themeBrandConfig) {
                null,
                ThemeBrandProto.THEME_BRAND_UNSPECIFIED,
                ThemeBrandProto.UNRECOGNIZED,
                ThemeBrandProto.THEME_BRAND_DEFAULT,
                    -> ThemeBrand.DEFAULT
                ThemeBrandProto.THEME_BRAND_ANDROID -> ThemeBrand.ANDROID
            },
            darkTheme = when (it.darkThemeConfig) {
                null,
                DarkThemeConfigProto.DARK_THEME_CONFIG_UNSPECIFIED,
                DarkThemeConfigProto.UNRECOGNIZED,
                DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM,
                    ->
                    DarkTheme.FOLLOW_SYSTEM
                DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT ->
                    DarkTheme.LIGHT
                DarkThemeConfigProto.DARK_THEME_CONFIG_DARK -> DarkTheme.DARK
            },
            useDynamicColor = it.useDynamicColor,
            authenticated = it.authenticated
        )
    }

    suspend fun setShowCompleted(showCompleted: Boolean) {
        try {
            userPreferences.updateData {
                it.copy { this.showCompleted = showCompleted }
            }
        } catch (ioException: IOException) {
            Log.e("TemplatePreferences", "Failed to update user preferences", ioException)
        }
    }


    suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        userPreferences.updateData {
            it.copy {
                this.themeBrandConfig = when (themeBrand) {
                    ThemeBrand.DEFAULT -> ThemeBrandProto.THEME_BRAND_DEFAULT
                    ThemeBrand.ANDROID -> ThemeBrandProto.THEME_BRAND_ANDROID
                }
            }
        }
    }

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkTheme) {
        userPreferences.updateData {
            it.copy {
                this.darkThemeConfig = when (darkThemeConfig) {
                    DarkTheme.FOLLOW_SYSTEM -> DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM
                    DarkTheme.LIGHT -> DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT
                    DarkTheme.DARK -> DarkThemeConfigProto.DARK_THEME_CONFIG_DARK
                }
            }
        }
    }

    suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        userPreferences.updateData {
            it.copy { this.useDynamicColor = useDynamicColor }
        }
    }

    suspend fun setAuthenticated(){
        userPreferences.updateData {
            it.copy { this.authenticated = true }
        }
    }


}
