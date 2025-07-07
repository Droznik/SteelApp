package com.mdrapp.de.ui.home.home.tabs.bikes.model

import com.mdrapp.de.R
import com.mdrapp.de.domain.model.category.SliderCategoriesDM
import com.mdrapp.de.ui.util.UIText


data class SliderCategoryVM(
    val title: UIText,
    val article: UIText,
    val categories: String = "",
    val image: String = "",
    val drawable: Int? = null
)


fun SliderCategoriesDM.toVM(): List<SliderCategoryVM> {
    return this.data?.map { categoryData ->
        SliderCategoryVM(
            title = UIText.DynamicString(categoryData.title ?: ""),
            categories = categoryData.categories ?: "",
            image = categoryData.path ?: "",
            article = UIText.StringResource(R.string.format_article, categoryData.bikeCount?.toIntOrNull() ?: 0)
        )
    }.orEmpty()

}