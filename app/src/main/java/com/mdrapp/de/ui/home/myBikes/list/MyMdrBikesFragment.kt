package com.mdrapp.de.ui.home.myBikes.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mdrapp.de.R
import com.mdrapp.de.common.fragment.BaseFragment
import com.mdrapp.de.ui.home.myBikes.MyMdrBikesType
import com.mdrapp.de.ui.home.myBikes.list.model.MdrOrderItemVM
import com.mdrapp.de.ui.home.myBikes.list.view.MyMdrBikeItem
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.views.TitleTopBar


@Composable
fun MyMdrBikesFragment(navController: NavController, vm: MyMdrBikesViewModel) {
    BaseFragment(vm, navController) {
        val state by vm.state.collectAsState()
        val onEvent = remember { vm::onEvent }

        MyMdrBikesContent(state, onEvent)
    }
}

@Composable
fun MyMdrBikesContent(
    state: MyMdrBikesState,
    onEvent: (MyMdrBikesEvent) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TitleTopBar(
            title = when(state.type) {
                MyMdrBikesType.ORDERS -> stringResource(R.string.my_mdr_bikes_toolbar_orders_title)
                MyMdrBikesType.BIKES -> stringResource(R.string.my_mdr_bikes_toolbar_bikes_title)
            }
        )
        OrderList(state.orders, state.type, onEvent)
    }
}

@Composable
private fun ColumnScope.OrderList(
    orders: List<MdrOrderItemVM>,
    type: MyMdrBikesType,
    onEvent: (MyMdrBikesEvent) -> Unit
) {
    LazyColumn(
        modifier = Modifier.weight(1f),
        contentPadding = PaddingValues(bottom = 36.dp)
    ) {
        items(orders) { item ->
            MyMdrBikeItem(item, type) { onEvent(MyMdrBikesEvent.ShowDetail(it)) }
        }
    }
}

@Preview
@Composable
fun MyMdrBikesPreview() {
    MDRTheme {
        MyMdrBikesContent(
            state = MyMdrBikesState(
                type = MyMdrBikesType.BIKES,
                orders = listOf(
                    MdrOrderItemVM(
                        id = 1,
                        orderNumber = "MDR2249427",
                        name = "Rose LP Sportose Bikes"
                    ),
                    MdrOrderItemVM(
                        id = 2,
                        orderNumber = "MDR2249427",
                        name = "Rose LP Sportose Bikes"
                    ),
                )
            ),
            onEvent = {}
        )
    }
}