package com.mdrapp.de.ui.home.map

import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.mdrapp.de.common.viewmodel.ContractViewModel
import com.mdrapp.de.domain.model.AddressDataRequestDM
import com.mdrapp.de.domain.model.LocationRequestDM
import com.mdrapp.de.domain.model.MapRadarRequestDM
import com.mdrapp.de.domain.repository.MapRepository
import com.mdrapp.de.navigation.DealerLocationGraph
import com.mdrapp.de.navigation.HomeNavHost
import com.mdrapp.de.navigation.NavEvent
import com.mdrapp.de.navigation.PopUpToAction
import com.mdrapp.de.ui.home.map.model.AddressItemVM
import com.mdrapp.de.ui.home.map.model.MapPointVM
import com.mdrapp.de.ui.home.map.model.toVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StoreMapViewModel @Inject constructor(
    private val mapRepository: MapRepository
) : ContractViewModel<StoreMapState, StoreMapEvent>() {
    override val initialState = StoreMapState()

    override fun onEvent(event: StoreMapEvent) {
        when (event) {
            is StoreMapEvent.OnPostCodeChange -> _state.update { it.copy(postCode = event.postCode) }
            is StoreMapEvent.OnPostCodeSubmit -> getMapPoints(state.value.postCode)
            is StoreMapEvent.OnRadiusChange -> onRadiusChange(event.radius)
            is StoreMapEvent.OnMapPointSelected -> onMapPointSelected(event.mapPoint)
            is StoreMapEvent.OnMoreClicked -> onMoreClicked(event.mapPoint)
            StoreMapEvent.OnBannerClick -> navigateHome()
            is StoreMapEvent.OnAddressSelected -> _state.update { it.copy(selectedAddress = event.address) }
        }
    }

    private fun getMapPoints(postCode: String) {
        simpleLaunch {
            val mapPoints = withContext(Dispatchers.IO){mapRepository.getMapLocation(postCode).toVM()}
            _state.update { currentState ->
                if (mapPoints.isNotEmpty()) {
                    val selectedMapPoint = mapPoints.first()
                    currentState.copy(
                        mapPoints = mapPoints,
                        mapResultsError = false,
                        selectedMapPoint = selectedMapPoint
                    ).also {
                        onMapPointSelected(selectedMapPoint)
                        moveCameraToSelectedMapPoint()
                    }
                } else {
                    currentState.copy(
                        mapResultsError = true
                    )
                }
            }
        }
    }

    private fun onMoreClicked(addressItemVM: AddressItemVM) {
        _state.update { it.copy(selectedAddress = addressItemVM) }
        navigate(NavEvent.To(DealerLocationGraph.DealerLocationDetails.route))
    }

    private fun onMapPointSelected(mapPoint: MapPointVM?) {
        if (mapPoint !=null) {
            _state.update { it.copy(selectedMapPoint = mapPoint) }
            }
        getRadarResponse(state.value.radius)
    }

    private fun moveCameraToSelectedMapPoint() {
        val cameraPosition: CameraPosition = CameraPosition.fromLatLngZoom(
            LatLng(state.value.selectedMapPoint?.geometry?.location?.lat?: 51.1657,
            state.value.selectedMapPoint?.geometry?.location?.lng?: 10.4515), 10f)
        _state.update { it.copy(cameraPosition = cameraPosition) }
    }

    private fun onRadiusChange(radius: String) {
        _state.update { it.copy(radius = radius) }
        getRadarResponse(radius)
    }

    private fun navigateHome() {
        navigate(
            NavEvent.To(
                route = HomeNavHost.Home.route,
                popUpTo = PopUpToAction.Graph(true)
            )
        )
    }
    private var radarJob: Job? = null
    private fun getRadarResponse(radius: String) {
        radarJob?.cancel()
        radarJob = simpleLaunch {
            val mapRadarResponse = withContext(Dispatchers.IO){ mapRepository.getRadarResponse(
                MapRadarRequestDM(
                    AddressDataRequestDM(
                        LocationRequestDM(
                            state.value.selectedMapPoint?.geometry?.location?.lat ?: 53.551086,
                            state.value.selectedMapPoint?.geometry?.location?.lng ?: 9.993682
                        )
                    )
                ), radius
            ).toVM()}
            _state.update { currentState ->
                if (mapRadarResponse.isEmpty()) {
                    currentState.copy(mapResultsError = true)
                } else {
                    currentState.copy(
                        mapResultsError = false,
                        radarResponse = mapRadarResponse
                    )
                }
            }
        }
    }
}
