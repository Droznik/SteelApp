package com.mdrapp.de.domain.model

data class OfferListItemDM(
    val id: Long,
    val offerValidUntil: String?,
    val model: String?,
    val brand: String?,
    val totalPrice: Double?,
    val leasingRate: Double?,
    val dealer: String?
)

data class OfferDetailDM(
    val id: Long,
    val offerValidUntil: String?,
    val purchaseTotalPrice: Double?,
    val leasingRate: Double?,
    val bike: OfferDetailBikeDM?,
    val accessoryInfo: OfferDetailAccessoryInfoDM?,
    val dealer: OfferDetailDealerDM?,
    val calculation: OfferDetailCalculationDM?,
)

data class OfferDetailBikeDM(
    val model: String?,
    val type: String?,
    val brand: String?,
    val frameType: String?,
    val category: String?,
    val frameSize: String?,
    val color: String?,
)

data class OfferDetailAccessoryInfoDM(
    val accessories: List<OfferDetailAccessoryDM>?,
    val totalPriceNet: Double?,
    val totalPriceGross: Double?,
    val uvp: Double?,
)

data class OfferDetailAccessoryDM(
    val title: String?,
    val quantity: Int?,
    val netSellingPrice: Double?,
    val grossSalesPrice: Double?,
    val salesPriceGrossIncl: String?,
    val uvp: Double?,
)

data class OfferDetailDealerDM(
    val name: String?,
    val shippingAddress: String?,
    val shippingPostcode: String?,
    val shippingCity: String?,
    val shippingPhone: String?,
    val website: String?,
)

data class OfferDetailCalculationDM(
    val leasingRate: Double?,
    val insuranceRate: Double?,
    val serviceRate: Double?,
    val lessEmployerContribution: Double?,
    val withheldFromGrossSalary: Double?,
    val pecuniaryAdvantage: Double?,
)