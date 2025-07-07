package com.mdrapp.de.domain.repository

import com.mdrapp.de.domain.model.OfferDetailDM
import com.mdrapp.de.domain.model.OfferListItemDM

interface OfferRepository {

    suspend fun getOfferList(): List<OfferListItemDM>
    suspend fun deleteOffer(offerId: Long)
    suspend fun getOfferDetail(offerId: Long): OfferDetailDM
}