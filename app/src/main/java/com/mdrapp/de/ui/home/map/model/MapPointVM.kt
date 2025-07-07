package com.mdrapp.de.ui.home.map.model

import com.mdrapp.de.domain.model.GeometryDM
import com.mdrapp.de.domain.model.MapLocationDM
import com.mdrapp.de.domain.model.MapPointDM
import com.mdrapp.de.domain.model.NortheastDM
import com.mdrapp.de.domain.model.OpeningHoursDM
import com.mdrapp.de.domain.model.PhotoDM
import com.mdrapp.de.domain.model.PlusCodeDM
import com.mdrapp.de.domain.model.SouthwestDM
import com.mdrapp.de.domain.model.ViewportDM

data class MapPointVM(
    val businessStatus: String,
    val formattedAddress: String,
    val geometry: GeometryVM,
    val icon: String,
    val iconBackgroundColor: String,
    val iconMaskBaseUri: String,
    val name: String,
    val placeId: String,
    val plusCode: PlusCodeVM,
    val rating: Double,
    val reference: String,
    val types: List<String>,
    val userRatingsTotal: Int,
    val openingHours: OpeningHoursVM? = null,
    val photos: List<PhotoVM>? = null
)

data class GeometryVM(
    val location: LocationVM,
    val viewport: ViewportVM
)

data class LocationVM(
    val lat: Double,
    val lng: Double
)

data class ViewportVM(
    val northeast: NortheastVM,
    val southwest: SouthwestVM
)

data class NortheastVM(
    val lat: Double,
    val lng: Double
)

data class SouthwestVM(
    val lat: Double,
    val lng: Double
)

data class PlusCodeVM(
    val compoundCode: String,
    val globalCode: String
)

data class OpeningHoursVM(
    val openNow: Boolean
)

data class PhotoVM(
    val height: Int,
    val htmlAttributions: List<String>,
    val photoReference: String,
    val width: Int
)

fun MapPointDM.toVM(): MapPointVM {
    return MapPointVM(
        businessStatus = this.businessStatus,
        formattedAddress = this.formattedAddress,
        geometry = this.geometry.toVM(),
        icon = this.icon,
        iconBackgroundColor = this.iconBackgroundColor,
        iconMaskBaseUri = this.iconMaskBaseUri,
        name = this.name,
        placeId = this.placeId,
        plusCode = this.plusCode.toVM(),
        rating = this.rating,
        reference = this.reference,
        types = this.types,
        userRatingsTotal = this.userRatingsTotal,
        openingHours = this.openingHours.toVM(),
        photos = this.photos.map { it.toVM() }
    )
}

fun GeometryDM.toVM(): GeometryVM {
    return GeometryVM(
        location = this.location.toVM(),
        viewport = this.viewport.toVM()
    )
}

fun MapLocationDM.toVM(): LocationVM {
    return LocationVM(
        lat = this.lat,
        lng = this.lng
    )
}

fun ViewportDM.toVM(): ViewportVM {
    return ViewportVM(
        northeast = this.northeast.toVM(),
        southwest = this.southwest.toVM()
    )
}

fun NortheastDM.toVM(): NortheastVM {
    return NortheastVM(
        lat = this.lat,
        lng = this.lng
    )
}

fun SouthwestDM.toVM(): SouthwestVM {
    return SouthwestVM(
        lat = this.lat,
        lng = this.lng
    )
}

fun PlusCodeDM.toVM(): PlusCodeVM {
    return PlusCodeVM(
        compoundCode = this.compoundCode,
        globalCode = this.globalCode
    )
}

fun OpeningHoursDM.toVM(): OpeningHoursVM {
    return OpeningHoursVM(
        openNow = this.openNow
    )
}

fun PhotoDM.toVM(): PhotoVM {
    return PhotoVM(
        height = this.height,
        htmlAttributions = this.htmlAttributions,
        photoReference = this.photoReference,
        width = this.width
    )
}

fun List<MapPointDM>.toVM(): List<MapPointVM> {
    return this.map { it.toVM() }
}
