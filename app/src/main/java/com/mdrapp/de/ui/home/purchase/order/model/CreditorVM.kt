package com.mdrapp.de.ui.home.purchase.order.model

import com.mdrapp.de.domain.model.CreditorDM

data class CreditorVM(
    val id: Long,
    val name: String
)

fun CreditorDM.toVM(): CreditorVM = CreditorVM(id, name.orEmpty())
