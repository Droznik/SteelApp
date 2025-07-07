package com.mdrapp.de.ui.views

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.mdrapp.de.ui.theme.MDRTheme


@Composable
fun Loader(
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier.fillMaxSize(),
    size: Dp = 56.dp,
    strokeWidth: Dp = 8.dp,
    color: Color = MDRTheme.colors.primaryLoader
) {
    Box(
        modifier = modifier
            .zIndex(500f)
            .clickable(false) {}
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .size(size),
            color = color,
            strokeWidth = strokeWidth
        )
    }
}