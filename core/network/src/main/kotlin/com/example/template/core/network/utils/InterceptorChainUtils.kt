package com.example.template.core.network.utils

import com.example.template.core.error.ApiError
import com.example.template.core.error.ApiFailureReason
import com.example.template.core.error.ErrorInfo
import com.example.template.core.utils.AppJson
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

inline fun Interceptor.Chain.proceedBodyString(
    request: Request,
    bodyHandler: (response: Response, bodyString: String?) -> Response,
): Response {
    val originalResponse = proceed(request)
    val body = originalResponse.body
    val bodyString = body?.string()

    // 에러 응답 파싱을 위해 body를 한번 소비했기 때문에 새로 만들어주지 않으면 다른 인터셉터에서 body 접근 시 크래시 난다.
    val newResponse =
        originalResponse.newBuilder().body(bodyString?.toResponseBody(body.contentType())).build()

    bodyString?.toResponseBody(body.contentType())
    return bodyHandler(newResponse, bodyString)
}

/**
 * @suppress
 */
inline fun Interceptor.Chain.proceedApiError(
    request: Request,
    errorHandler: (response: Response, error: ApiError) -> Response,
): Response = proceedBodyString(request) { response, bodyString ->
    if (!response.isSuccessful) {
        val errorInfo = bodyString?.let {
            AppJson.fromJson<ErrorInfo>(it)
        }

        val apiFailureReason = errorInfo?.let {
            AppJson.fromJson<ApiFailureReason>(it.errorCode.toString())
        }

        if(apiFailureReason != null){
            return errorHandler(response, ApiError(apiFailureReason, errorInfo))
        }
    }
    return response
}