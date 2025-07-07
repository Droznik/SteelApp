package com.mdrapp.de.ui.home.myOffers.detail.view

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mdrapp.de.R
import com.mdrapp.de.ui.home.myOffers.detail.model.OfferDetailAccessoryVM
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.views.HorizontalTextBlock


@Composable
fun OfferDetailAccessoryItem(item: OfferDetailAccessoryVM) {
    HorizontalTextBlock(stringResource(R.string.designation), item.title, true)
    HorizontalTextBlock(stringResource(R.string.amount), item.quantity.toString(), true)
    HorizontalTextBlock(
        title = stringResource(R.string.net_selling_price),
        value = stringResource(R.string.price_euro, item.netSellingPrice),
        isDivider = true
    )
    HorizontalTextBlock(
        title = stringResource(R.string.gross_sales_price),
        value = stringResource(R.string.price_euro, item.grossSalesPrice),
        isDivider = true
    )
    HorizontalTextBlock(
        title = stringResource(R.string.uvp),
        value = stringResource(R.string.price_euro, item.uvp),
    )
}

@Preview
@Composable
private fun OfferDetailAccessoryItemPreview() {
    MDRTheme {
        Column {
            OfferDetailAccessoryItem(
                item = OfferDetailAccessoryVM(
                    title = "",
                    quantity = 1,
                    netSellingPrice = 0.0,
                    grossSalesPrice = 0.0,
                    salesPriceGrossIncl = "",
                    uvp = 0.0,
                )
            )
        }
    }
}