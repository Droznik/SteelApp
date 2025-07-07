package com.mdrapp.de.ui.home.myBikes.list.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mdrapp.de.R
import com.mdrapp.de.ui.home.myBikes.MyMdrBikesType
import com.mdrapp.de.ui.home.myBikes.list.model.MdrOrderItemVM
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.views.BaseOrderItem
import com.mdrapp.de.ui.views.HorizontalTextBlock

@Composable
fun MyMdrBikeItem(item: MdrOrderItemVM, type: MyMdrBikesType, onClick: (MdrOrderItemVM) -> Unit) {
    BaseOrderItem(
        orderNumber = item.orderNumber,
        onItemClick = { onClick(item) }
    ) {
        when(type) {
            MyMdrBikesType.BIKES -> {
                HorizontalTextBlock(title = stringResource(R.string.mdrStatus), value = item.mdrStatus)
                HorizontalTextBlock(title = stringResource(R.string.bicycle), value = item.name)
                HorizontalTextBlock(title = stringResource(R.string.service_package), value = item.servicePackage)
                HorizontalTextBlock(title = stringResource(R.string.leasing_end), value = item.leasingEnd)
            }
            MyMdrBikesType.ORDERS -> {
                HorizontalTextBlock(title = stringResource(R.string.mdrStatus), value = item.mdrStatus)
                HorizontalTextBlock(title = stringResource(R.string.bicycle), value = item.name)
                HorizontalTextBlock(title = stringResource(R.string.my_mdr_bikes_order_dealer), value = item.dealer)
            }
        }
    }
}

@Preview
@Composable
private fun MyMdrBikeItemPreview() {
    MDRTheme {
        MyMdrBikeItem(
            item = MdrOrderItemVM(
                id = 1,
                orderNumber = "MDR2249427",
                name = "Rose LP Sportose Bikes"
            ),
            type = MyMdrBikesType.BIKES,
            onClick = {}
        )
    }
}