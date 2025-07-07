package com.mdrapp.de.ui.home.myBikes.list

import androidx.lifecycle.SavedStateHandle
import com.mdrapp.de.common.viewmodel.ContractViewModel
import com.mdrapp.de.domain.repository.MyBikesRepository
import com.mdrapp.de.navigation.MY_MDR_BIKES_TYPE_ARG
import com.mdrapp.de.navigation.MyMdrBikesGraph
import com.mdrapp.de.navigation.NavEvent
import com.mdrapp.de.ui.home.myBikes.MyMdrBikesType
import com.mdrapp.de.ui.home.myBikes.list.model.MdrOrderItemVM
import com.mdrapp.de.ui.home.myBikes.list.model.toVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MyMdrBikesViewModel @Inject constructor(
    private val myBikesRepo: MyBikesRepository,
    savedStateHandle: SavedStateHandle
) : ContractViewModel<MyMdrBikesState, MyMdrBikesEvent>() {

    private val type = MyMdrBikesType.valueOf(savedStateHandle[MY_MDR_BIKES_TYPE_ARG]!!)

    override val initialState = MyMdrBikesState(type)

    init {
        loadOrders()
    }

    override fun onEvent(event: MyMdrBikesEvent) {
        when(event) {
            is MyMdrBikesEvent.ShowDetail -> showDetail(event.order)
        }
    }

    private fun loadOrders() = simpleLaunch {
        val orders = withContext(Dispatchers.IO) { myBikesRepo.getMdrOrders(type.param).map { it.toVM() } }

        _state.update { it.copy(orders = orders) }
    }

    private fun showDetail(order: MdrOrderItemVM) {
        navigate(NavEvent.To(MyMdrBikesGraph.Detail.createRoute(order.id, order.orderNumber, type)))
    }
}