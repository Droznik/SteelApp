package com.mdrapp.de.data.repository

import com.mdrapp.de.data.MdrApiService
import com.mdrapp.de.data.model.remote.baseResponse.mdr.handled
import com.mdrapp.de.data.model.remote.mdrOrders.UploadFileInfo
import com.mdrapp.de.data.model.remote.mdrOrders.UploadOrderFileRequest
import com.mdrapp.de.data.model.remote.mdrOrders.toDomain
import com.mdrapp.de.domain.model.MdrOrderDetailDM
import com.mdrapp.de.domain.model.MdrOrderItemDM
import com.mdrapp.de.domain.repository.MyBikesRepository
import javax.inject.Inject

class MyBikesRepositoryImpl @Inject constructor(
    private val api: MdrApiService
) : MyBikesRepository {

    override suspend fun getMdrOrders(type: String): List<MdrOrderItemDM> =
        api.getMdrOrders(type).handled().toDomain()

    override suspend fun getMdrOrderDetail(id: Long): MdrOrderDetailDM =
        api.getMdrOrderDetail(id).handled().toDomain()

    override suspend fun getMdrOrderFileUrl(id: Long): String =
        api.getMdrOrderFile(id).handled().response?.url.orEmpty()

    override suspend fun uploadDocument(orderId: Long, fileName: String, mime: String, base64: String) {
        api.uploadDocument(
            UploadOrderFileRequest(
                orderId = orderId,
                name = fileName,
                file = UploadFileInfo(mime, base64)
            )
        ).handled()
    }
}