package com.mdrapp.de.ui.home.myOffers.detail

import androidx.lifecycle.SavedStateHandle
import com.mdrapp.de.common.viewmodel.ContractViewModel
import com.mdrapp.de.domain.repository.OfferRepository
import com.mdrapp.de.navigation.NavEvent
import com.mdrapp.de.navigation.OFFER_ID_ARG
import com.mdrapp.de.navigation.PurchaseGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class OfferDetailViewModel @Inject constructor(
    private val offerRepo: OfferRepository,
    savedStateHandle: SavedStateHandle
) : ContractViewModel<OfferDetailState, OfferDetailEvent>() {

    val offerId: Long = savedStateHandle.get<Long>(OFFER_ID_ARG)!!
    override val initialState = OfferDetailState()

    init {
        loadDetailInfo()
    }

    override fun onEvent(event: OfferDetailEvent) {
        when(event) {
            OfferDetailEvent.Back -> navigate(NavEvent.Back)
            OfferDetailEvent.ShowOrderForm -> showOrderForm()
        }
    }

    private fun loadDetailInfo() = simpleLaunch {
        val detailInfo = withContext(Dispatchers.IO) { offerRepo.getOfferDetail(offerId) }

        _state.update { it.fromOfferDetailDM(detailInfo) }
    }

    private fun showOrderForm() {
        navigate(NavEvent.To(PurchaseGraph.Order.createRoute(offerId)))
    }
}