package com.mdrapp.de.ui.home.purchase.order.model

import androidx.annotation.StringRes
import com.mdrapp.de.R
import kotlinx.collections.immutable.persistentListOf

data class LagerStatusVM(
    @StringRes val titleId: Int,
    val value: Boolean
)

val lagerStatuses = persistentListOf(
    LagerStatusVM(R.string.purchase_lager_status_true, true),
    LagerStatusVM(R.string.purchase_lager_status_false, false)
)
