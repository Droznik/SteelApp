package com.mdrapp.de.domain.model

data class MapPointDM(
    val businessStatus: String,
    val formattedAddress: String,
    val geometry: GeometryDM,
    val icon: String,
    val iconBackgroundColor: String,
    val iconMaskBaseUri: String,
    val name: String,
    val placeId: String,
    val plusCode: PlusCodeDM,
    val rating: Double,
    val reference: String,
    val types: List<String>,
    val userRatingsTotal: Int,
    val openingHours: OpeningHoursDM,
    val photos: List<PhotoDM>
)

data class GeometryDM(
    val location: MapLocationDM,
    val viewport: ViewportDM
)

data class MapLocationDM(
    val lat: Double,
    val lng: Double
)

data class ViewportDM(
    val northeast: NortheastDM,
    val southwest: SouthwestDM
)

data class NortheastDM(
    val lat: Double,
    val lng: Double
)

data class SouthwestDM(
    val lat: Double,
    val lng: Double
)

data class PlusCodeDM(
    val compoundCode: String,
    val globalCode: String
)

data class OpeningHoursDM(
    val openNow: Boolean
)

data class PhotoDM(
    val height: Int,
    val htmlAttributions: List<String>,
    val photoReference: String,
    val width: Int
)

data class AddressItemDM(
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

data class MapRadarRequestDM(
    val addressData: AddressDataRequestDM
)

data class LocationRequestDM(
    val lat: Double,
    val lng: Double
)

data class AddressDataRequestDM(
    val location: LocationRequestDM
)