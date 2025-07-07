package com.mdrapp.de.data.repository

import com.mdrapp.de.data.MdrApiService
import com.mdrapp.de.data.model.remote.baseResponse.mdr.handled
import com.mdrapp.de.data.model.remote.map.toData
import com.mdrapp.de.data.model.remote.map.toDomain
import com.mdrapp.de.domain.model.AddressItemDM
import com.mdrapp.de.domain.model.MapPointDM
import com.mdrapp.de.domain.model.MapRadarRequestDM
import com.mdrapp.de.domain.repository.MapRepository
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    private val api: MdrApiService,
) : MapRepository {
    override suspend fun getMapLocation(postCode: String): List<MapPointDM> =
        api.getAddresses(postCode).handled().toDomain()

    override suspend fun getRadarResponse(mapRadarRequestDM: MapRadarRequestDM,radius: String): List<AddressItemDM> =
        api.radarAddress(radius, mapRadarRequestDM.toData()).handled().toDomain()
}