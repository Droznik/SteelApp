package com.mdrapp.de.data.repository

import com.mdrapp.de.data.MdrApiService
import com.mdrapp.de.data.model.remote.baseResponse.mdr.handled
import com.mdrapp.de.data.model.remote.profile.toDomain
import com.mdrapp.de.domain.model.ProfileDM
import com.mdrapp.de.domain.repository.UserRepository
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(
    private val api: MdrApiService
) : UserRepository {
    override suspend fun getUserProfile(): ProfileDM {
        return api.getUserProfile().handled().toDomain()
    }
}