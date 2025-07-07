package com.mdrapp.de.data.model.remote.registration

import com.google.gson.annotations.SerializedName
import com.mdrapp.de.data.model.remote.baseResponse.mdr.BaseResponse
import com.mdrapp.de.domain.model.auth.registration.BranchDM
import com.mdrapp.de.domain.model.auth.registration.CompanyDM
import com.mdrapp.de.domain.model.auth.registration.CorporationDM
import com.mdrapp.de.domain.model.auth.registration.LocationDM
import com.mdrapp.de.domain.model.auth.registration.LocationDataDM

data class ShopCodeResponse(
    @SerializedName("response")
    val response: LocationData? = null,
): BaseResponse()

data class LocationData(
    @SerializedName("locations")
    val locations: List<Location?>? = null
)

data class Location(
    @SerializedName("active")
    val active: Boolean? = false,
    @SerializedName("corporation")
    val corporation: Corporation? = null,
    @SerializedName("corporation_id")
    val corporationId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String? = ""
)

data class Company(
    @SerializedName("active")
    val active: Boolean? = false,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String? = ""
)

data class Branch(
    @SerializedName("active")
    val active: Boolean? = false,
    @SerializedName("company")
    val company: Company? = null,
    @SerializedName("company_id")
    val companyId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String? = ""
)

data class Corporation(
    @SerializedName("active")
    val active: Boolean? = false,
    @SerializedName("branch")
    val branch: Branch? = null,
    @SerializedName("branch_id")
    val branchId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String? = ""
)

fun ShopCodeResponse.toDomain(): LocationDataDM = LocationDataDM(
    locations = response?.locations?.map { it?.toDomain() }.orEmpty()
)

fun Location.toDomain(): LocationDM = LocationDM(
    active = active,
    corporation = corporation?.toDomain(),
    id = id,
    title = title
)

fun Corporation.toDomain(): CorporationDM = CorporationDM(
    active = active,
    branch = branch?.toDomain(),
    id = id,
    title = title
)

fun Branch.toDomain(): BranchDM = BranchDM(
    active = active,
    company = company?.toDomain(),
    id = id,
    title = title
)

fun Company.toDomain(): CompanyDM = CompanyDM(
    active = active,
    id = id,
    title = title
)