package com.mdrapp.de.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.mdrapp.de.ui.theme.MDRTheme


@Composable
fun PrimarySnackBar(data: SnackbarData) {
    SnackBarContent(data.visuals.message, data::dismiss)
}

@Composable
private fun SnackBarContent(message: String, onDismiss: () -> Unit) {
    Row(
        modifier = Modifier
            .zIndex(550f)
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = MDRTheme.colors.snackBarBackground)
            .clickable(enabled = false) {}
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            style = MDRTheme.typography.medium,
            text = message,
            color = MDRTheme.colors.snackBarText,
            fontSize = 14.sp
        )
        Icon(
            modifier = Modifier
                .padding(start = 16.dp)
                .clickable { onDismiss() },
            imageVector = Icons.Default.Close,
            contentDescription = null,
            tint = MDRTheme.colors.snackBarText
        )
    }
}

@Preview
@Composable
fun SnackBarPreview() {
    MDRTheme {
        SnackBarContent(message = "Hello World!") {}
    }
}