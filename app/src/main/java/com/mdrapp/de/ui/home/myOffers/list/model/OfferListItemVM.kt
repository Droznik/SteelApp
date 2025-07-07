package com.mdrapp.de.ui.home.myOffers.list.model

import com.mdrapp.de.domain.model.OfferListItemDM

data class OfferListItemVM(
    val id: Long,
    val offerValidUntil: String,
    val model: String,
    val brand: String,
    val totalPrice: Double,
    val leasingRate: Double,
    val dealer: String,
)

fun OfferListItemDM.toVM(): OfferListItemVM = OfferListItemVM(
    id = id,
    offerValidUntil = offerValidUntil.orEmpty(),
    model = model.orEmpty(),
    brand = brand.orEmpty(),
    totalPrice = totalPrice ?: 0.0,
    leasingRate = leasingRate ?: 0.0,
    dealer = dealer.orEmpty()
)