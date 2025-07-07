package com.mdrapp.de.data

import com.cbsapp.de.data.model.remote.bikeCategories.SliderCategoriesResponse
import com.cbsapp.de.data.model.remote.product.GetProductResponse
import com.cbsapp.de.data.model.remote.product.PagingVariantsResponse
import com.mdrapp.de.data.interceptors.cbs.CBS_HEADER_AUTH_GUEST
import com.mdrapp.de.data.model.remote.product.ProductFilter
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CbsApiService {

    /* products */

    @POST("bike-products/top-bikes")
    @Headers(CBS_HEADER_AUTH_GUEST)
    suspend fun getTopBikes(
        @Query("per_page") perPage: Int? = null,
        @Query("page") page: Int? = null,
        @Body params: Map<String, String> = mapOf()
    ): PagingVariantsResponse

    @POST("bike-products/special-offer")
    @Headers(CBS_HEADER_AUTH_GUEST)
    suspend fun getSpecialOffer(
        @Query("per_page") perPage: Int? = null,
        @Query("page") page: Int? = null,
        @Body params: Map<String, String> = mapOf()
    ): PagingVariantsResponse

    @POST("bike-products/action-carousel")
    @Headers(CBS_HEADER_AUTH_GUEST)
    suspend fun getActionCarousel(
        @Query("per_page") perPage: Int? = null,
        @Query("page") page: Int? = null,
        @Body params: Map<String, String> = mapOf()
    ): PagingVariantsResponse

    @GET("bike-product/{id}")
    @Headers(CBS_HEADER_AUTH_GUEST)
    suspend fun getProduct(@Path("id") productId: Long): GetProductResponse

    @POST("bike-products/main-variants")
    @Headers(CBS_HEADER_AUTH_GUEST)
    suspend fun getMainVariants(
        @Query("per_page") perPage: Int? = null,
        @Query("page") page: Int? = null,
        @Body params: Map<String, String> = mapOf()
    ): PagingVariantsResponse

    @GET("bike-products/filters")
    @Headers(CBS_HEADER_AUTH_GUEST)
    suspend fun getProductFilters(): List<ProductFilter>

    @GET("category-sliders")
    @Headers(CBS_HEADER_AUTH_GUEST)
    suspend fun getSliderCategories(@Query("type") type: String): SliderCategoriesResponse
}