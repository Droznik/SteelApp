package com.mdrapp.de.data.repository

import com.cbsapp.de.data.model.remote.bikeCategories.toDomain
import com.mdrapp.de.data.model.remote.product.toCatalogProductDM
import com.mdrapp.de.data.model.remote.product.toDomain
import com.cbsapp.de.data.model.remote.product.toSliderProductsDM
import com.mdrapp.de.data.CbsApiService
import com.mdrapp.de.data.model.remote.baseResponse.cbs.handled
import com.mdrapp.de.domain.model.PagingProductVariantsDM
import com.mdrapp.de.domain.model.ProductDM
import com.mdrapp.de.domain.model.ProductFilterDM
import com.mdrapp.de.domain.model.SliderProductDM
import com.mdrapp.de.domain.model.category.SliderCategoriesDM
import com.mdrapp.de.domain.repository.ProductRepository
import com.mdrapp.de.domain.util.BikeType
import javax.inject.Inject


class ProductRepositoryImpl @Inject constructor (
    private val api: CbsApiService,
) : ProductRepository {

    override suspend fun getTopProductsSlider(type: BikeType?): List<SliderProductDM> =
        api.getTopBikes(params = getParamsByType(type)).handled().toSliderProductsDM()

    override suspend fun getTopProductsCatalog(
        perPage: Int?,
        page: Int?,
        params: Map<String, String>
    ): PagingProductVariantsDM {
        val response = api.getTopBikes(perPage, page, params).handled()
        val products = response.data?.filterNotNull().orEmpty()

        return PagingProductVariantsDM(
            variants = products.map { it.toCatalogProductDM() },
            currentPage = response.meta?.currentPage,
            lastPage = response.meta?.lastPage
        )
    }

    override suspend fun getSpecialOfferSlider(type: BikeType?): List<SliderProductDM> =
        api.getSpecialOffer(params = getParamsByType(type)).handled().toSliderProductsDM()

    override suspend fun getSpecialOfferCatalog(
        perPage: Int?,
        page: Int?,
        params: Map<String, String>
    ): PagingProductVariantsDM {
        val response = api.getSpecialOffer(perPage, page, params).handled()
        val products = response.data?.filterNotNull().orEmpty()

        return PagingProductVariantsDM(
            variants = products.map { it.toCatalogProductDM() },
            currentPage = response.meta?.currentPage,
            lastPage = response.meta?.lastPage
        )
    }

    override suspend fun getActionCarouselSlider(type: BikeType?): List<SliderProductDM> =
        api.getActionCarousel(params = getParamsByType(type)).handled().toSliderProductsDM()

    override suspend fun getActionCarouselCatalog(
        perPage: Int?,
        page: Int?,
        params: Map<String, String>
    ): PagingProductVariantsDM {
        val response = api.getActionCarousel(perPage, page, params).handled()
        val products = response.data?.filterNotNull().orEmpty()

        return PagingProductVariantsDM(
            variants = products.map { it.toCatalogProductDM() },
            currentPage = response.meta?.currentPage,
            lastPage = response.meta?.lastPage
        )
    }

    override suspend fun getProduct(productId: Long): ProductDM {
        val response = api.getProduct(productId).handled()
        return response.data!!.toDomain()
    }

    override suspend fun getMainVariants(
        perPage: Int?,
        page: Int?,
        params: Map<String, String>
    ): PagingProductVariantsDM {

        val response = api.getMainVariants(perPage, page, params).handled()
        val products = response.data?.filterNotNull().orEmpty()

        return PagingProductVariantsDM(
            variants = products.map { it.toCatalogProductDM() },
            currentPage = response.meta?.currentPage,
            lastPage = response.meta?.lastPage
        )
    }

    private fun getParamsByType(type: BikeType?) = type?.let {
        when(type) {
            BikeType.None -> mapOf()
            else -> mapOf("type" to it.name)
        }
    } ?: mapOf()

    override suspend fun getSliderCategories(type: String): SliderCategoriesDM {
        return api.getSliderCategories(type).handled().toDomain()
    }

    override suspend fun getProductFilters(): List<ProductFilterDM> =
        api.getProductFilters().filter { !it.values.isNullOrEmpty() }.map { it.toDomain() }
}