package com.mdrapp.de.ui.home.myOffers.list.view

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.mdrapp.de.R
import com.mdrapp.de.ui.home.myOffers.list.model.OfferListItemVM
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.views.BaseOrderItem
import com.mdrapp.de.ui.views.HorizontalTextBlock


@Composable
fun OfferListItem(
    item: OfferListItemVM,
    onClick: (OfferListItemVM) -> Unit,
    onDelete: (OfferListItemVM) -> Unit,
) {
    BaseOrderItem(
        orderNumber = item.brand,
        action = {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_delete),
                contentDescription = null,
                modifier = Modifier.clickable { onDelete(item) }
            )
        },
        onItemClick = { onClick(item) }
    ) {
        HorizontalTextBlock(
            title = stringResource(R.string.offer_item_valid_until),
            value = item.offerValidUntil
        )
        HorizontalTextBlock(
            title = stringResource(R.string.model),
            value = item.model
        )
        HorizontalTextBlock(
            title = stringResource(R.string.total_price),
            value = stringResource(R.string.price_euro, item.totalPrice)
        )
        HorizontalTextBlock(
            title = stringResource(R.string.leasing_rate),
            value = stringResource(R.string.price_euro, item.leasingRate)
        )
        HorizontalTextBlock(
            title = stringResource(R.string.dealer),
            value = item.dealer
        )
    }
}

@Preview
@Composable
private fun OfferListItemPreview() {
    MDRTheme {
        OfferListItem(
            item = OfferListItemVM(
                id = 1,
                offerValidUntil = "30.09.24",
                model = "Model",
                brand = "Brand",
                totalPrice = 1500.0,
                leasingRate = 38.0,
                dealer = "Test",
            ),
            onClick = {},
            onDelete = {}
        )
    }
}