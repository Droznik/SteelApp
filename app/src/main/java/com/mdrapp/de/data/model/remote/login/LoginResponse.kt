package com.mdrapp.de.data.model.remote.login

import com.google.gson.annotations.SerializedName
import com.mdrapp.de.data.model.remote.baseResponse.mdr.BaseResponse
import com.mdrapp.de.domain.model.auth.login.LoginDataDM

data class LoginResponse(
    @SerializedName("response") val loginData: LoginData
) : BaseResponse()

data class LoginData(
    @SerializedName("finished_registration") val isFinishedRegistration: Boolean? = null,
    @SerializedName("email_verified_at") val isEmailVerified: Boolean? = null,
    @SerializedName("access_token") val accessToken: String? = null,
    @SerializedName("token") val token: String? = null,
)

fun LoginResponse.toDM(): LoginDataDM {
    return LoginDataDM(
        isFinishedRegistration = this.loginData.isFinishedRegistration ?: false,
        isEmailVerified = this.loginData.isEmailVerified ?: false,
        accessToken = this.loginData.accessToken ?: "",
        token = this.loginData.token ?: ""
    )
}