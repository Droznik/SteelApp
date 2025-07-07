package com.mdrapp.de.ui.home.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.mdrapp.de.R
import com.mdrapp.de.ui.theme.MDRTheme

@Composable
fun StoreMapFaqDialog(onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f),
            color = MDRTheme.colors.primaryBackground
        ) {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                item {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        IconButton(
                            onClick = onDismissRequest,
                            modifier = Modifier.align(Alignment.TopEnd)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close"
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    DialogMainTitle(stringResource(R.string.dealer_map_dialog_main_title))
                    DialogDescription(stringResource(R.string.dealer_map_dialog_main_description))
                    DialogTitle(stringResource(R.string.dealer_map_dialog_specialist_dealers_title))
                    DialogDescription(stringResource(R.string.dealer_map_dialog_dialers_desc))
                    DialogTitle(stringResource(R.string.dealer_map_dialog_choose_bike))
                    DialogDescription(stringResource(R.string.dealer_map_dialog_choose_bike_desc))
                    DialogTitle(stringResource(R.string.dealer_map_dialog_employer_req))
                    DialogDescription(stringResource(R.string.dealer_map_dialog_no_problem))
                    DialogTitle(stringResource(R.string.dealer_map_dialog_pick_up_auth))
                    DialogDescription(stringResource(R.string.dealer_map_dialog_fahrpass_scan))
                    DialogTitle(stringResource(R.string.dealer_map_dialog_inspector))
                    DialogDescription(stringResource(R.string.dealer_map_dialog_fahrenpass))
                    DialogTitle(stringResource(R.string.dealer_map_dialog_damaged))
                    DialogDescription(stringResource(R.string.dealer_map_scan))
                }
            }
        }
    }
}

@Composable
private fun DialogMainTitle(title: String) {
    Text(
        text = title,
        style = MDRTheme.typography.semiBold,
        fontSize = 19.sp,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
private fun DialogTitle(title: String) {
    Text(
        text = title,
        style = MDRTheme.typography.semiBold,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
private fun DialogDescription(description: String) {
    Text(
        text = description,
        color = MDRTheme.colors.secondaryText,
        fontSize = 14.sp,
        style = MDRTheme.typography.regular,
        modifier = Modifier.padding(bottom = 24.dp)
    )
}

@Composable
@Preview(heightDp = 1300)
private fun ScrollableDialogPreview() {
    MDRTheme {
        StoreMapFaqDialog(onDismissRequest = {})
    }
}