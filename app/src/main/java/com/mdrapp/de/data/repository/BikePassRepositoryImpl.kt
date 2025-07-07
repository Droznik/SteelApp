package com.mdrapp.de.data.repository

import com.mdrapp.de.data.MdrApiService
import com.mdrapp.de.data.model.remote.baseResponse.mdr.handled
import com.mdrapp.de.data.model.remote.toDomain
import com.mdrapp.de.domain.model.bikePass.BikePassDM
import com.mdrapp.de.domain.repository.BikePassRepository
import javax.inject.Inject

class BikePassRepositoryImpl @Inject constructor(
    private val apiService: MdrApiService
) : BikePassRepository {

    override suspend fun getBikePass(): BikePassDM {
        return apiService.getBikePass().handled().toDomain()
    }

}