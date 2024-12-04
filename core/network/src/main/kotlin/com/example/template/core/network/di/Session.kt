package com.example.template.core.network.di

import jakarta.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Api

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthApi