package com.example.template.core.auth

import com.example.template.core.error.ApiFailureReason
import com.example.template.core.error.ClientError
import com.example.template.core.error.ClientFailureReason
import com.example.template.core.auth.model.Auth
import com.example.template.core.network.utils.Constants
import com.example.template.core.network.utils.ExceptionWrapper
import com.example.template.core.network.utils.proceedApiError
import com.example.template.core.preference.PreferenceKeys
import com.example.template.core.preference.PreferenceStorage
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class AccessTokenInterceptor(
    private val auth: Auth,
    private val preference: PreferenceStorage
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val usedAccessToken = preference[PreferenceKeys.Token]?.accessToken

        val request = usedAccessToken?.let {
            chain.request().withAccessToken(it)
        } ?: throw ExceptionWrapper(ClientError(ClientFailureReason.TokenNotFound))

        return chain.proceedApiError(request) { response, error ->

            // -401 발생시 자동 갱신
            if (error.reason != ApiFailureReason.InvalidAccessToken) {
                return response
            }

            // 나중에 들어온 요청들 pending (중복 갱신 방어)
            synchronized(this) {
                // resume 돼서 들어왔을 때 현재 토큰 보고
                val currentToken = preference[PreferenceKeys.Token] ?: return@synchronized

                val accessToken = if (currentToken.accessToken != usedAccessToken) {
                    // 이전 요청에서 넣었던 토큰과 현재 토큰이 다르면
                    // 이미 앞의 요청에서 갱신됐다고 판단하고, 현재 토큰 사용
                    currentToken.accessToken
                } else {
                    try {
                        // 갱신 요청 이후 토큰 사용
                        auth.refresh(currentToken.refreshToken)!!.refreshToken
                    } catch (e: Throwable) {
                        throw ExceptionWrapper(e)
                    }
                }

//                val requestUrl = request.url.toString()
//                if (requestUrl.contains(com.kakao.sdk.auth.Constants.CHECK_ACCESS_TOKEN)) {
//                    // check_access_token 호출은 API 재시도하지 않음
//                    return@synchronized
//                }

                // 변경된 accessToken으로 API 재시도
                return chain.proceed(request.withAccessToken(accessToken))
            }
            return response
        }
    }
}

/**
 * @suppress
 */
fun Request.withAccessToken(accessToken: String) =
    newBuilder().removeHeader(Constants.AUTHORIZATION)
        .addHeader(Constants.AUTHORIZATION, "${Constants.BEARER} $accessToken").build()
