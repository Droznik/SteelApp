package com.mdrapp.de.ui.home.myBikes.detail.view

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mdrapp.de.R
import com.mdrapp.de.ui.home.myBikes.detail.MdrOrderAccessoryVM
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.views.HorizontalTextBlock


@Composable
fun MdrOrderAccessoryItem(item: MdrOrderAccessoryVM) {
    HorizontalTextBlock(stringResource(R.string.designation), item.name, true)
    HorizontalTextBlock(stringResource(R.string.amount), item.quantity.toString(), true)
    HorizontalTextBlock(
        title = stringResource(R.string.net_selling_price),
        value = stringResource(R.string.price_euro, item.net),
        isDivider = true
    )
    HorizontalTextBlock(
        title = stringResource(R.string.gross_sales_price),
        value = stringResource(R.string.price_euro, item.gross),
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
            MdrOrderAccessoryItem(
                item = MdrOrderAccessoryVM(
                    id = 0,
                    name = "",
                    quantity = 1,
                    net = 0.0,
                    gross = 0.0,
                    uvp = 0.0,
                )
            )
        }
    }
}