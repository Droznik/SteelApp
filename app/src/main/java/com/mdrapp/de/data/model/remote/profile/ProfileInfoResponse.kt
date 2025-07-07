package com.mdrapp.de.data.model.remote.profile

import com.google.gson.annotations.SerializedName
import com.mdrapp.de.data.model.remote.baseResponse.mdr.BaseResponse
import com.mdrapp.de.domain.model.ProfileDM

data class ProfileInfoResponse(
    @SerializedName("response")
    val response: ProfileData? = null,
): BaseResponse()

data class ProfileData(
    @SerializedName("daten")
    val userInfo: UserProfileData? = null,
    @SerializedName("Dienstradmodell")
    val companyBikeData: CompanyBikeData? = null,
)

data class UserProfileData(
    @SerializedName("Arbeitgeber")
    val employer: String? = "",
    @SerializedName("Name")
    val name: String? = "",
    @SerializedName("email")
    val email: String? = "",
    @SerializedName("Telefonnummer")
    val phone: String? = "",
    @SerializedName("Adresse")
    val address: String? = "",
    @SerializedName("Personal_number")
    val personalNumber: String? = "",
    @SerializedName("Land")
    val country: String? = "",
)

data class CompanyBikeData(
    @SerializedName("Erlaubte")
    val allowed: String? = "",
    @SerializedName("MinMaxPrice")
    val minMaxPrice: String? = "",
    @SerializedName("Grundlage")
    val basis: String? = "",
    @SerializedName("Preisbegrenzung")
    val priceLimit: String? = "",
    @SerializedName("Anzahl")
    val quantity: String? = "",
    @SerializedName("Arbeitgeberzuschüsse")
    val employerSubsidies: String? = "",
    @SerializedName("Leasingfähiges")
    val leasable: String? = "",
)

fun ProfileInfoResponse.toDomain(): ProfileDM = ProfileDM(
    employer = response?.userInfo?.employer ?: "",
    name = response?.userInfo?.name ?: "",
    email = response?.userInfo?.email ?: "",
    phone = response?.userInfo?.phone ?: "",
    address = response?.userInfo?.address ?: "",
    personalNumber = response?.userInfo?.personalNumber ?: "",
    country = response?.userInfo?.country ?: "",
    allowed = response?.companyBikeData?.allowed ?: "",
    minMaxPrice = response?.companyBikeData?.minMaxPrice ?: "",
    basis = response?.companyBikeData?.basis ?: "",
    priceLimit = response?.companyBikeData?.priceLimit ?: "",
    quantity = response?.companyBikeData?.quantity ?: "",
    employerSubsidies = response?.companyBikeData?.employerSubsidies ?: "",
    leasable = response?.companyBikeData?.leasable ?: "",
)