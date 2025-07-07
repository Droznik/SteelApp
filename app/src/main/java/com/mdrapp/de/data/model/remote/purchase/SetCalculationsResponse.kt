package com.mdrapp.de.data.model.remote.purchase

import com.google.gson.annotations.SerializedName
import com.mdrapp.de.data.model.remote.baseResponse.mdr.BaseResponse
import com.mdrapp.de.domain.model.CalculationsResultDM


data class SetCalculationsResponse(
    @SerializedName("response") val response: CalculationsResponse? = null
): BaseResponse()

data class CalculationsResponse(
    @SerializedName("leasingRate") val leasingRate: Double? = null,
    @SerializedName("totalPurchasePrice") val totalPurchasePrice: Double? = null,
    @SerializedName("insuranceRate") val insuranceRate: Double? = null,
)

fun SetCalculationsResponse.toDM(): CalculationsResultDM = CalculationsResultDM(
    leasingRate = response?.leasingRate,
    totalPurchasePrice = response?.totalPurchasePrice,
    insuranceRate = response?.insuranceRate,
)