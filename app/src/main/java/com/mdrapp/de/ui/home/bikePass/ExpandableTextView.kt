package com.mdrapp.de.ui.home.bikePass

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mdrapp.de.R
import com.mdrapp.de.ext.noRippleClickable
import com.mdrapp.de.ui.theme.MDRTheme

@Composable
fun ExpandableTextView(
    viewId: String,
    title: String,
    expandedItem: String?,
    isDividerVisible: Boolean,
    onExpand: (String) -> Unit,
    content: @Composable () -> Unit,
) {

    val isExpanded = expandedItem == viewId


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .noRippleClickable { onExpand(if (isExpanded) "" else viewId) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .padding(start = 20.dp, bottom = 8.dp, top = 15.dp)
                .weight(1f),
            text = title,
            color = MDRTheme.colors.titleText,
            style = MDRTheme.typography.title
        )
        Icon(
            painter = if (isExpanded) painterResource(id = R.drawable.ic_arrow_top) else painterResource(
                id = R.drawable.ic_arrow_bottom
            ),
            contentDescription = null,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }

    if (isExpanded) {
        content()
    } else if (isDividerVisible) {
        HorizontalDivider(
            thickness = 1.dp,
            color = MDRTheme.colors.secondaryText,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ExpandableTextViewPreview() {
    MDRTheme {
        //ExpandableTextView(title = "test", content = "")
    }
}