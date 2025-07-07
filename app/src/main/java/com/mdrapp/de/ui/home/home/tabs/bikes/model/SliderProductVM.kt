package com.mdrapp.de.ui.home.home.tabs.bikes.model

import com.mdrapp.de.domain.model.SliderProductDM


data class SliderProductVM(
    val id: Long,
    val displayName: String,
    val category: String,
    val imgUrl: String
)

fun SliderProductDM.toVM(): SliderProductVM = SliderProductVM(
    id = id,
    displayName = displayName.orEmpty(),
    category = category.orEmpty(),
    imgUrl = imageUrl.orEmpty(),
)