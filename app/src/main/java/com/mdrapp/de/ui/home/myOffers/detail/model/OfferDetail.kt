package com.mdrapp.de.ui.home.myOffers.detail.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class OfferDetailBikeVM(
    val model: String = "",
    val type: String = "",
    val brand: String = "",
    val frameType: String = "",
    val category: String = "",
    val frameSize: String = "",
    val color: String = "",
)

data class OfferDetailAccessoryInfoVM(
    val accessories: ImmutableList<OfferDetailAccessoryVM> = persistentListOf(),
    val totalPriceNet: Double = 0.0,
    val totalPriceGross: Double = 0.0,
    val uvp: Double = 0.0,
)

data class OfferDetailDealerVM(
    val name: String = "",
    val shippingAddress: String = "",
    val shippingPostcode: String = "",
    val shippingCity: String = "",
    val shippingPhone: String = "",
    val website: String = "",
)

data class OfferDetailCalculationVM(
    val leasingRate: Double = 0.0,
    val insuranceRate: Double = 0.0,
    val serviceRate: Double = 0.0,
    val lessEmployerContribution: Double = 0.0,
    val withheldFromGrossSalary: Double = 0.0,
    val pecuniaryAdvantage: Double = 0.0,
)
