package com.mdrapp.de.ui.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mdrapp.de.ui.theme.MDRTheme


@Composable
fun VerticalTextBlock(
    title: String,
    value: String,
    horizontalPadding: Dp = 0.dp
) {
    Text(
        text = title,
        style = MDRTheme.typography.medium,
        fontSize = 16.sp,
        color = MDRTheme.colors.secondaryText,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding)
    )
    Text(
        text = value,
        style = MDRTheme.typography.medium,
        fontSize = 16.sp,
        color = MDRTheme.colors.titleText,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding)
    )
}

@Composable
fun HorizontalTextBlock(
    title: String,
    value: String,
    isDivider: Boolean = false,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier.fillMaxWidth()
) {
    Row(modifier = modifier) {
        Text(
            text = title,
            style = MDRTheme.typography.medium,
            fontSize = 16.sp,
            color = MDRTheme.colors.secondaryText,
        )
        Spacer(modifier = Modifier.width(32.dp))
        Text(
            text = value,
            style = MDRTheme.typography.medium,
            fontSize = 16.sp,
            color = MDRTheme.colors.titleText,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
    if (isDivider) {
        TextBlockDivider()
    }
}

@Composable
fun VerticalTextBlockWithSubtitle(
    title: String,
    subtitle: String,
    value: String,
    isDividerVisible: Boolean = true,
    horizontalPadding: Dp = 0.dp
) {
    Text(
        text = title,
        style = MDRTheme.typography.medium,
        fontSize = 16.sp,
        color = MDRTheme.colors.secondaryText,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding)
    )
    Spacer(Modifier.height(4.dp))
    Text(
        text = subtitle,
        style = MDRTheme.typography.medium,
        fontSize = 16.sp,
        color = MDRTheme.colors.titleText,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding)
    )
    Spacer(Modifier.height(4.dp))
    Text(
        text = value,
        style = MDRTheme.typography.regular,
        fontSize = 14.sp,
        color = MDRTheme.colors.secondaryText,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding)
    )
    if (isDividerVisible) {
        TextBlockDivider()
    }
}

@Preview(showBackground = true)
@Composable
private fun TextPreview() {
    MDRTheme {
        Column(modifier = Modifier.fillMaxWidth()) {
            VerticalTextBlock("Dienstradnutzer", "kurademo-mdrnutzer@baronmobil.com")
            Spacer(modifier = Modifier.height(32.dp))
            HorizontalTextBlock("Vorname", "Max")
        }
    }
}