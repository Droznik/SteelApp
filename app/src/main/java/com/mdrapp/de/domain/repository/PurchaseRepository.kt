package com.mdrapp.de.domain.repository

import com.mdrapp.de.domain.model.CalculationsResultDM
import com.mdrapp.de.domain.model.OfferOrderInfoDM
import com.mdrapp.de.domain.model.PurchaseInfoDM
import com.mdrapp.de.domain.model.SetCalculationsDM
import com.mdrapp.de.domain.model.SetPurchaseDM

interface PurchaseRepository {

    suspend fun checkPurchase()
    suspend fun getPurchaseInfo(): PurchaseInfoDM
    suspend fun getOfferOrderInfo(offerId: Long): OfferOrderInfoDM
    suspend fun setPurchase(request: SetPurchaseDM)
    suspend fun calculate(request: SetCalculationsDM): CalculationsResultDM
}