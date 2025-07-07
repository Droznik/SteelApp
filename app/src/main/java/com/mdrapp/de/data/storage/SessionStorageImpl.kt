package com.mdrapp.de.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mdrapp.de.data.model.pref.toBikeCategoriesPref
import com.mdrapp.de.data.model.pref.toCategoryList
import com.mdrapp.de.domain.storage.SessionStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject


val TOKEN = stringPreferencesKey("token")
val ACCESS_TOKEN = stringPreferencesKey("access_token")
val INITIALS = stringPreferencesKey("user_initials")
val CATEGORIES = stringPreferencesKey("onboarding_categories")
val HOW_TO = booleanPreferencesKey("isHowToComplete")

class SessionStorageImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SessionStorage {

    override suspend fun getInitials(): Flow<String?> {
        return dataStore.data.map { it[INITIALS] }
    }

    override suspend fun setInitials(initials: String) {
        dataStore.edit { it[INITIALS] = initials }
    }

    override suspend fun getToken(): String? {
       return dataStore.data.first()[TOKEN]
    }

    override suspend fun setToken(token: String) {
        dataStore.edit { it[TOKEN] = token }
    }

    override suspend fun setAccessToken(token: String) {
        dataStore.edit { it[ACCESS_TOKEN] = token }
    }

    override suspend fun getAccessToken(): String? {
        return dataStore.data.first()[ACCESS_TOKEN]
    }

    override suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.remove(TOKEN)
            preferences.remove(ACCESS_TOKEN)
            preferences.remove(INITIALS)
            preferences.remove(CATEGORIES)
        }
    }

    override suspend fun isAuthorised(): Boolean {
        return !dataStore.data.first()[ACCESS_TOKEN].isNullOrBlank()
    }

    override suspend fun getCategories(): List<String>? {
        return dataStore.data.first()[CATEGORIES]?.toCategoryList()
    }

    override suspend fun setCategories(ids: List<String>) {
        dataStore.edit { it[CATEGORIES] = ids.toBikeCategoriesPref() }
    }

    override suspend fun completeHowTo(isComplete: Boolean) {
        dataStore.edit { it[HOW_TO] = isComplete }
    }

    override suspend fun isHowToComplete(): Boolean {
        return dataStore.data.first()[HOW_TO] ?: false
    }

}