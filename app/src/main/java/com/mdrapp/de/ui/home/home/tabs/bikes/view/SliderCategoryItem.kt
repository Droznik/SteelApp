package com.mdrapp.de.ui.home.home.tabs.bikes.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mdrapp.de.R
import com.mdrapp.de.ui.home.home.tabs.bikes.model.SliderCategoryVM
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.util.UIText


@Composable
fun SliderCategoryItem(category: SliderCategoryVM, onItemClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(182.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = MDRTheme.colors.divider, shape = RoundedCornerShape(10.dp))
            .clickable { onItemClick() }
    ) {
        AsyncImage(
            model = when(category.drawable) {
                null -> category.image.ifEmpty { R.drawable.img_temp_category }
                else -> category.drawable
            },
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 11.dp)
        ) {
            Text(
                text = category.article.asString(),
                style = MDRTheme.typography.regular,
                fontSize = 14.sp,
                color = MDRTheme.colors.lightText
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = category.title.asString(),
                style = MDRTheme.typography.medium,
                fontSize = 24.sp,
                color = MDRTheme.colors.lightText
            )
        }
    }
}

@Preview
@Composable
fun SliderCategoryItemPreview() {
    MDRTheme {
        SliderCategoryItem(SliderCategoryVM(UIText.DynamicString("City"), UIText.StringResource(R.string.format_article,39))) {}
    }
}