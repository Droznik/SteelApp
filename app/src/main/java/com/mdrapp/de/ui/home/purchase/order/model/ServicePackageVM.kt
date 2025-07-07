package com.mdrapp.de.ui.home.purchase.order.model

import com.mdrapp.de.domain.model.ServicePackageDM

data class ServicePackageVM(
    val id: Long,
    val title: String,
    val price: String,
    val displayTitle: String,
)

fun ServicePackageDM.toVM(): ServicePackageVM  = ServicePackageVM(
    id = id,
    title = title.orEmpty(),
    price = price.orEmpty(),
    displayTitle = "${this.title} ${this.price} €"
)