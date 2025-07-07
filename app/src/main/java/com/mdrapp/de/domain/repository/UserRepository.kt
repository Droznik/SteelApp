package com.mdrapp.de.domain.repository

import com.mdrapp.de.domain.model.ProfileDM

interface UserRepository {
    suspend fun getUserProfile(): ProfileDM
}