package com.mdrapp.de.ui.home.bikePass

import androidx.navigation.NavController
import com.mdrapp.de.ui.home.bikePass.model.BikePassDataVM
import com.mdrapp.de.ui.home.bikePass.model.CustomerDataVM
import com.mdrapp.de.ui.home.bikePass.model.LeasingDataVM

data class BikePassState(
    val bikePassData: BikePassDataVM? = null,
    val leasingData: LeasingDataVM? = null,
    val customerData: CustomerDataVM? = null
)

sealed interface BikePassEvent {
    data object OnFaqBtnClicked: BikePassEvent
    data object OnSupportBtnClicked: BikePassEvent
    data class OnResetPasswordBtnClicked(val navController: NavController): BikePassEvent
}