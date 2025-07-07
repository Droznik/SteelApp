package com.mdrapp.de.data.repository

import com.mdrapp.de.data.MdrApiService
import com.mdrapp.de.data.model.remote.baseResponse.mdr.handled
import com.mdrapp.de.data.model.remote.IdRequest
import com.mdrapp.de.data.model.remote.offers.toDomain
import com.mdrapp.de.domain.model.OfferDetailDM
import com.mdrapp.de.domain.model.OfferListItemDM
import com.mdrapp.de.domain.repository.OfferRepository
import javax.inject.Inject

class OfferRepositoryImpl @Inject constructor(
    private val api: MdrApiService
) : OfferRepository {

    override suspend fun getOfferList(): List<OfferListItemDM> =
        api.getOfferList().handled().toDomain()

    override suspend fun deleteOffer(offerId: Long) {
        api.deleteOffer(IdRequest(offerId)).handled()
    }

    override suspend fun getOfferDetail(offerId: Long): OfferDetailDM =
        api.getOfferDetail(IdRequest(offerId)).handled().toDomain()
}