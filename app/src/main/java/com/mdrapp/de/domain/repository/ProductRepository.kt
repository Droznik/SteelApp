package com.mdrapp.de.domain.repository

import com.mdrapp.de.domain.model.PagingProductVariantsDM
import com.mdrapp.de.domain.model.ProductDM
import com.mdrapp.de.domain.model.ProductFilterDM
import com.mdrapp.de.domain.model.SliderProductDM
import com.mdrapp.de.domain.model.category.SliderCategoriesDM
import com.mdrapp.de.domain.util.BikeType


interface ProductRepository {
    suspend fun getTopProductsSlider(type: BikeType?): List<SliderProductDM>
    suspend fun getTopProductsCatalog(
        perPage: Int? = null,
        page: Int? = null,
        params: Map<String, String> = mapOf()
    ): PagingProductVariantsDM

    suspend fun getSpecialOfferSlider(type: BikeType?): List<SliderProductDM>
    suspend fun getSpecialOfferCatalog(
        perPage: Int? = null,
        page: Int? = null,
        params: Map<String, String> = mapOf()
    ): PagingProductVariantsDM

    suspend fun getActionCarouselSlider(type: BikeType?): List<SliderProductDM>
    suspend fun getActionCarouselCatalog(
        perPage: Int? = null,
        page: Int? = null,
        params: Map<String, String> = mapOf()
    ): PagingProductVariantsDM

    suspend fun getProduct(productId: Long): ProductDM

    suspend fun getMainVariants(
        perPage: Int? = null,
        page: Int? = null,
        params: Map<String, String> = mapOf()
    ): PagingProductVariantsDM

    suspend fun getSliderCategories(type: String): SliderCategoriesDM

    suspend fun getProductFilters(): List<ProductFilterDM>
}