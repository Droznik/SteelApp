package com.mdrapp.de.domain.model.category

data class SliderCategoriesDM(
    val data: List<CategoryDataDM>? = null
)

data class CategoryDataDM(
    val title: String? = null,
    val path: String? = null,
    val categories: String? = null,
    val bikeCount: String? = null,
)