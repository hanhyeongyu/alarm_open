package com.example.template.core.auth.di

import androidx.tracing.trace
import com.example.template.core.auth.AccessTokenInterceptor
import com.example.template.core.auth.AuthInterceptor
import com.example.template.core.auth.model.Auth
import com.example.template.core.network.BuildConfig
import com.example.template.core.network.di.AuthApi
import com.example.template.core.preference.PreferenceStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AuthModule{
    @AuthApi
    @Provides
    @Singleton
    fun authOkHttpCallFactory(
        authInterceptor: AuthInterceptor,
        accessTokenInterceptor: AccessTokenInterceptor
    ): Call.Factory = trace("AuthOkHttpClient") {
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(accessTokenInterceptor)
            .addInterceptor(
                HttpLoggingInterceptor()
                    .apply {
                        if (BuildConfig.DEBUG) {
                            setLevel(HttpLoggingInterceptor.Level.BODY)
                        }
                    },
            )
            .build()
    }

    @Provides
    @Singleton
    fun authInterceptor(
        preferenceStorage: PreferenceStorage
    ): AuthInterceptor {
        return AuthInterceptor(preferenceStorage)
    }

    @Provides
    @Singleton
    fun accessTokenInterceptor(
        auth : Auth,
        preferenceStorage: PreferenceStorage
    ): AccessTokenInterceptor {
        return AccessTokenInterceptor(auth, preferenceStorage)
    }
}
