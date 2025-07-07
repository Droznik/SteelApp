package com.mdrapp.de.data.model.remote.registration

import com.google.gson.annotations.SerializedName
import com.mdrapp.de.data.model.remote.baseResponse.mdr.BaseResponse
import com.mdrapp.de.domain.model.auth.registration.FinishRegistrationResponseDM
import com.mdrapp.de.domain.model.auth.registration.RegistrationDataDM

data class FinishRegistrationResponse(
    @SerializedName("response")
    val response: RegistrationData? = null,
): BaseResponse()

data class RegistrationData(
    @SerializedName("is_email_verified")
    val isEmailVerified: Boolean? = false,
    @SerializedName("finished_registration")
    val finishedRegistration: Boolean? = false,
    @SerializedName("access_token")
    val accessToken: String? = null
)

fun FinishRegistrationResponse.toDomain(): FinishRegistrationResponseDM {
    errors?.get("error")?.filterNotNull()?.joinToString()?.let { throw Exception(it) }
    return FinishRegistrationResponseDM(
        response = response?.toDomain(),
        success = success ?: false,
        addressError = errors?.get("address")?.filterNotNull()?.joinToString()?: "",
        cityError = errors?.get("city")?.filterNotNull()?.joinToString()?: "",
        perNoError = errors?.get("per_no")?.filterNotNull()?.joinToString()?: "",
        phoneError = errors?.get("mobile_phone")?.filterNotNull()?.joinToString()?: "",
        postCodeError = errors?.get("postcode")?.filterNotNull()?.joinToString()?: ""
    )
}

fun RegistrationData.toDomain(): RegistrationDataDM = RegistrationDataDM(
    isEmailVerified = isEmailVerified ?: false,
    finishedRegistration = finishedRegistration ?: false,
    accessToken = accessToken ?: ""
)