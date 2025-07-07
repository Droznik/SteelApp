package com.mdrapp.de.ui.home.myOffers.detail

import com.mdrapp.de.domain.model.OfferDetailDM
import com.mdrapp.de.ui.home.myOffers.detail.model.OfferDetailAccessoryInfoVM
import com.mdrapp.de.ui.home.myOffers.detail.model.OfferDetailBikeVM
import com.mdrapp.de.ui.home.myOffers.detail.model.OfferDetailCalculationVM
import com.mdrapp.de.ui.home.myOffers.detail.model.OfferDetailDealerVM
import com.mdrapp.de.ui.home.myOffers.detail.model.toVM
import kotlinx.collections.immutable.toPersistentList


data class OfferDetailState(
    val offerValidUntil: String = "",
    val purchaseTotalPrice: Double = 0.0,
    val leasingRate: Double = 0.0,
    val bike: OfferDetailBikeVM = OfferDetailBikeVM(),
    val accessoryInfo: OfferDetailAccessoryInfoVM = OfferDetailAccessoryInfoVM(),
    val dealer: OfferDetailDealerVM = OfferDetailDealerVM(),
    val calculation: OfferDetailCalculationVM = OfferDetailCalculationVM(),
)

sealed interface OfferDetailEvent {
    data object Back : OfferDetailEvent
    data object ShowOrderForm : OfferDetailEvent
}

fun OfferDetailState.fromOfferDetailDM(detail: OfferDetailDM) = this.copy(
    offerValidUntil = detail.offerValidUntil.orEmpty(),
    purchaseTotalPrice = detail.purchaseTotalPrice ?: 0.0,
    leasingRate = detail.leasingRate ?: 0.0,
    bike = OfferDetailBikeVM(
        model = detail.bike?.model.orEmpty(),
        type = detail.bike?.type.orEmpty(),
        brand = detail.bike?.brand.orEmpty(),
        frameType = detail.bike?.frameType.orEmpty(),
        category = detail.bike?.category.orEmpty(),
        frameSize = detail.bike?.frameSize.orEmpty(),
        color = detail.bike?.color.orEmpty(),
    ),
    accessoryInfo =  OfferDetailAccessoryInfoVM(
        accessories = detail.accessoryInfo?.accessories?.map { it.toVM() }.orEmpty().toPersistentList(),
        totalPriceNet = detail.accessoryInfo?.totalPriceNet ?: 0.0,
        totalPriceGross = detail.accessoryInfo?.totalPriceGross ?: 0.0,
        uvp = detail.accessoryInfo?.uvp ?: 0.0,
    ),
    dealer =  OfferDetailDealerVM(
        name = detail.dealer?.name.orEmpty(),
        shippingAddress = detail.dealer?.shippingAddress.orEmpty(),
        shippingPostcode = detail.dealer?.shippingPostcode.orEmpty(),
        shippingCity = detail.dealer?.shippingCity.orEmpty(),
        shippingPhone = detail.dealer?.shippingPhone.orEmpty(),
        website = detail.dealer?.website.orEmpty(),
    ),
    calculation = OfferDetailCalculationVM(
        leasingRate = detail.calculation?.leasingRate ?: 0.0,
        insuranceRate = detail.calculation?.insuranceRate ?: 0.0,
        serviceRate = detail.calculation?.serviceRate ?: 0.0,
        lessEmployerContribution = detail.calculation?.lessEmployerContribution ?: 0.0,
        withheldFromGrossSalary = detail.calculation?.withheldFromGrossSalary ?: 0.0,
        pecuniaryAdvantage = detail.calculation?.pecuniaryAdvantage ?: 0.0,
    )
)