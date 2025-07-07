package com.mdrapp.de.domain.storage

import kotlinx.coroutines.flow.Flow

interface SessionStorage {

    suspend fun getInitials(): Flow<String?>

    suspend fun setInitials(initials: String)

    suspend fun setAccessToken(token: String)

    suspend fun getAccessToken(): String?

    suspend fun getToken(): String?

    suspend fun setToken(token: String)

    suspend fun isAuthorised(): Boolean

    suspend fun getCategories() : List<String>?

    suspend fun setCategories(ids: List<String>)

    suspend fun logout()
    suspend fun completeHowTo(isComplete: Boolean)
    suspend fun isHowToComplete(): Boolean
}