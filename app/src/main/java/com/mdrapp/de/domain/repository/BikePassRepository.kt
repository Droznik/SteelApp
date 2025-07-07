package com.mdrapp.de.domain.repository

import com.mdrapp.de.domain.model.bikePass.BikePassDM

interface BikePassRepository {
    suspend fun getBikePass(): BikePassDM
}