package com.mdrapp.de.data.model.remote.resendMail

import com.google.gson.annotations.SerializedName
import com.mdrapp.de.data.model.remote.baseResponse.mdr.BaseResponse
import com.mdrapp.de.domain.model.auth.resendMail.ResendMailDataDM

data class ResendMailResponse(
    @SerializedName("response") val resendMailData: ResendMailData
) : BaseResponse()

data class ResendMailData(
    @SerializedName("email_verified_at") val isEmailVerified: Boolean? = null
) : BaseResponse()

fun ResendMailResponse.toDM(): ResendMailDataDM {
    return ResendMailDataDM(
        isEmailVerified = this.resendMailData.isEmailVerified
    )
}