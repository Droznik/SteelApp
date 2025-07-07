package com.mdrapp.de.data.interceptors.cbs

import com.mdrapp.de.UnauthorizedException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

const val CBS_CODE_SUCCESS = 200
val CBS_CODE_FOR_REPLACE = listOf(422)

class CbsCodesInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val builder = chain.request().newBuilder()
        val response = chain.proceed(builder.build())
        return when (response.code) {
            in CBS_CODE_FOR_REPLACE -> response.newBuilder().code(CBS_CODE_SUCCESS).build()
            401 -> throw UnauthorizedException()
            else -> response
        }
    }
}