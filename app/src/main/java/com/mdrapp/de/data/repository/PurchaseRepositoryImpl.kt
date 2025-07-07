package com.mdrapp.de.data.repository

import com.mdrapp.de.data.MdrApiService
import com.mdrapp.de.data.model.remote.IdRequest
import com.mdrapp.de.data.model.remote.baseResponse.mdr.handled
import com.mdrapp.de.data.model.remote.offers.toDomain
import com.mdrapp.de.data.model.remote.purchase.toDM
import com.mdrapp.de.data.model.remote.purchase.toData
import com.mdrapp.de.domain.model.CalculationsResultDM
import com.mdrapp.de.domain.model.OfferOrderInfoDM
import com.mdrapp.de.domain.model.SetCalculationsDM
import com.mdrapp.de.domain.model.SetPurchaseDM
import com.mdrapp.de.domain.repository.PurchaseRepository
import javax.inject.Inject

class PurchaseRepositoryImpl @Inject constructor(
    val api: MdrApiService
) : PurchaseRepository {

    override suspend fun checkPurchase() {
        api.getOrderCheck().handled()
    }

    override suspend fun getPurchaseInfo() = api.getOrder().handled().toDM()

    override suspend fun getOfferOrderInfo(offerId: Long): OfferOrderInfoDM =
        api.getOfferOrder(IdRequest(offerId)).handled().toDomain()

    override suspend fun setPurchase(request: SetPurchaseDM) {
        api.createOrder(request.toData()).handled()
    }

    override suspend fun calculate(request: SetCalculationsDM): CalculationsResultDM =
        api.calculate(request.toData()).handled().toDM()
}