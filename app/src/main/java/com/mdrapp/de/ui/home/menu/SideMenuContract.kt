package com.mdrapp.de.ui.home.menu

data class SideMenuState(
    val initials: String = ""
)

sealed interface SideMenuEvent {
    data class NavigatePopUpToHome(val route: String) : SideMenuEvent
    data class OnPurchaseClick(val onSuccess: () -> Unit) : SideMenuEvent
}
