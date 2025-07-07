package com.mdrapp.de.data.interceptors.mdr

import com.mdrapp.de.UnauthorizedException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

const val CODE_SUCCESS = 200
val CODE_FOR_REPLACE = listOf(400, 403, 422)

class CodesInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val builder = chain.request().newBuilder()
        val response = chain.proceed(builder.build())
        return when (response.code) {
            in CODE_FOR_REPLACE -> response.newBuilder().code(CODE_SUCCESS).build()
            401 -> throw UnauthorizedException()
            else -> response
        }
    }
}