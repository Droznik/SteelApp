package com.mdrapp.de.ui.auth.login.forgotPassword

data class ForgotPasswordState(
    val email: String = "",
    val isEmailVerifiedDialogVisible: Boolean = false
)

sealed interface ForgotPasswordEvent {
    data class OnEmailChange(val value: String): ForgotPasswordEvent
    data object OnResetPasswordBtnClick : ForgotPasswordEvent
    data object OnCloseDialogBtnCLicked : ForgotPasswordEvent
    data object OnVerifyEmailBtnClicked : ForgotPasswordEvent
    data object OnLoginBtnClicked : ForgotPasswordEvent
}