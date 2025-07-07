package com.mdrapp.de.data.model.remote.mdrOrders

import com.google.gson.annotations.SerializedName
import com.mdrapp.de.data.model.remote.baseResponse.mdr.BaseResponse
import com.mdrapp.de.domain.model.MdrOrderItemDM

data class GetMdrOrdersResponse(
    @SerializedName("response") val response: MdrOrdersResponse? = null
) : BaseResponse()

data class MdrOrdersResponse(
    @SerializedName("bikes") val orders: List<MdrOrderItem>? = null
)

data class MdrOrderItem(
    @SerializedName("id") val id: Long,
    @SerializedName("number") val orderNumber: String? = null,
    @SerializedName("title") val name: String? = null,
    @SerializedName("service_package") val servicePackage: String? = null,
    @SerializedName("leasing_end") val leasingEnd: String? = null,
    @SerializedName("status") val mdrStatus: String? = null,
    @SerializedName("creditor") val dealer: String? = null,
)

fun GetMdrOrdersResponse.toDomain(): List<MdrOrderItemDM> = response?.orders?.map { it.toDomain() }.orEmpty()

fun MdrOrderItem.toDomain(): MdrOrderItemDM = MdrOrderItemDM(
    id = id,
    orderNumber = orderNumber,
    name = name,
    servicePackage = servicePackage,
    leasingEnd = leasingEnd,
    mdrStatus = mdrStatus,
    dealer = dealer,
)