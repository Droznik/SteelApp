package com.mdrapp.de.data.model.remote.registration

import com.google.gson.annotations.SerializedName

data class FinishRegistrationRequest(
    @SerializedName("per_no") val perNo: String,
    @SerializedName("address") val address: String,
    @SerializedName("postcode") val postCode: Int,
    @SerializedName("city") val city: String,
    @SerializedName("mobile_phone") val mobilePhone: Int,
)