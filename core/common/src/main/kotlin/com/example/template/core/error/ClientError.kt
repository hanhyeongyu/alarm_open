package com.example.template.core.error

import kotlinx.serialization.Serializable

@Serializable
data class ClientError(
    val reason: ClientFailureReason,
    val errorDescription: String = reason.javaClass.getField(reason.name)
        .getAnnotation(Description::class.java)?.value
        ?: "Client-side error",
): AppError(errorDescription)


@Serializable
enum class ClientFailureReason{
    /// 기타 에러
    @Description("unknown error.")
    Unknown,

    /// 사용자의 취소 액션 등
    @Description("user cancelled.")
    Cancelled,

    /// API 요청에 사용할 토큰이 없음
    @Description("authentication tokens don't exist.")
    TokenNotFound,

    /// 지원되지 않는 기능
    @Description("not supported")
    NotSupported,

    /// 잘못된 파라미터
    @Description("bad parameters.")
    BadParameter,

    /// type casting 실패
    @Description("illegal state.")
    CastingFailed
}