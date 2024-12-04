package com.example.template.core.auth.api

import com.example.template.core.auth.model.OAuthToken
import retrofit2.http.Body
import retrofit2.http.POST

internal interface AuthNetworkApi {
    @POST("users/signup")
    suspend fun signup(@Body signup: Signup)

    @POST("users/issue")
    suspend fun issue(@Body issueToken: IssueToken): OAuthToken

    @POST("users/issue/refresh")
    fun refresh(@Body refreshToken: RefreshToken): retrofit2.Call<OAuthToken>
}