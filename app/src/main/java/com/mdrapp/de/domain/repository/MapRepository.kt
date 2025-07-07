package com.mdrapp.de.domain.repository

import com.mdrapp.de.domain.model.AddressItemDM
import com.mdrapp.de.domain.model.MapPointDM
import com.mdrapp.de.domain.model.MapRadarRequestDM

interface MapRepository {
    suspend fun getMapLocation(postCode: String): List<MapPointDM>
    suspend fun getRadarResponse(mapRadarRequestDM: MapRadarRequestDM, radius: String): List<AddressItemDM>
}