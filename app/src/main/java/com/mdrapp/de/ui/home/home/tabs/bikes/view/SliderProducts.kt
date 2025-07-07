package com.mdrapp.de.ui.home.home.tabs.bikes.view

import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mdrapp.de.R
import com.mdrapp.de.ui.home.home.tabs.bikes.model.SliderProductVM
import com.mdrapp.de.ui.theme.MDRTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList


@Composable
fun SliderProducts(
    @StringRes title: Int,
    @StringRes actionTitle: Int,
    products: ImmutableList<SliderProductVM>,
    onItemClick: (Long) -> Unit,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(title),
                style = MDRTheme.typography.semiBold,
                fontSize = 19.sp,
                color = MDRTheme.colors.primaryText,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            )
            Box(
                modifier = Modifier
                    .height(26.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .border(
                        width = 1.dp,
                        color = MDRTheme.colors.appBlue,
                        shape = RoundedCornerShape(30.dp)
                    )
                    .padding(horizontal = 16.dp)
                    .clickable { onActionClick() },
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = stringResource(actionTitle),
                    style = MDRTheme.typography.regular,
                    fontSize = 14.sp,
                    color = MDRTheme.colors.appBlue,
                )
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(end = 32.dp, start = 16.dp)
        ) {
            items(
                items = products
            ) { product ->
                SliderProductItem(product = product, onItemClick = { onItemClick(it) })
            }
        }
    }
}

@Preview
@Composable
private fun SliderProductsPreview() {
    MDRTheme {
        SliderProducts(
            title = R.string.special_promotions,
            actionTitle = R.string.all_bikes,
            products = List(5) { index ->
                SliderProductVM(
                    index.toLong(),
                    "XTRA WATT EVO+",
                    "E-Urban Bike",
                    ""
                )
            }.toPersistentList(),
            onItemClick = {},
            onActionClick = {}
        )
    }
}