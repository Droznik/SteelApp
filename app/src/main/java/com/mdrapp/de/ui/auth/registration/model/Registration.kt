package com.mdrapp.de.ui.auth.registration.model

import com.mdrapp.de.domain.model.auth.registration.BranchDM
import com.mdrapp.de.domain.model.auth.registration.CompanyDM
import com.mdrapp.de.domain.model.auth.registration.CompanyDataDM
import com.mdrapp.de.domain.model.auth.registration.CorporationDM
import com.mdrapp.de.domain.model.auth.registration.LocationDM
import com.mdrapp.de.domain.model.auth.registration.LocationDataDM

data class LocationDataVM(
    val locations : List<LocationVM?>
)

data class LocationVM(
    val active: Boolean?,
    val corporation: CorporationVM?,
    val id: Int?,
    val title: String?
)

data class CorporationVM(
    val active: Boolean?,
    val branch: BranchVM?,
    val id: Int?,
    val title: String?
)

data class BranchVM(
    val active: Boolean?,
    val company: CompanyVM?,
    val id: Int?,
    val title: String?
)

data class CompanyVM(
    val active: Boolean?,
    val id: Int?,
    val title: String?
)

data class CompanyDataVM(
    val id: Int?,
    val title: String?,
    val address: String?,
    val postCode: String?,
    val city: String?,
    val phoneNumber: String?,
)

fun CompanyDataDM.toVM(): CompanyDataVM {
    return CompanyDataVM(
        id = this.id,
        title = this.title,
        address = this.address,
        postCode = this.postcode,
        city = this.city,
        phoneNumber = this.phoneNumber
    )
}

fun LocationDataDM.toVM(): LocationDataVM {
    return LocationDataVM(
        locations = this.locations.map { it?.toVM() }
    )
}

fun LocationDM.toVM(): LocationVM {
    return LocationVM(
        active = this.active,
        corporation = this.corporation?.toVM(),
        id = this.id,
        title = this.title
    )
}

fun CorporationDM.toVM(): CorporationVM {
    return CorporationVM(
        active = this.active,
        branch = this.branch?.toVM(),
        id = this.id,
        title = this.title
    )
}

fun BranchDM.toVM(): BranchVM {
    return BranchVM(
        active = this.active,
        company = this.company?.toVM(),
        id = this.id,
        title = this.title
    )
}

fun CompanyDM.toVM(): CompanyVM {
    return CompanyVM(
        active = this.active,
        id = this.id,
        title = this.title
    )
}