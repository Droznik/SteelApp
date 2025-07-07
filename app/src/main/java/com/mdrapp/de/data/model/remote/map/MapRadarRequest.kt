package com.mdrapp.de.data.model.remote.map

import com.google.gson.annotations.SerializedName
import com.mdrapp.de.domain.model.AddressDataRequestDM
import com.mdrapp.de.domain.model.LocationRequestDM
import com.mdrapp.de.domain.model.MapRadarRequestDM

data class MapRadarRequest(
    @SerializedName("addressData")
    val addressData: AddressDataRequest
)

data class LocationRequest(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double
)

data class AddressDataRequest(
    @SerializedName("location")
    val location: LocationRequest,
)

fun MapRadarRequestDM.toData(): MapRadarRequest {
    return MapRadarRequest(
        addressData = this.addressData.toData()
    )
}

fun AddressDataRequestDM.toData(): AddressDataRequest {
    return AddressDataRequest(
        location = this.location.toData()
    )
}

fun LocationRequestDM.toData(): LocationRequest {
    return LocationRequest(
        lat = this.lat,
        lng = this.lng
    )
}