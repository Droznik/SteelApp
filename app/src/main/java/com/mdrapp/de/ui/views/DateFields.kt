package com.mdrapp.de.ui.views

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mdrapp.de.ui.theme.MDRTheme

@Composable
fun PrimaryDateField(
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier.fillMaxWidth(),
    @StringRes label: Int,
    date: String,
    height: Dp = 46.dp,
    isError: Boolean = false,
    isRequired: Boolean = false,
    readOnly: Boolean = false,
    onDatePicked: (Long) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) PrimaryDatePicker(
        onDismissRequest = { showDatePicker = false },
        onDatePicked = { onDatePicked(it) }
    )
    Box {
        PrimaryOutlinedTextField(
            modifier = modifier,
            value = date,
            onValueChange = {},
            label = { PrimaryTextFieldPlaceholder(label, isRequired = isRequired) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    tint = MDRTheme.colors.secondaryText,
                    modifier = Modifier.padding(end = 22.dp)
                )
            },
            height = height,
            singleLine = true,
            isError = isError,
            readOnly = true
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(CircleShape)
                .clickable(enabled = !readOnly) { showDatePicker = true }
        )
    }
}