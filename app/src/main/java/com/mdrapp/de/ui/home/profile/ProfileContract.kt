package com.mdrapp.de.ui.home.profile

import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import com.mdrapp.de.domain.model.ProfileDM

data class ProfileState(
    val employer: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val address: String = "",
    val personalNumber: String = "",
    val country: String = "",

    val allowed: String = "",
    val minMaxPrice: String = "",
    val basis: String = "",
    val priceLimit: String = "",
    val quantity: String = "",
    val employerSubsidies: String = "",
    val leasable: String = "",
)

sealed interface ProfileEvent {
    data class OnLogout(val rootNavController: NavController) : ProfileEvent
    data object OnTwoFactorAuthClick : ProfileEvent
    data object OnChangeRequested : ProfileEvent
}

fun ProfileState.fromDM(profileDM: ProfileDM) = this.copy(
    employer = profileDM.employer,
    name = profileDM.name,
    email = profileDM.email,
    phone = profileDM.phone,
    address = profileDM.address,
    personalNumber = profileDM.personalNumber,
    country = profileDM.country,
    allowed = profileDM.allowed,
    minMaxPrice = profileDM.minMaxPrice,
    basis = profileDM.basis,
    priceLimit = profileDM.priceLimit,
    quantity = profileDM.quantity,
    employerSubsidies = HtmlCompat.fromHtml(profileDM.employerSubsidies, HtmlCompat.FROM_HTML_MODE_LEGACY).toString(),
    leasable = HtmlCompat.fromHtml(profileDM.leasable, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
)