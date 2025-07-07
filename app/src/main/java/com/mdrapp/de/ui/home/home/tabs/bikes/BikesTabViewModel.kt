package com.mdrapp.de.ui.home.home.tabs.bikes

import androidx.lifecycle.viewModelScope
import com.mdrapp.de.common.viewmodel.ContractViewModel
import com.mdrapp.de.domain.repository.ProductRepository
import com.mdrapp.de.domain.util.BikeType
import com.mdrapp.de.navigation.HomeNavHost
import com.mdrapp.de.navigation.NavEvent
import com.mdrapp.de.ui.home.home.SliderType
import com.mdrapp.de.ui.home.home.tabs.bikes.model.SliderCategoryVM
import com.mdrapp.de.ui.home.home.tabs.bikes.model.toVM
import com.mdrapp.de.ui.util.UIText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class BikesTabViewModel @Inject constructor(
    private val productRepo: ProductRepository
) : ContractViewModel<BikesTabState, BikesTabEvent>() {

    override val initialState = BikesTabState()
    lateinit var bikeType: BikeType
    private var isLoaded: Boolean = false

    override fun onEvent(event: BikesTabEvent) {
        when(event) {
            is BikesTabEvent.OnCategoryClick -> goToCategory(event.item)
            is BikesTabEvent.OnHomeProductClick -> toProduct(event.id)
            is BikesTabEvent.OnAllProductsClick -> goToSliderCatalog(event.sliderType)
            BikesTabEvent.OnPurchaseClick -> navigate(NavEvent.To(HomeNavHost.Purchase.route))
            BikesTabEvent.ShowProfile -> navigate(NavEvent.To(HomeNavHost.Profile.route))
        }
    }

    private fun goToCategory(item: SliderCategoryVM) {
        navigate(
            NavEvent.To(
                route = HomeNavHost.Category.createRoute(
                    bikeType, (item.title as UIText.DynamicString).value, item.categories
                ),
                launchSingleTop = true
            )
        )
    }

    private fun toProduct(id: Long) {
        navigate(NavEvent.To(HomeNavHost.Product.createRoute(id), true))
    }

    private fun goToSliderCatalog(type: SliderType) {
        when(type) {
            SliderType.TopBikes -> navigate(
                NavEvent.To(HomeNavHost.TopBikes.createRoute(bikeType), true)
            )
            SliderType.SpecialOffer -> navigate(
                NavEvent.To(HomeNavHost.SpecialOffer.createRoute(bikeType), true)
            )
            SliderType.ActionCarousel -> navigate(
                NavEvent.To(HomeNavHost.ActionCarousel.createRoute(bikeType), true)
            )
        }

    }

    fun loadEbikes() {
        if (!isLoaded){
            viewModelScope.launch(baseErrorHandler) {
                loading = true
                supervisorScope {
                    listOf(
                        launch {
                            val categories = withContext(Dispatchers.IO) {
                                productRepo.getSliderCategories(bikeType.name).toVM()
                                    .toPersistentList()
                            }
                            _state.update { it.copy(categories = categories) }
                        },
                        launch {
                            val specialOffer = withContext(Dispatchers.IO) {
                                productRepo.getSpecialOfferSlider(bikeType).map { it.toVM() }
                                    .toPersistentList()
                            }
                            _state.update { it.copy(specialPromotions = specialOffer) }
                        },
                        launch {
                            val actionCarousel = withContext(Dispatchers.IO) {
                                productRepo.getActionCarouselSlider(bikeType).map { it.toVM() }
                                    .toPersistentList()
                            }
                            _state.update { it.copy(actionCarousel = actionCarousel) }
                        },
                        launch {
                            val topBikes = withContext(Dispatchers.IO) {
                                productRepo.getTopProductsSlider(bikeType).map { it.toVM() }
                                    .toPersistentList()
                            }
                            _state.update { it.copy(topRecommendations = topBikes) }
                        }
                    ).joinAll()
                    loading = false
                    isLoaded = true
                }
            }
        }
    }
}