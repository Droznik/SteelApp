package com.mdrapp.de.ui.home.home

import com.mdrapp.de.common.viewmodel.ContractViewModel
import com.mdrapp.de.navigation.HomeNavHost
import com.mdrapp.de.navigation.NavEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ShopViewModel @Inject constructor(

) : ContractViewModel<ShopState, ShopEvent> () {

    override val initialState = ShopState()

    override fun onEvent(event: ShopEvent) {
        when(event) {
            ShopEvent.ShowProfile -> navigate(NavEvent.To(HomeNavHost.Profile.route))
        }
    }
}