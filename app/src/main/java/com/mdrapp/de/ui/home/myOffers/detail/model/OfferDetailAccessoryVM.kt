package com.mdrapp.de.ui.home.myOffers.detail.model

import com.mdrapp.de.domain.model.OfferDetailAccessoryDM

data class OfferDetailAccessoryVM(
    val title: String,
    val quantity: Int,
    val netSellingPrice: Double,
    val grossSalesPrice: Double,
    val salesPriceGrossIncl: String,
    val uvp: Double,
)

fun OfferDetailAccessoryDM.toVM(): OfferDetailAccessoryVM = OfferDetailAccessoryVM(
    title = title.orEmpty(),
    quantity = quantity ?: 1,
    netSellingPrice = netSellingPrice ?: 0.0,
    grossSalesPrice = grossSalesPrice ?: 0.0,
    salesPriceGrossIncl = salesPriceGrossIncl.orEmpty(),
    uvp = uvp ?: 0.0,
)