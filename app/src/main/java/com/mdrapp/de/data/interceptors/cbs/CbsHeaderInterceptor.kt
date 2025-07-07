package com.mdrapp.de.data.interceptors.cbs

import android.content.Context
import com.mdrapp.de.domain.storage.SessionStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


const val CBS_HEADER_AUTH = "Authorization"
const val CBS_PREFIX_TOKEN = "Bearer"
const val CBS_PREFIX_GUEST = "jwt"
const val CBS_HEADER_AUTH_TOKEN = "$CBS_HEADER_AUTH: $CBS_PREFIX_TOKEN"
const val CBS_HEADER_AUTH_GUEST = "$CBS_HEADER_AUTH: $CBS_PREFIX_GUEST"
const val CBS_HEADER_LANG_TAG = "lang-tag"

class CbsHeaderInterceptor @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
    private val sessionStorage: SessionStorage
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authHeader = request.header(CBS_HEADER_AUTH)
        val builder = request.newBuilder()
        val currentLanguage = applicationContext.resources.configuration.locales[0].language

        when(authHeader) {
            CBS_PREFIX_TOKEN -> runBlocking {
//                sessionStorage.getToken()?.let {
//                    builder.header(CBS_HEADER_AUTH, "$CBS_PREFIX_TOKEN $it")
//                    if (BuildConfig.DEBUG) Log.i("TOKEN", it)
//                }
            }
            CBS_PREFIX_GUEST -> runBlocking {
//                sessionStorage.getGuestToken()?.let {
//                    builder.header(CBS_HEADER_AUTH, "$CBS_PREFIX_GUEST $it")
//                    if (BuildConfig.DEBUG) Log.i("GUEST_TOKEN", it)
//                }
            }
        }

        builder.header("Accept", "application/json")
        builder.header(CBS_HEADER_LANG_TAG, currentLanguage)

        return chain.proceed(builder.build())
    }
}