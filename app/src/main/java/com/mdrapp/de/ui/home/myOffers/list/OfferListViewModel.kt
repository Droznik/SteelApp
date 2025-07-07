package com.mdrapp.de.ui.home.myOffers.list

import com.mdrapp.de.common.viewmodel.ContractViewModel
import com.mdrapp.de.domain.repository.OfferRepository
import com.mdrapp.de.navigation.MyOffersGraph
import com.mdrapp.de.navigation.NavEvent
import com.mdrapp.de.ui.home.myOffers.list.model.OfferListItemVM
import com.mdrapp.de.ui.home.myOffers.list.model.toVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class OfferListViewModel @Inject constructor(
    private val offerRepo: OfferRepository
) : ContractViewModel<OfferListState, OfferListEvent>() {

    override val initialState = OfferListState()

    init {
        loadOffers()
    }

    override fun onEvent(event: OfferListEvent) {
        when(event) {
            OfferListEvent.OnAllOffersDelete -> onAllOffersDelete()
            is OfferListEvent.OnOfferClick -> onOfferClick(event.item)
            is OfferListEvent.OnOfferDelete -> onOfferDelete(event.id)
        }
    }

    private fun loadOffers() = simpleLaunch {
        val offers = withContext(Dispatchers.IO) {
            offerRepo.getOfferList().map { it.toVM() }.toPersistentList()
        }

        _state.update { it.copy(offers = offers, count = offers.size) }
    }

    private fun onAllOffersDelete() {

    }

    private fun onOfferClick(item: OfferListItemVM) {
        navigate(NavEvent.To(MyOffersGraph.OfferDetail.createRoute(item.id)))
    }

    private fun onOfferDelete(id: Long) = simpleLaunch {
        val deletedIndex = state.value.offers.indexOfFirst { it.id == id }

        if (deletedIndex != -1) {
            withContext(Dispatchers.IO) { offerRepo.deleteOffer(id) }

            val updatedOffers = state.value.offers.toMutableList()
                .apply { removeAt(deletedIndex) }
                .toPersistentList()

            _state.update { it.copy(offers = updatedOffers, count = updatedOffers.size) }
        }
    }
}