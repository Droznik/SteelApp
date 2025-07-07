package com.mdrapp.de.ui.views

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mdrapp.de.ui.theme.MDRTheme

@Composable
fun PrimaryHorizontalDivider(
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier.fillMaxWidth()
) {
    HorizontalDivider(modifier, color = MDRTheme.colors.divider)
}

@Composable
fun TextBlockDivider() {
    PrimaryHorizontalDivider(modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp))
}

@Composable
fun TextLineHorizontalDivider(
    text: String,
    height: Dp = 13.dp,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier.fillMaxWidth()
) {
    Box (
        modifier = modifier.height(height),
        contentAlignment = Alignment.Center
    ) {
        PrimaryHorizontalDivider()
        Text(
            text = text,
            color = MDRTheme.colors.divider,
            style = MDRTheme.typography.semiBold,
            fontSize = 11.sp,
            modifier = Modifier
                .background(MDRTheme.colors.primaryBackground)
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
@Preview
fun TextLineHorizontalDividerPreview() {
    MDRTheme {
        TextLineHorizontalDivider("Test")
    }
}