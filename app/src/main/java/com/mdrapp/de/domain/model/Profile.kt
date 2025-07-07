package com.mdrapp.de.domain.model

data class ProfileDM(
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