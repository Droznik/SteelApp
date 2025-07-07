package com.mdrapp.de.data.interceptors.mdr

import android.content.Context
import android.util.Log
import com.mdrapp.de.BuildConfig
import com.mdrapp.de.domain.storage.SessionStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


const val HEADER_AUTH = "Authorization"
const val PREFIX_TOKEN = "Bearer"
const val PREFIX_REGISTER_TOKEN = "register_token"
const val HEADER_AUTH_REGISTER = "$HEADER_AUTH: $PREFIX_REGISTER_TOKEN"

class HeaderInterceptor @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
    private val sessionStorage: SessionStorage
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authHeader = request.header(HEADER_AUTH)
        val builder = request.newBuilder()
        //val currentLanguage = applicationContext.resources.configuration.locales[0].language
        when(authHeader) {
            PREFIX_REGISTER_TOKEN -> runBlocking {
                sessionStorage.getToken().let {
                    builder.header(HEADER_AUTH, "$PREFIX_TOKEN $it")
                    if (BuildConfig.DEBUG) Log.i("TOKEN", it?:"")
                }
            }
            else -> runBlocking {
                sessionStorage.getAccessToken().let {
                    builder.header(HEADER_AUTH, "$PREFIX_TOKEN $it")
                    if (BuildConfig.DEBUG) Log.i("ACCESS_TOKEN", it?:"")
                }
            }
        }

        builder.header("Accept", "application/json")

        return chain.proceed(builder.build())
    }
}