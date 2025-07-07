package com.mdrapp.de.ui.home.myBikes.detail

import com.mdrapp.de.domain.model.MdrOrderAccessoryDM
import com.mdrapp.de.domain.model.MdrOrderBikeDM
import com.mdrapp.de.domain.model.MdrOrderCalculationDM
import com.mdrapp.de.domain.model.MdrOrderDealerDM
import com.mdrapp.de.domain.model.MdrOrderDetailDM
import com.mdrapp.de.domain.model.MdrOrderDocDM
import com.mdrapp.de.domain.model.MdrOrderInfoDM
import com.mdrapp.de.domain.model.MdrOrderPricesDM
import com.mdrapp.de.domain.model.MdrOrderTrackingStatusDM


data class MdrOrderDetailCommonInfoVM(
    val bikeName: String = "",
    val servicePackage: String = "",
    val leasingEnd: String = "",
    val mdrStatus: String = "",
    val dealer: String = ""
)

data class MdrOrderBikeVM(
    val model: String = "",
    val type: String = "",
    val brand: String = "",
    val frameType: String = "",
    val category: String = "",
    val frameSize: String = "",
    val color: String = "",
    val quantity: String = "1"
)

data class MdrOrderAccessoryVM(
    val id: Long,
    val name: String = "",
    val quantity: Int = 1,
    val uvp: Double = 0.0,
    val net: Double = 0.0,
    val gross: Double = 0.0
)

data class MdrOrderPricesVM(
    val net: Double = 0.0,
    val gross: Double = 0.0,
    val uvp: Double = 0.0,
)

data class MdrOrderDealerVM(
    val name: String = "",
    val address: String = "",
)

data class MdrOrderCalculationVM(
    val leasingRate: Double = 0.0,
    val insuranceRate: Double = 0.0,
    val serviceRate: Double = 0.0,
    val totalAgRate: Double = 0.0,
    val totalAnRate: Double = 0.0,
    val taxRate: Double = 0.0,
    val benefit: Double = 0.0,
)

data class MdrOrderInfoVM(
    val leasingCompany: String = "",
    val insuranceTariff: String = "",
    val servicePackage: String = "",
    val deliveryDate: String = "",
    val leasingDuration: Int = 0,
    val leasingEnd: String = "",
)

data class MdrOrderDocVM(
    val id: Long,
    val name: String = "",
    val allowedToOpen: Boolean = false,
    val employerNote: String? = null,
)

data class MdrOrderTrackingStatusVM(
    val value: String = "",
    val label: String = "",
    val description: String = "",
    val active: Boolean = false,
)


fun MdrOrderDetailDM.toCommonInfoVM(): MdrOrderDetailCommonInfoVM = MdrOrderDetailCommonInfoVM(
    bikeName = bikeName.orEmpty(),
    servicePackage = servicePackage.orEmpty(),
    leasingEnd = leasingEnd.orEmpty(),
    mdrStatus = mdrStatus.orEmpty(),
    dealer = dealerName.orEmpty(),
)

fun MdrOrderBikeDM.toVM(): MdrOrderBikeVM = MdrOrderBikeVM(
    model = model.orEmpty(),
    type = type.orEmpty(),
    brand = brand.orEmpty(),
    frameType = frame.orEmpty(),
    category = category.orEmpty(),
    frameSize = size.orEmpty(),
    color = color.orEmpty(),
)

fun MdrOrderAccessoryDM.toVM(): MdrOrderAccessoryVM = MdrOrderAccessoryVM(
    id = id,
    name = name.orEmpty(),
    quantity = quantity ?: 1,
    uvp = uvp ?: 0.0,
    net = net ?: 0.0,
    gross = gross ?: 0.0,
)

fun MdrOrderPricesDM.toVM(): MdrOrderPricesVM = MdrOrderPricesVM(
    net = net ?: 0.0,
    gross = gross ?: 0.0,
    uvp = uvp ?: 0.0,
)

fun MdrOrderDealerDM.toVM(): MdrOrderDealerVM = MdrOrderDealerVM(name.orEmpty(), address.orEmpty())

fun MdrOrderCalculationDM.toVM(): MdrOrderCalculationVM = MdrOrderCalculationVM(
    leasingRate = leasingRate ?: 0.0,
    insuranceRate = insuranceRate ?: 0.0,
    serviceRate = serviceRate ?: 0.0,
    totalAgRate = totalAgRate ?: 0.0,
    totalAnRate = totalAnRate ?: 0.0,
    taxRate = taxRate ?: 0.0,
    benefit = benefit ?: 0.0,
)

fun MdrOrderInfoDM.toVM(): MdrOrderInfoVM = MdrOrderInfoVM(
    leasingCompany = leasingCompany.orEmpty(),
    insuranceTariff = insuranceTariff.orEmpty(),
    servicePackage = servicePackage.orEmpty(),
    deliveryDate = deliveryDate.orEmpty(),
    leasingDuration = leasingDuration ?: 0,
    leasingEnd = leasingEnd.orEmpty(),
)

fun MdrOrderDocDM.toVM(): MdrOrderDocVM = MdrOrderDocVM(
    id = id,
    name = name.orEmpty(),
    allowedToOpen = allowedToOpen ?: false,
    employerNote = employerNote,
)

fun MdrOrderTrackingStatusDM.toVM(): MdrOrderTrackingStatusVM = MdrOrderTrackingStatusVM(
    value = value.orEmpty(),
    label = label.orEmpty(),
    description = description.orEmpty(),
    active = active ?: false,
)

