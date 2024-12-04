package com.example.template.core.error

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable



data class ApiError(
    val reason: ApiFailureReason,
    val errorInfo: ErrorInfo
): AppError(errorInfo.message)


@Serializable
data class ErrorInfo(
    val errorCode: Int,
    val message: String
)

@Serializable
enum class ApiFailureReason(val errorCode: Int){

    /** 기타 서버 에러 */
    @SerialName("-9999")
    Unknown(-9999),

    /** 기타 서버 에러 */
    @SerialName("-1")
    Internal(-1),

    /** 잘못된 파라미터 */
    @SerialName("-2")
    BadParameter(-2),

    /** 존재하지 않은 엔티티 */
    @SerialName("-3")
    EntityNotFound(-3),

    /** 잘못된 상태값 */
    @SerialName("-4")
    IllegalStatus(-4),

    /** 앱키 또는 토큰이 잘못된 경우. 예) 토큰 만료 */
    @SerialName("-401")
    InvalidAccessToken(-401),

    /** 접근 권한이 없음 */
    @SerialName("-403")
    AccessDenied(-403),
}