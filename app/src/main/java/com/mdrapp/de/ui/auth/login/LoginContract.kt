package com.mdrapp.de.ui.auth.login

import com.mdrapp.de.BuildConfig

data class LoginState(
    val email: String = if (BuildConfig.DEBUG) "carmelademo@baronmobil.com" else "",
    val password: String = if (BuildConfig.DEBUG) "uyt!arb7jtg_uec9AXW" else "",
    val isEmailVerifiedDialogVisible: Boolean = false
    )

sealed interface LoginEvent {
    data class OnEmailChange(val value: String) : LoginEvent
    data class OnPasswordChange(val value: String) : LoginEvent
    data object OnForgotPasswordBtnClicked : LoginEvent
    data object OnLoginBtnClicked : LoginEvent
    data object OnCloseDialogBtnCLicked : LoginEvent
    data object OnVerifyEmailBtnClicked : LoginEvent
    data object OnRegistrationBtnClicked : LoginEvent
}