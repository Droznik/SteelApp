package com.mdrapp.de.ui.home.myBikes.detail.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mdrapp.de.ui.home.myBikes.detail.MdrOrderTrackingStatusVM
import com.mdrapp.de.ui.theme.MDRTheme


@Composable
fun MdrOrderStatusItem(status: MdrOrderTrackingStatusVM) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Box(
            modifier = Modifier
                .heightIn(min = 50.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(50))
                .background(
                    color = if (status.active) MDRTheme.colors.titleText else MDRTheme.colors.primaryBackground,
                    shape = RoundedCornerShape(50)
                )
                .border(
                    width = 1.dp,
                    color = if (status.active) Color.Transparent else MDRTheme.colors.primaryText,
                    shape = RoundedCornerShape(50)
                ).padding(horizontal = 32.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = status.label,
                style = MDRTheme.typography.semiBold,
                fontSize = 18.sp,
                color = if (status.active) MDRTheme.colors.lightText else MDRTheme.colors.primaryText,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        if (status.active) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = status.description,
                style = MDRTheme.typography.regular,
                fontSize = 14.sp,
                color = MDRTheme.colors.primaryText
            )
        }
    }
}

@Preview
@Composable
fun MdrOrderStatusItemPreview() {
    MDRTheme {
        MdrOrderStatusItem(
            status = MdrOrderTrackingStatusVM(
                "",
                "Warte auf Arbeiterfreigabe",
                "Die Radbestellung wird bei mein-dienstrad.de geprüft.",
                active = true
            )
        )
    }
}