package com.mdrapp.de.ui.home.home.tabs.bikes

import com.mdrapp.de.R
import com.mdrapp.de.ui.home.home.SliderType
import com.mdrapp.de.ui.home.home.tabs.bikes.model.SliderCategoryVM
import com.mdrapp.de.ui.home.home.tabs.bikes.model.SliderProductVM
import com.mdrapp.de.ui.util.UIText
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf


data class BikesTabState(
    val profileBanner: SliderCategoryVM = SliderCategoryVM(
        title = UIText.StringResource(R.string.shop_banner_profile_title),
        article = UIText.StringResource(R.string.shop_banner_sub_title),
        drawable = R.drawable.img_home_banner_profile
    ),
    val purchaseBanner: SliderCategoryVM = SliderCategoryVM(
        title = UIText.StringResource(R.string.shop_banner_purchase_title),
        article = UIText.StringResource(R.string.shop_banner_sub_title),
        drawable = R.drawable.img_home_banner_purchase
    ),
    val categories: ImmutableList<SliderCategoryVM> = persistentListOf(),
    val specialPromotions: ImmutableList<SliderProductVM> = persistentListOf(),
    val actionCarousel: ImmutableList<SliderProductVM> = persistentListOf(),
    val topRecommendations: ImmutableList<SliderProductVM> = persistentListOf(),
)

sealed interface BikesTabEvent {
    data object ShowProfile : BikesTabEvent
    data object OnPurchaseClick : BikesTabEvent
    data class OnCategoryClick(val item: SliderCategoryVM) : BikesTabEvent
    data class OnHomeProductClick(val id: Long) : BikesTabEvent
    data class OnAllProductsClick(val sliderType: SliderType) : BikesTabEvent
}