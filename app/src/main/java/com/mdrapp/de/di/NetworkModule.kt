package com.mdrapp.de.di

import com.google.gson.Gson
import com.mdrapp.de.BuildConfig
import com.mdrapp.de.data.CbsApiService
import com.mdrapp.de.data.MdrApiService
import com.mdrapp.de.data.interceptors.cbs.CbsCodesInterceptor
import com.mdrapp.de.data.interceptors.cbs.CbsHeaderInterceptor
import com.mdrapp.de.data.interceptors.cbs.CbsTimeoutInterceptor
import com.mdrapp.de.data.interceptors.mdr.CodesInterceptor
import com.mdrapp.de.data.interceptors.mdr.HeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideSamsApiService(
        @MdrOkHttpClient okHttpClient: OkHttpClient,
        gson: Gson
    ): MdrApiService = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()
        .create(MdrApiService::class.java)

    @Provides
    @Singleton
    fun provideCbsApiService(
        @CbsOkHttpClient okHttpClient: OkHttpClient,
        gson: Gson
    ): CbsApiService = Retrofit.Builder()
        .baseUrl(BuildConfig.CBS_API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()
        .create(CbsApiService::class.java)

    @Provides
    @Singleton
    @MdrOkHttpClient
    fun provideSamsOkHttpClient(
        codesInterceptor: CodesInterceptor,
        headerInterceptor: HeaderInterceptor
    ): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(getLogger())
        .addInterceptor(codesInterceptor)
        .addInterceptor(headerInterceptor)
        .build()

    @Provides
    @Singleton
    @CbsOkHttpClient
    fun provideCbsOkHttpClient(
        headerInterceptor: CbsHeaderInterceptor,
        codesInterceptor: CbsCodesInterceptor,
        timeoutInterceptor: CbsTimeoutInterceptor,
    ): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(getLogger())
        .addInterceptor(headerInterceptor)
        .addInterceptor(codesInterceptor)
        .addInterceptor(timeoutInterceptor)
        .build()
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MdrOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CbsOkHttpClient

private fun getLogger() =
    HttpLoggingInterceptor().apply {
        level = when {
            BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
            else              -> HttpLoggingInterceptor.Level.NONE
        }
    }