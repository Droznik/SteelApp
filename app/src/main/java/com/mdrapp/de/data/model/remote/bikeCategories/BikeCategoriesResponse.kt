package com.cbsapp.de.data.model.remote.bikeCategories

import com.google.gson.annotations.SerializedName
import com.mdrapp.de.data.model.remote.baseResponse.cbs.CbsBaseResponse
import com.mdrapp.de.domain.model.category.CategoryDataDM
import com.mdrapp.de.domain.model.category.SliderCategoriesDM

data class SliderCategoriesResponse (
    @SerializedName("data") val data: List<SliderCategoryData>? = null
) : CbsBaseResponse()

data class SliderCategoryData(
    @SerializedName("title") val title: String? = null,
    @SerializedName("path") val path: String? = null,
    @SerializedName("categories") val categories: String? = null,
    @SerializedName("bike_count") val bikeCount: String? = null,
)

fun SliderCategoriesResponse.toDomain(): SliderCategoriesDM {
    val categoryDataList = this.data.orEmpty().map { categoryData ->
        CategoryDataDM(
            title = categoryData.title ?: "",
            path = categoryData.path ?: "",
            categories = categoryData.categories ?: "",
            bikeCount = categoryData.bikeCount ?: ""
        )
    }
    return SliderCategoriesDM(data = categoryDataList)
}
