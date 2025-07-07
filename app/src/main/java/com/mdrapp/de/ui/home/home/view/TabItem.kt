package com.mdrapp.de.ui.home.home.view

import androidx.annotation.StringRes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.mdrapp.de.ext.noRippleClickable
import com.mdrapp.de.ui.theme.MDRTheme


@Composable
fun TabItem(
    isSelected: Boolean,
    @StringRes stringId: Int,
    allCaps: Boolean,
    onClick: () -> Unit
) {
    Text(
        text = if (allCaps) stringResource(stringId).uppercase() else stringResource(stringId),
        color = if (isSelected) MDRTheme.colors.titleText else MDRTheme.colors.primaryText,
        style = if (isSelected) MDRTheme.typography.semiBold else MDRTheme.typography.regular,
        fontSize = 14.sp,
        modifier = Modifier.noRippleClickable { onClick() }
    )
}