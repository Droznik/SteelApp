package com.mdrapp.de.ui.home.home


data class ShopState(
    val initials: String = ""
)

sealed interface ShopEvent {
    data object ShowProfile : ShopEvent
}
