package com.mdrapp.de.data.model.remote.registration

import com.google.gson.annotations.SerializedName
import com.mdrapp.de.data.model.remote.baseResponse.mdr.BaseResponse
import com.mdrapp.de.domain.model.auth.registration.EmailConfirmationResponseDM
import com.mdrapp.de.domain.model.auth.registration.EmailVerificationDataDM
import com.mdrapp.de.domain.model.auth.registration.UserDataDM

data class EmailConfirmationResponse(
    @SerializedName("response")
    val response: EmailVerificationData? = null,
): BaseResponse()

data class EmailVerificationData(
    @SerializedName("token")
    val token: String? = "",
    @SerializedName("is_email_verified")
    val isEmailVerified: Boolean? = false,
    @SerializedName("user")
    val user: UserData? = null
)

data class UserData(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("email")
    val email: String? = "",
)

fun EmailConfirmationResponse.toDomain(): EmailConfirmationResponseDM = EmailConfirmationResponseDM(
    response = response?.toDomain()
)

fun EmailVerificationData.toDomain(): EmailVerificationDataDM = EmailVerificationDataDM(
    token = token ?: "",
    isEmailVerified = isEmailVerified ?: false,
    user = user?.toDomain()
)

fun UserData.toDomain(): UserDataDM = UserDataDM(
    id = id ?: 0,
    email = email ?: ""
)