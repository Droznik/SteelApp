package com.mdrapp.de.data.model.remote.resetPassword

import com.google.gson.annotations.SerializedName
import com.mdrapp.de.data.model.remote.baseResponse.mdr.BaseResponse
import com.mdrapp.de.domain.model.auth.resetPassword.ResetPasswordDataDM

data class ResetPasswordResponse(
    @SerializedName("response") val resetPasswordData: ResetPasswordData
) : BaseResponse()

data class ResetPasswordData(
    @SerializedName("email_verified_at") val isEmailVerified: Boolean? = null
) : BaseResponse()

fun ResetPasswordResponse.toDM(): ResetPasswordDataDM {
    return ResetPasswordDataDM(
        isEmailVerified = this.resetPasswordData.isEmailVerified ?: false,
    )
}