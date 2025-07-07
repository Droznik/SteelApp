package com.mdrapp.de.data.model.remote.serviceCase

import com.google.gson.annotations.SerializedName
import com.mdrapp.de.data.model.remote.baseResponse.mdr.BaseResponse
import com.mdrapp.de.domain.model.ServiceCaseDM
import com.mdrapp.de.domain.model.ServiceCaseListDM

data class ServiceCaseListResponse(
    @SerializedName("response")
    val response: ServiceCaseData
): BaseResponse()

data class ServiceCaseData(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("active")
    val active: ActiveCases? = null,
    @SerializedName("Inactive")
    val inactive: InactiveCases? = null
)

data class ActiveCases(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("list")
    val list: List<ServiceOrder>? = null
)

data class InactiveCases(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("list")
    val list: List<ServiceOrder>? = null
)

data class ServiceCase(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("Serviceart")
    val serviceType: String? = null,
    @SerializedName("Status")
    val status: String? = null,
    @SerializedName("Zeitraum")
    val timePeriod: String? = null
)

data class ServiceOrder(
    @SerializedName("orderId") val orderId: Int,
    @SerializedName("count") val count: Int,
    @SerializedName("orderNumber") val orderNumber: String,
    @SerializedName("serviceCases") val serviceCases: List<ServiceCase>
)

fun ServiceCaseListResponse.toDomain(): ServiceCaseListDM {
    return ServiceCaseListDM(
        count = this.response.count?:0,
        activeCases = this.response.active?.list?.map { it.toDomain() }.orEmpty(),
        inactiveCases = this.response.inactive?.list?.map { it.toDomain() }.orEmpty()
    )
}

fun ServiceOrder.toDomain(): com.mdrapp.de.domain.model.ServiceOrderDM {
    return com.mdrapp.de.domain.model.ServiceOrderDM(
        orderId = this.orderId,
        count = this.count,
        orderNumber = this.orderNumber,
        serviceCases = this.serviceCases.map { it.toDomain() }
    )
}

fun ServiceCase.toDomain(): ServiceCaseDM {
    return ServiceCaseDM(
        id = this.id ?: 0,
        serviceType = this.serviceType?:"",
        status = this.status?:"",
        timePeriod = this.timePeriod?:""
    )
}