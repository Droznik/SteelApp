package com.mdrapp.de.ui.auth.onboardingBikeCategories

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mdrapp.de.ui.theme.MDRTheme

@Composable
fun OnboardingBikeItem(category: OnboardingBikeCategoryItemVM) {
    Column(
        modifier = Modifier.height(50.dp).fillMaxWidth().clip(RoundedCornerShape(50)).background(
            color = if (category.isSelected) MDRTheme.colors.titleText else MDRTheme.colors.primaryBackground,
            shape = RoundedCornerShape(50)
        ).border(
            width = 1.dp,
            color = if (category.isSelected) Color.Transparent else MDRTheme.colors.primaryText,
            shape = RoundedCornerShape(50)
        ).clickable { category.isSelected = !category.isSelected },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = category.title.uppercase(),
            style = MDRTheme.typography.semiBold,
            color = if (category.isSelected) MDRTheme.colors.lightText else MDRTheme.colors.primaryText
        )
    }
}

@Preview
@Composable
fun OnboardingCategoryItemPreview() {
    MDRTheme {
        OnboardingBikeItem(
            category = OnboardingBikeCategoryItemVM(
                "",
                "BikeTest",
                false
            )
        )
    }
}