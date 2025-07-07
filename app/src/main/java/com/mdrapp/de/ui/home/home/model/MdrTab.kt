package com.mdrapp.de.ui.home.home.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable

data class MdrTab(
    @StringRes val title: Int,
    val screen: @Composable () -> Unit
)
