package com.mdrapp.de.domain.model

data class PagingProductVariantsDM(
    val variants: List<CatalogProductDM>,
    val currentPage: Int?,
    val lastPage: Int?
)
