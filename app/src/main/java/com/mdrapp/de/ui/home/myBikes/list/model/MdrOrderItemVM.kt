package com.mdrapp.de.ui.home.myBikes.list.model

import com.mdrapp.de.domain.model.MdrOrderItemDM

data class MdrOrderItemVM(
    val id: Long,
    val orderNumber: String,
    val name: String,
    val servicePackage: String = "",
    val leasingEnd: String = "",
    val mdrStatus: String = "",
    val dealer: String = ""
)

fun MdrOrderItemDM.toVM(): MdrOrderItemVM = MdrOrderItemVM(
    id = id,
    orderNumber = orderNumber.orEmpty(),
    name = name.orEmpty(),
    servicePackage = servicePackage.orEmpty(),
    leasingEnd = leasingEnd.orEmpty(),
    mdrStatus = mdrStatus.orEmpty(),
    dealer = dealer.orEmpty(),
)