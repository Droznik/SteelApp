package com.mdrapp.de.data.model.remote.map

import com.google.gson.annotations.SerializedName
import com.mdrapp.de.data.model.remote.baseResponse.mdr.BaseResponse
import com.mdrapp.de.domain.model.GeometryDM
import com.mdrapp.de.domain.model.MapLocationDM
import com.mdrapp.de.domain.model.MapPointDM
import com.mdrapp.de.domain.model.NortheastDM
import com.mdrapp.de.domain.model.OpeningHoursDM
import com.mdrapp.de.domain.model.PhotoDM
import com.mdrapp.de.domain.model.PlusCodeDM
import com.mdrapp.de.domain.model.SouthwestDM
import com.mdrapp.de.domain.model.ViewportDM


data class MapLocationResponse(
    val response: MapPointResponse
): BaseResponse()

data class MapPointResponse(
    val list: List<MapPoint>
): BaseResponse()

data class MapPoint(
    @SerializedName("business_status")
    val businessStatus: String? = null,
    @SerializedName("formatted_address")
    val formattedAddress: String? = null,
    val geometry: Geometry? = null,
    val icon: String? = null,
    @SerializedName("icon_background_color")
    val iconBackgroundColor: String? = null,
    @SerializedName("icon_mask_base_uri")
    val iconMaskBaseUri: String? = null,
    val name: String? = null,
    @SerializedName("place_id")
    val placeId: String? = null,
    @SerializedName("plus_code")
    val plusCode: PlusCode? = null,
    val rating: Double? = null,
    val reference: String? = null,
    val types: List<String>? = null,
    @SerializedName("user_ratings_total")
    val userRatingsTotal: Int? = null,
    @SerializedName("opening_hours")
    val openingHours: OpeningHours? = null,
    val photos: List<Photo>? = null
)

data class Geometry(
    val location: Location? = null,
    val viewport: Viewport? = null
)

data class Location(
    val lat: Double? = null,
    val lng: Double? = null
)

data class Viewport(
    val northeast: Northeast? = null,
    val southwest: Southwest? = null
)

data class Northeast(
    val lat: Double? = null,
    val lng: Double? = null
)

data class Southwest(
    val lat: Double? = null,
    val lng: Double? = null
)

data class PlusCode(
    @SerializedName("compound_code")
    val compoundCode: String? = null,
    @SerializedName("global_code")
    val globalCode: String? = null
)

data class OpeningHours(
    @SerializedName("open_now")
    val openNow: Boolean? = null
)

data class Photo(
    val height: Int,
    @SerializedName("html_attributions")
    val htmlAttributions: List<String>? = null,
    @SerializedName("photo_reference")
    val photoReference: String? = null,
    val width: Int? = null
)

fun MapLocationResponse.toDomain(): List<MapPointDM> {
    return response.list.map { it.toDomain() }
}

fun MapPoint.toDomain(): MapPointDM {
    return MapPointDM(
        businessStatus = this.businessStatus ?: "",
        formattedAddress = this.formattedAddress ?: "",
        geometry = this.geometry?.toDomain()?: GeometryDM(MapLocationDM(0.0, 0.0), ViewportDM(NortheastDM(0.0, 0.0), SouthwestDM(0.0, 0.0)),),
        icon = this.icon ?: "",
        iconBackgroundColor = this.iconBackgroundColor ?: "",
        iconMaskBaseUri = this.iconMaskBaseUri ?: "",
        name = this.name ?: "",
        placeId = this.placeId ?: "",
        plusCode = this.plusCode?.toDomain()?: PlusCodeDM("", ""),
        rating = this.rating ?: 0.0,
        reference = this.reference ?: "",
        types = this.types ?: emptyList(),
        userRatingsTotal = this.userRatingsTotal ?: 0,
        openingHours = this.openingHours?.toDomain()?: OpeningHoursDM(false),
        photos = this.photos?.map { it.toDomain() } ?: emptyList()
    )
}

fun Geometry.toDomain(): GeometryDM {
    return GeometryDM(
        location = this.location?.toDomain()?: MapLocationDM(0.0, 0.0),
        viewport = this.viewport?.toDomain()?: ViewportDM(NortheastDM(0.0, 0.0), SouthwestDM(0.0, 0.0))
    )
}

fun Location.toDomain(): MapLocationDM {
    return MapLocationDM(
        lat = this.lat?:0.0,
        lng = this.lng?:0.0
    )
}

fun Viewport.toDomain(): ViewportDM {
    return ViewportDM(
        northeast = this.northeast?.toDomain()?: NortheastDM(0.0, 0.0  ),
        southwest = this.southwest?.toDomain()?: SouthwestDM(0.0, 0.0)
    )
}

fun Northeast.toDomain(): NortheastDM {
    return NortheastDM(
        lat = this.lat?:0.0,
        lng = this.lng?:0.0
    )
}

fun Southwest.toDomain(): SouthwestDM {
    return SouthwestDM(
        lat = this.lat?:0.0,
        lng = this.lng?:0.0
    )
}

fun PlusCode.toDomain(): PlusCodeDM {
    return PlusCodeDM(
        compoundCode = this.compoundCode ?: "",
        globalCode = this.globalCode ?: ""
    )
}

fun OpeningHours.toDomain(): OpeningHoursDM {
    return OpeningHoursDM(
        openNow = this.openNow?:false
    )
}

fun Photo.toDomain(): PhotoDM {
    return PhotoDM(
        height = this.height,
        htmlAttributions = this.htmlAttributions?.toList() ?: emptyList(),
        photoReference = this.photoReference?:"",
        width = this.width?:0
    )
}