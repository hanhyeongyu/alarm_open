package com.example.template.core.error

import kotlinx.serialization.Serializable


@Serializable
sealed class AppError(open val msg: String) : RuntimeException(msg) {
}


/**
 * @suppress
 */
@Target(AnnotationTarget.PROPERTY)
annotation class Description(val value: String)