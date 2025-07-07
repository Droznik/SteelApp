package com.mdrapp.de.ui.home.map.model

import com.mdrapp.de.domain.model.AddressItemDM

data class AddressItemVM(
    val id: Int,
    val name: String,
    val street: String,
    val zip: String,
    val city: String,
    val phone: String,
    val email: String,
    val website: String,
    val lng: Double,
    val lat: Double
)

fun AddressItemDM.toVM(): AddressItemVM {
    return AddressItemVM(
        id = this.id,
        name = this.name,
        street = this.street,
        zip = this.zip,
        city = this.city,
        phone = this.phone,
        email = this.email,
        website = this.website,
        lng = this.lng,
        lat = this.lat
    )
}

fun List<AddressItemDM>.toVM(): List<AddressItemVM> {
    return this.map { it.toVM() }
}