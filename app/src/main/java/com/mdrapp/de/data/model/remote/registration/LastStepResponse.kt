package com.mdrapp.de.data.model.remote.registration

import com.google.gson.annotations.SerializedName
import com.mdrapp.de.data.model.remote.baseResponse.mdr.BaseResponse
import com.mdrapp.de.domain.model.auth.registration.CompanyDataDM
import com.mdrapp.de.domain.model.auth.registration.LastStepDataDM
import com.mdrapp.de.domain.model.auth.registration.LastStepResponseDM

data class LastStepResponse(
    @SerializedName("response")
    val response: LastStepData? = null,
): BaseResponse()

data class LastStepData(
    @SerializedName("token")
    val token: String? = "",
    @SerializedName("is_email_verified")
    val isEmailVerified: Boolean? = false,
    @SerializedName("company")
    val company: CompanyData? = null
)

data class CompanyData(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("title")
    val title: String? = "",
    @SerializedName("address")
    val address: String? = "",
    @SerializedName("city")
    val city: String? = "",
    @SerializedName("postcode")
    val postcode: String? = "",
    @SerializedName("phone_number")
    val phoneNumber: String? = "",
)

fun LastStepResponse.toDomain(): LastStepResponseDM = LastStepResponseDM(
    response = response?.toDomain()
)

fun LastStepData.toDomain(): LastStepDataDM = LastStepDataDM(
    token = token ?: "",
    isEmailVerified = isEmailVerified ?: false,
    company = company?.toDomain()
)

fun CompanyData.toDomain(): CompanyDataDM = CompanyDataDM(
    id = id ?: 0,
    title = title ?: "",
    address = address ?: "",
    city = city ?: "",
    postcode = postcode ?: "",
    phoneNumber = phoneNumber ?: ""
)