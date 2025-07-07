package com.mdrapp.de.ui.home.bikePass

import androidx.navigation.NavController
import com.mdrapp.de.common.viewmodel.ContractViewModel
import com.mdrapp.de.domain.repository.BikePassRepository
import com.mdrapp.de.navigation.AuthGraph
import com.mdrapp.de.navigation.HomeNavHost
import com.mdrapp.de.navigation.NavEvent
import com.mdrapp.de.ui.home.bikePass.model.toVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BikePassViewModel @Inject constructor(
    private val bikePassRepository: BikePassRepository
) : ContractViewModel<BikePassState, BikePassEvent>() {

    override val initialState = BikePassState()

    init {
        getBikePass()
    }

    override fun onEvent(event: BikePassEvent) {
        when (event) {
            is BikePassEvent.OnFaqBtnClicked -> navigate(NavEvent.To(HomeNavHost.Faq.route))
            is BikePassEvent.OnResetPasswordBtnClicked -> forgotPassword(event.navController)
            is BikePassEvent.OnSupportBtnClicked -> navigate(NavEvent.To(HomeNavHost.Support.route))
        }
    }

    private fun getBikePass() = simpleLaunch {
        val response = withContext(Dispatchers.IO) { bikePassRepository.getBikePass().toVM() }
        _state.update {
            it.copy(
                bikePassData = response.bikePassData,
                leasingData = response.leasingData,
                customerData = response.customerData
            )
        }
    }

    private fun forgotPassword(rootNavController: NavController) = simpleLaunch {
        rootNavController.navigate(AuthGraph.ForgotPassword.route) {
            launchSingleTop = true
        }
    }

}