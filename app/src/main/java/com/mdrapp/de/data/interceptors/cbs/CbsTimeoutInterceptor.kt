package com.mdrapp.de.data.interceptors.cbs

import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject

const val CBS_TIMEOUT = "TIMEOUT"

class CbsTimeoutInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newTimeout = request.header(CBS_TIMEOUT)
        val connectTimeout = newTimeout?.toIntOrNull() ?: 30000
        val readTimeout = newTimeout?.toIntOrNull() ?: 30000
        val writeTimeout = newTimeout?.toIntOrNull() ?: 30000

        val newRequest = request.newBuilder().removeHeader(CBS_TIMEOUT).build()

        return chain
            .withConnectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
            .withReadTimeout(readTimeout, TimeUnit.MILLISECONDS)
            .withWriteTimeout(writeTimeout, TimeUnit.MILLISECONDS)
            .proceed(newRequest)
    }
}