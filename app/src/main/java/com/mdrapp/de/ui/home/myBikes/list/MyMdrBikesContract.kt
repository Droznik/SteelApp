package com.mdrapp.de.ui.home.myBikes.list

import com.mdrapp.de.ui.home.myBikes.MyMdrBikesType
import com.mdrapp.de.ui.home.myBikes.list.model.MdrOrderItemVM

data class MyMdrBikesState(
    val type: MyMdrBikesType,
    val orders: List<MdrOrderItemVM> = emptyList()
)

sealed interface MyMdrBikesEvent {
    data class ShowDetail(val order: MdrOrderItemVM) : MyMdrBikesEvent
}
