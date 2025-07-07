package com.mdrapp.de.data.model.remote.registration

import com.google.gson.annotations.SerializedName
import com.mdrapp.de.data.model.remote.baseResponse.mdr.BaseResponse
import com.mdrapp.de.domain.model.auth.registration.RegistrationResponseDM

data class RegistrationResponse(
    @SerializedName("response")
    val response: TokenData? = null,
): BaseResponse()

data class TokenData(
    @SerializedName("token")
    val token: String? = ""
)

fun RegistrationResponse.toDomain(): RegistrationResponseDM = RegistrationResponseDM(
    success = success?:false,
    token = response?.token?:"",
    locationIdError = errors?.get("location_id")?.joinToString(", ") ?: "",
    usernameError = errors?.get("name")?.joinToString(", ") ?: "",
    emailError = errors?.get("email")?.joinToString(", ") ?: "",
    passwordError = errors?.get("password")?.joinToString(", ") ?: "",
    passwordConfirmationError = errors?.get("password_confirmation")?.joinToString(", ") ?: "",
    firstNameError = errors?.get("first_name")?.joinToString(", ") ?: "",
    lastNameError = errors?.get("last_name")?.joinToString(", ") ?: "",
    genderError = errors?.get("gender")?.joinToString(", ") ?: "",
    termsError = errors?.get("terms")?.joinToString(", ") ?: ""
)

