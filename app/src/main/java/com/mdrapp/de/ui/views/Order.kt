package com.mdrapp.de.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mdrapp.de.ui.theme.MDRTheme


@Composable
fun BaseOrderItem(
    orderNumber: String,
    action: (@Composable RowScope.() -> Unit)? = null,
    onItemClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .let {
                if (onItemClick != null) it.clickable { onItemClick() } else it
            }
            .padding(top = 15.dp, bottom = 6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = orderNumber,
                style = MDRTheme.typography.title,
                color = MDRTheme.colors.titleText,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .weight(1f)
            )
            if (action != null) {
                action()
                Spacer(modifier = Modifier.width(20.dp))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        PrimaryHorizontalDivider()
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            content()
            PrimaryHorizontalDivider()
        }
    }
}

@Preview
@Composable
private fun BaseOrderItemPreview() {
    MDRTheme {
        BaseOrderItem(orderNumber = "MDR2249427") {
            HorizontalTextBlock(title = "Serviceart", value = "Inspektion / UVV")
            HorizontalTextBlock(title = "Status", value = "Verwendet")
            HorizontalTextBlock(title = "Zeitraum", value = "01.01.2023 - 31.04.2024")
        }
    }
}