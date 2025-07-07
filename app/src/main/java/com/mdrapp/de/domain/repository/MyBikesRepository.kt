package com.mdrapp.de.domain.repository

import com.mdrapp.de.domain.model.MdrOrderDetailDM
import com.mdrapp.de.domain.model.MdrOrderItemDM

interface MyBikesRepository {

    suspend fun getMdrOrders(type: String): List<MdrOrderItemDM>
    suspend fun getMdrOrderDetail(id: Long): MdrOrderDetailDM
    suspend fun getMdrOrderFileUrl(id: Long): String
    suspend fun uploadDocument(orderId: Long, fileName: String, mime: String, base64: String)
}