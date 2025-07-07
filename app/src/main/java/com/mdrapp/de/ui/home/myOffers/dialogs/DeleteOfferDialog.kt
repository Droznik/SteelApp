package com.mdrapp.de.ui.home.myOffers.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.mdrapp.de.R
import com.mdrapp.de.ext.noRippleClickable
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.views.PrimaryButton


@Composable
fun DeleteOfferDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(color = MDRTheme.colors.primaryBackground)
        ) {
            Box(
                modifier = Modifier
                    .noRippleClickable { onDismissRequest() }
                    .align(Alignment.End)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = MDRTheme.colors.titleText,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(30.dp)
                )
            }
            Text(
                text = stringResource(R.string.delete_offer_desc),
                style = MDRTheme.typography.semiBold,
                fontSize = 19.sp,
                color = MDRTheme.colors.titleText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(100.dp))
            PrimaryButton(
                text = stringResource(R.string.confirm),
                height = 43.dp,
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 24.dp)
            ) {
                onDismissRequest()
                onConfirm()
            }
        }
    }
}

@Preview
@Composable
private fun DeleteOfferDialogPreview() {
    MDRTheme {
        DeleteOfferDialog(onDismissRequest = {}) {}
    }
}