package com.mdrapp.de.data.model.remote.mdrOrders

import com.google.gson.annotations.SerializedName
import com.mdrapp.de.data.model.remote.baseResponse.mdr.BaseResponse
import com.mdrapp.de.domain.model.MdrOrderAccessoryDM
import com.mdrapp.de.domain.model.MdrOrderBikeDM
import com.mdrapp.de.domain.model.MdrOrderCalculationDM
import com.mdrapp.de.domain.model.MdrOrderDealerDM
import com.mdrapp.de.domain.model.MdrOrderDetailDM
import com.mdrapp.de.domain.model.MdrOrderDocDM
import com.mdrapp.de.domain.model.MdrOrderInfoDM
import com.mdrapp.de.domain.model.MdrOrderPricesDM
import com.mdrapp.de.domain.model.MdrOrderTrackingStatusDM

data class GetMdrOrderDetailResponse(
    @SerializedName("response") val response: MdrOrderDetailResponse? = null
) : BaseResponse()

data class MdrOrderDetailResponse(
    @SerializedName("bike") val bike: MdrOrderDetailBike? = null,
)

data class MdrOrderDetailBike(
    @SerializedName("id") val id: Long,
    @SerializedName("number") val number: String? = null,
    @SerializedName("title") val bikeName: String? = null,
    @SerializedName("status") val mdrStatus: String? = null,
    @SerializedName("service_package") val servicePackage: String? = null,
    @SerializedName("leasing_end") val leasingEnd: String? = null,
    @SerializedName("creditor_name") val dealerName: String? = null,
    @SerializedName("bike") val bike: MdrOrderBike? = null,
    @SerializedName("accessories") val accessories: List<MdrOrderAccessory>? = null,
    @SerializedName("price") val prices: MdrOrderPrices? = null,
    @SerializedName("creditor") val dealer: MdrOrderDealer? = null,
    @SerializedName("calculation") val calculation: MdrOrderCalculation? = null,
    @SerializedName("order") val order: MdrOrderInfo? = null,
    @SerializedName("docs") val docs: List<MdrOrderDoc>? = null,
    @SerializedName("allowed_to_upload_docs") val allowedToUpload: Boolean? = null,
    @SerializedName("status_info") val trackingStatuses: List<MdrOrderTrackingStatus>? = null,
)

data class MdrOrderBike(
    @SerializedName("model") val model: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("brand") val brand: String? = null,
    @SerializedName("frame") val frame: String? = null,
    @SerializedName("size") val size: String? = null,
    @SerializedName("color") val color: String? = null,
    @SerializedName("category") val category: String? = null,
)

data class MdrOrderAccessory(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String? = null,
    @SerializedName("quantity") val quantity: Int? = null,
    @SerializedName("uvp") val uvp: Double? = null,
    @SerializedName("nett_price") val net: Double? = null,
    @SerializedName("gross_price") val gross: Double? = null,
)

data class MdrOrderPrices(
    @SerializedName("nett") val net: Double? = null,
    @SerializedName("gross") val gross: Double? = null,
    @SerializedName("uvp") val uvp: Double? = null,
)

data class MdrOrderDealer(
    @SerializedName("name") val name: String? = null,
    @SerializedName("address") val address: String? = null,
)

data class MdrOrderCalculation(
    @SerializedName("leasing_rate") val leasingRate: Double? = null,
    @SerializedName("insurance_rate") val insuranceRate: Double? = null,
    @SerializedName("service_rate") val serviceRate: Double? = null,
    @SerializedName("total_ag_rate") val totalAgRate: Double? = null,
    @SerializedName("total_an_rate") val totalAnRate: Double? = null,
    @SerializedName("tax_rate") val taxRate: Double? = null,
    @SerializedName("benefit") val benefit: Double? = null,
)

data class MdrOrderInfo(
    @SerializedName("leasing_company") val leasingCompany: String? = null,
    @SerializedName("insurance_tariff") val insuranceTariff: String? = null,
    @SerializedName("service_package") val servicePackage: String? = null,
    @SerializedName("delivery_date") val deliveryDate: String? = null,
    @SerializedName("leasing_duration") val leasingDuration: Int? = null,
    @SerializedName("leasing_end") val leasingEnd: String? = null,
)

data class MdrOrderDoc(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String? = null,
    @SerializedName("allowed_to_open") val allowedToOpen: Boolean? = null,
    @SerializedName("employer_note") val employerNote: String? = null,
)

data class MdrOrderTrackingStatus(
    @SerializedName("value") val value: String? = null,
    @SerializedName("label") val label: String? = null,
    @SerializedName("faq") val description: String? = null,
    @SerializedName("active") val active: Boolean? = null,
)

fun GetMdrOrderDetailResponse.toDomain(): MdrOrderDetailDM = MdrOrderDetailDM(
    id = response?.bike?.id!!,
    number = response.bike.number,
    bikeName = response.bike.bikeName,
    mdrStatus = response.bike.mdrStatus,
    servicePackage = response.bike.servicePackage,
    leasingEnd = response.bike.leasingEnd,
    dealerName = response.bike.dealerName,
    bike = response.bike.bike?.toDomain(),
    accessories = response.bike.accessories?.map { it.toDomain() },
    prices = response.bike.prices?.toDomain(),
    dealer = response.bike.dealer?.toDomain(),
    calculation = response.bike.calculation?.toDomain(),
    order = response.bike.order?.toDomain(),
    docs = response.bike.docs?.map { it.toDomain() },
    allowedToUpload = response.bike.allowedToUpload,
    trackingStatuses = response.bike.trackingStatuses?.map { it.toDomain() },
)

fun MdrOrderBike.toDomain(): MdrOrderBikeDM = MdrOrderBikeDM(
    model = model,
    type = type,
    brand = brand,
    frame = frame,
    size = size,
    color = color,
    category = category
)

fun MdrOrderAccessory.toDomain(): MdrOrderAccessoryDM = MdrOrderAccessoryDM(
    id = id,
    name = name,
    quantity = quantity,
    uvp = uvp,
    net = net,
    gross = gross,
)

fun MdrOrderPrices.toDomain(): MdrOrderPricesDM = MdrOrderPricesDM(
    net = net,
    gross = gross,
    uvp = uvp,
)

fun MdrOrderDealer.toDomain() = MdrOrderDealerDM(name, address)

fun MdrOrderCalculation.toDomain(): MdrOrderCalculationDM = MdrOrderCalculationDM(
    leasingRate = leasingRate,
    insuranceRate = insuranceRate,
    serviceRate = serviceRate,
    totalAgRate = totalAgRate,
    totalAnRate = totalAnRate,
    taxRate = taxRate,
    benefit = benefit,
)

fun MdrOrderInfo.toDomain(): MdrOrderInfoDM = MdrOrderInfoDM(
    leasingCompany = leasingCompany,
    insuranceTariff = insuranceTariff,
    servicePackage = servicePackage,
    deliveryDate = deliveryDate,
    leasingDuration = leasingDuration,
    leasingEnd = leasingEnd,
)

fun MdrOrderDoc.toDomain(): MdrOrderDocDM = MdrOrderDocDM(
    id = id,
    name = name,
    allowedToOpen = allowedToOpen,
    employerNote = employerNote,
)

fun MdrOrderTrackingStatus.toDomain(): MdrOrderTrackingStatusDM = MdrOrderTrackingStatusDM(
    value = value,
    label = label,
    description = description,
    active = active,
)