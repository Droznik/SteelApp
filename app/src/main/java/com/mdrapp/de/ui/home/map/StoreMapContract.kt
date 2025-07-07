package com.mdrapp.de.ui.home.map

import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.mdrapp.de.ui.home.map.model.AddressItemVM
import com.mdrapp.de.ui.home.map.model.MapPointVM

data class StoreMapState(
    val cameraPosition: CameraPosition = CameraPosition.fromLatLngZoom(LatLng(51.1657, 10.4515), 10f),
    val postCode : String = "",
    val radius : String = "15",
    val mapPoints: List<MapPointVM> = emptyList(),
    val selectedMapPoint: MapPointVM? = null,
    val radarResponse: List<AddressItemVM> = emptyList(),
    val selectedAddress: AddressItemVM? = null,
    val mapResultsError: Boolean = false
)

sealed interface StoreMapEvent {
    data class OnMapPointSelected(val mapPoint: MapPointVM) : StoreMapEvent
    data class OnPostCodeChange(val postCode: String) : StoreMapEvent
    data object OnPostCodeSubmit : StoreMapEvent
    data class OnRadiusChange(val radius: String) : StoreMapEvent
    data object OnBannerClick : StoreMapEvent
    data class OnMoreClicked(val mapPoint: AddressItemVM) : StoreMapEvent
    data class OnAddressSelected(val address: AddressItemVM) : StoreMapEvent
}