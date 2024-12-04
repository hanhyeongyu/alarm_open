package com.example.template.core.auth.model

import android.util.Log
import androidx.tracing.trace
import com.example.template.core.auth.Token
import com.example.template.core.auth.api.AuthNetworkApi
import com.example.template.core.auth.api.IssueToken
import com.example.template.core.auth.api.RefreshToken
import com.example.template.core.auth.api.Signup
import com.example.template.core.datastore.UserPreferencesDataSource
import com.example.template.core.network.BuildConfig
import com.example.template.core.network.di.Api
import com.example.template.core.preference.PreferenceKeys
import com.example.template.core.preference.PreferenceStorage
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

private const val AUTH_BASE_URL = BuildConfig.AUTH_URL

@Singleton
class Auth @Inject constructor(
    networkJson: Json,
    @Api okhttpCallFactory: dagger.Lazy<Call.Factory>,
    val userPreferencesDataSource: UserPreferencesDataSource,
    val preferenceStorage: PreferenceStorage
){
    private val authApi =  trace("AuthAPI"){
        Retrofit.Builder()
            .baseUrl(AUTH_BASE_URL)
            .callFactory{ okhttpCallFactory.get().newCall(it)}
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(AuthNetworkApi::class.java)
    }

    suspend fun signup(email: String, password: String){
        authApi.signup(Signup(email, password))
    }

    suspend fun signIn(email: String, password: String): OAuthToken {
        try{
            val oauthToken = authApi.issue(IssueToken(email, password))
            preferenceStorage[PreferenceKeys.Token] = oauthToken
            userPreferencesDataSource.setAuthenticated()
            return oauthToken
        }catch (e: Exception){
            Log.e("Auth", e.toString())
            throw e
        }

    }

    fun refresh(refreshToken: String): OAuthToken?{
        val oauthToken = authApi.refresh(RefreshToken(refreshToken)).execute().body()
        if (oauthToken == null){
            return null
        }
        preferenceStorage[PreferenceKeys.Token] = oauthToken
        return oauthToken
    }
}
