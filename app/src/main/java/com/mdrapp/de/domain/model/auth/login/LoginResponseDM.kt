package com.mdrapp.de.domain.model.auth.login

data class LoginDataDM(
    val isFinishedRegistration: Boolean,
    val isEmailVerified: Boolean,
    val accessToken: String,
    val token: String
)