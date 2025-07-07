package com.mdrapp.de.ui.views

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mdrapp.de.R
import com.mdrapp.de.ui.theme.MDRTheme

@Composable
fun CheckBoxRow(
    checked: Boolean,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier.fillMaxWidth(),
    iconSize: Dp = 15.dp,
    topOffset: Dp = 1.dp,
    spaceBetween: Dp = 12.dp,
    isError: Boolean = false,
    onChecked: (Boolean) -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier.clickable { onChecked(!checked) },
        verticalAlignment = Alignment.Top
    ) {
        val icon = when {
            checked -> painterResource(R.drawable.ic_checkbox_checked)
            else -> painterResource(R.drawable.ic_checkbox_unchecked)
        }
        val tint = when {
            isError -> MDRTheme.colors.error
            else -> MDRTheme.colors.titleText
        }

        Icon(
            painter = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier
                .padding(top = topOffset)
                .size(iconSize)
        )
        Spacer(modifier = Modifier.width(spaceBetween))
        content()
    }
}

@Composable
fun RowScope.PrimaryCheckboxText(text: String) {
    Text(
        text = text,
        style = MDRTheme.typography.regular,
        fontSize = 14.sp,
        modifier = Modifier.weight(1f)
    )
}

@Preview
@Composable
private fun CheckBoxRowPreview() {
    MDRTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 36.dp),
            verticalArrangement = Arrangement.spacedBy(36.dp)
        ) {
            CheckBoxRow(checked = true, onChecked = {}) {
                PrimaryCheckboxText(text = "Hiermit bestätige ich folgende Hinweise:\n1. Ich habe die Bestimmungen zur Überlassung des Fahrrads gemäß „KBV Fahrrad\" zur Kenntnis genommen und bin mit ihnen einverstanden.",)
            }
            CheckBoxRow(checked = false, onChecked = {}) {
                PrimaryCheckboxText(text = "Hiermit bestätige ich folgende Hinweise")
            }
            CheckBoxRow(checked = false, isError = true, onChecked = {}) {
                PrimaryCheckboxText(text = "Hiermit bestätige ich folgende Hinweise")
            }
        }
    }
}