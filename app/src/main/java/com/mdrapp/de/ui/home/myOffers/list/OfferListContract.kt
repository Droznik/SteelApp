package com.mdrapp.de.ui.home.myOffers.list

import com.mdrapp.de.ui.home.myOffers.list.model.OfferListItemVM
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf


data class OfferListState(
    val offers: ImmutableList<OfferListItemVM> = persistentListOf(),
    val count: Int = 0
)


sealed interface OfferListEvent {
    data class OnOfferClick(val item: OfferListItemVM) : OfferListEvent
    data class OnOfferDelete(val id: Long) : OfferListEvent
    data object OnAllOffersDelete : OfferListEvent
}