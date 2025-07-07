package com.mdrapp.de.ui.views

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.mdrapp.de.R
import com.mdrapp.de.ui.theme.MDRTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimaryDatePicker(
    onDismissRequest: () -> Unit,
    onDatePicked: (Long) -> Unit
) {
    val datePickerState = rememberDatePickerState()
    val confirmEnabled = remember {
        derivedStateOf { datePickerState.selectedDateMillis != null }
    }
    val buttonColors = ButtonDefaults.buttonColors().copy(
        contentColor = MDRTheme.colors.appGreen,
        containerColor = MDRTheme.colors.primaryBackground,
        disabledContainerColor = MDRTheme.colors.primaryBackground
    )

    DatePickerDialog(
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(
                onClick =  {
                    datePickerState.selectedDateMillis?.let {
                        onDatePicked(it)
                        onDismissRequest()
                    }
                },
                enabled = confirmEnabled.value,
                colors = buttonColors
            ) {
                Text(
                    text = stringResource(R.string.save).uppercase(),
                    style = MDRTheme.typography.semiBold,
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick =  { onDismissRequest() },
                colors = buttonColors
            ) {
                Text(
                    text = stringResource(R.string.cancel).uppercase(),
                    style = MDRTheme.typography.semiBold,
                )
            }
        },
        colors = DatePickerDefaults.colors().copy(
            containerColor = MDRTheme.colors.primaryBackground,
        )
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors().copy(
                selectedDayContainerColor = MDRTheme.colors.appGreen,
                todayContentColor = MDRTheme.colors.appGreen,
                todayDateBorderColor = MDRTheme.colors.appGreen,
                dateTextFieldColors = primaryOutlinedTextFieldColors()
            )
        )
    }
}