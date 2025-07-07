package com.mdrapp.de.ui.home.home.tabs.bikes.view

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mdrapp.de.R
import com.mdrapp.de.ui.home.home.tabs.bikes.model.SliderProductVM
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.util.debugPlaceholder


@Composable
fun SliderProductItem(product: SliderProductVM, onItemClick: (Long) -> Unit) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable { onItemClick(product.id) }
    ) {
        Box(
            modifier = Modifier
                .size(width = 272.dp, height = 250.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(
                    width = 1.dp,
                    color = MDRTheme.colors.divider,
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ){
            AsyncImage(
                model = product.imgUrl.ifEmpty { R.drawable.img_temp_home_product },
                placeholder = debugPlaceholder(R.drawable.img_temp_home_product),
                contentDescription = null,
                modifier = Modifier
                    .size(width = 264.dp, height = 214.dp)
                    .clip(RoundedCornerShape(10.dp)),
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = product.category,
            style = MDRTheme.typography.regular,
            fontSize = 14.sp,
            color = MDRTheme.colors.secondaryText
        )
        Text(
            text = product.displayName,
            style = MDRTheme.typography.medium,
            fontSize = 16.sp,
            color = MDRTheme.colors.titleText,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
fun SliderProductItemPreview() {
    MDRTheme {
        SliderProductItem(SliderProductVM(1,"XTRA WATT EVO+", "E-Urban Bike","")) {}
    }
}