package com.mdrapp.de.domain.model

data class MdrOrderItemDM(
    val id: Long,
    val orderNumber: String?,
    val name: String?,
    val servicePackage: String?,
    val leasingEnd: String?,
    val mdrStatus: String?,
    val dealer: String?
)

data class MdrOrderDetailDM(
    val id: Long,
    val number: String?,
    val bikeName: String?,
    val mdrStatus: String?,
    val servicePackage: String?,
    val leasingEnd: String?,
    val dealerName: String?,
    val bike: MdrOrderBikeDM?,
    val accessories: List<MdrOrderAccessoryDM>?,
    val prices: MdrOrderPricesDM?,
    val dealer: MdrOrderDealerDM?,
    val calculation: MdrOrderCalculationDM?,
    val order: MdrOrderInfoDM?,
    val docs: List<MdrOrderDocDM>?,
    val allowedToUpload: Boolean?,
    val trackingStatuses: List<MdrOrderTrackingStatusDM>?,
)

data class MdrOrderBikeDM(
    val model: String?,
    val type: String?,
    val brand: String?,
    val frame: String?,
    val size: String?,
    val color: String?,
    val category: String?
)

data class MdrOrderAccessoryDM(
    val id: Long,
    val name: String?,
    val quantity: Int?,
    val uvp: Double?,
    val net: Double?,
    val gross: Double?,
)

data class MdrOrderPricesDM(
    val net: Double?,
    val gross: Double?,
    val uvp: Double?,
)

data class MdrOrderDealerDM(
    val name: String?,
    val address: String?,
)

data class MdrOrderCalculationDM(
    val leasingRate: Double?,
    val insuranceRate: Double?,
    val serviceRate: Double?,
    val totalAgRate: Double?,
    val totalAnRate: Double?,
    val taxRate: Double?,
    val benefit: Double?,
)

data class MdrOrderInfoDM(
    val leasingCompany: String?,
    val insuranceTariff: String?,
    val servicePackage: String?,
    val deliveryDate: String?,
    val leasingDuration: Int?,
    val leasingEnd: String?,
)

data class MdrOrderDocDM(
    val id: Long,
    val name: String?,
    val allowedToOpen: Boolean?,
    val employerNote: String?,
)

data class MdrOrderTrackingStatusDM(
    val value: String?,
    val label: String?,
    val description: String?,
    val active: Boolean?,
)
