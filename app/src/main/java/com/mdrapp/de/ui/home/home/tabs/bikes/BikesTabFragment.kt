package com.mdrapp.de.ui.home.home.tabs.bikes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mdrapp.de.R
import com.mdrapp.de.common.fragment.BaseFragment
import com.mdrapp.de.domain.util.BikeType
import com.mdrapp.de.ui.home.home.SliderType
import com.mdrapp.de.ui.home.home.tabs.bikes.model.SliderCategoryVM
import com.mdrapp.de.ui.home.home.tabs.bikes.model.SliderProductVM
import com.mdrapp.de.ui.home.home.tabs.bikes.view.SliderCategoryItem
import com.mdrapp.de.ui.home.home.tabs.bikes.view.SliderProducts
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.util.UIText
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList


@Composable
fun BikesTabFragment(navController: NavController, vm: BikesTabViewModel, type: BikeType) {
    BaseFragment(vm, navController) {
        val state by vm.state.collectAsState()
        val onEvent = remember { vm::onEvent }

        LaunchedEffect(Unit) {
            vm.bikeType = type
            vm.loadEbikes()
        }

        BikesTabContent(state, onEvent)
    }
}

@Composable
fun BikesTabContent(
    state: BikesTabState,
    onEvent: (BikesTabEvent) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(13.dp),
        contentPadding = PaddingValues(bottom = 54.dp)
    ) {
        item {
            SliderCategoryItem(category = state.profileBanner) { onEvent(BikesTabEvent.ShowProfile) }
        }
        item {
            SliderCategoryItem(category = state.purchaseBanner) { onEvent(BikesTabEvent.OnPurchaseClick) }
        }
        sliderCategories(state.categories) { sliderCategoryItem ->
            onEvent(BikesTabEvent.OnCategoryClick(sliderCategoryItem))
        }
        item {
            AnimatedVisibility(state.specialPromotions.isNotEmpty()) {
                SliderProducts(
                    title = R.string.special_promotions,
                    actionTitle = R.string.all_bikes,
                    products = state.specialPromotions,
                    onItemClick = { onEvent(BikesTabEvent.OnHomeProductClick(it)) },
                    onActionClick = { onEvent(BikesTabEvent.OnAllProductsClick(SliderType.SpecialOffer)) },
                    modifier = Modifier.padding(top = 63.dp)
                )
            }
        }
        item {
            AnimatedVisibility(state.actionCarousel.isNotEmpty()) {
                SliderProducts(
                    title = R.string.action_carousel,
                    actionTitle = R.string.all_bikes,
                    products = state.actionCarousel,
                    onItemClick = { onEvent(BikesTabEvent.OnHomeProductClick(it)) },
                    onActionClick = { onEvent(BikesTabEvent.OnAllProductsClick(SliderType.ActionCarousel)) },
                    modifier = Modifier.padding(top = 63.dp)
                )
            }
        }
        item {
            AnimatedVisibility(state.topRecommendations.isNotEmpty()) {
                SliderProducts(
                    title = R.string.top_recommendations,
                    actionTitle = R.string.all_bikes,
                    products = state.topRecommendations,
                    onItemClick = { onEvent(BikesTabEvent.OnHomeProductClick(it)) },
                    onActionClick = { onEvent(BikesTabEvent.OnAllProductsClick(SliderType.TopBikes)) },
                    modifier = Modifier.padding(top = 63.dp)
                )
            }
        }
    }
}

fun LazyListScope.sliderCategories(
    categories: ImmutableList<SliderCategoryVM>,
    onCategoryClick: (item: SliderCategoryVM) -> Unit
) {
    items(categories.size) { index ->
        SliderCategoryItem(category = categories[index], onItemClick = {
            onCategoryClick(categories[index])
        })
    }
}


@Preview(heightDp = 1700)
@Composable
fun BikesTabPreview() {
    MDRTheme {
        BikesTabContent(state = BikesTabState(
            categories = listOf(
                SliderCategoryVM( UIText.DynamicString("City / Urban"), UIText.StringResource(R.string.format_article,39)),
                SliderCategoryVM( UIText.DynamicString("Trekking"), UIText.StringResource(R.string.format_article,236)),
            ).toPersistentList(),
            specialPromotions = List(5) { index ->
                SliderProductVM(
                    index.toLong(),
                    "XTRA WATT EVO+",
                    "E-Urban Bike",
                    ""
                )
            }.toPersistentList(),
            topRecommendations = List(5) { index ->
                SliderProductVM(
                    index.toLong(),
                    "XTRA WATT EVO+",
                    "E-Urban Bike",
                    ""
                )
            }.toPersistentList()
        ), onEvent = {})
    }
}