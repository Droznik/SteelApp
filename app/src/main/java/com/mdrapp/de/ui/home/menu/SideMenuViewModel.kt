package com.mdrapp.de.ui.home.menu

import com.mdrapp.de.common.viewmodel.ContractViewModel
import com.mdrapp.de.domain.repository.PurchaseRepository
import com.mdrapp.de.navigation.HomeNavHost
import com.mdrapp.de.navigation.NavEvent
import com.mdrapp.de.navigation.PopUpToAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class SideMenuViewModel @Inject constructor(
    private val purchaseRepo: PurchaseRepository
) : ContractViewModel<SideMenuState, SideMenuEvent>() {

    override val initialState = SideMenuState()

    override fun onEvent(event: SideMenuEvent) {
        when(event) {
            is SideMenuEvent.NavigatePopUpToHome -> navigatePopUpToHome(event.route)
            is SideMenuEvent.OnPurchaseClick -> onPurchaseClick(event.onSuccess)
        }
    }

    private fun navigatePopUpToHome(route: String) {
        navigate(
            NavEvent.To(
                route = route,
                popUpTo = PopUpToAction.RouteDestination(HomeNavHost.Home.route, false)
            )
        )
    }

    private fun onPurchaseClick(onSuccess: () -> Unit) = simpleLaunch {
        withContext(Dispatchers.IO) { purchaseRepo.checkPurchase() }
        navigatePopUpToHome(HomeNavHost.Purchase.route)
        onSuccess()
    }
}