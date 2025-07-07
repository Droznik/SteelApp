package com.mdrapp.de.ui.auth.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mdrapp.de.R
import com.mdrapp.de.ui.auth.login.LoginEvent
import com.mdrapp.de.ui.theme.MDRTheme

@Composable
fun EmailUnverifiedDialog(
    onDismiss: () -> Unit,
    onEvent: (LoginEvent) -> Unit
) {
    AlertDialog(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = onDismiss,
        containerColor = MDRTheme.colors.primaryBackground,
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopEnd
            ) {
                Icon(
                    modifier = Modifier.clickable { onDismiss() },
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = null
                )
            }
            Column(modifier = Modifier.padding(top = 30.dp)) {
                Text(
                    text = stringResource(id = R.string.email_unverified_dialog_title),
                    style = MDRTheme.typography.medium,
                    color = MDRTheme.colors.titleText,
                    fontSize = 20.sp
                )
                Spacer(Modifier.height(50.dp))
                Text(
                    text = stringResource(id = R.string.email_unverified_dialog_subtitle),
                    style = MDRTheme.typography.medium,
                    color = MDRTheme.colors.titleText,
                    fontSize = 16.sp
                )
                Spacer(Modifier.height(28.dp))
                SendVerificationMessage(onEvent)
            }
        },
        confirmButton = {}
    )
}

@Composable
private fun SendVerificationMessage(onEvent: (LoginEvent) -> Unit) {
    ClickableText(
        text = buildAnnotatedString {
            withStyle(SpanStyle(fontSize = 14.sp, color = MDRTheme.colors.titleText)) {
                append(stringResource(id = R.string.email_unverified_dialog_did_not_get_anything))
            }
            withStyle(SpanStyle(fontSize = 14.sp, color = MDRTheme.colors.linkText)) {
                append(stringResource(id = R.string.email_unverified_dialog_resend_btn))
            }
        },
        onClick = {
            onEvent(LoginEvent.OnVerifyEmailBtnClicked)
        },
        style = MDRTheme.typography.semiBold,
    )
}

@Preview
@Composable
private fun CustomDialogPreview() {
    MDRTheme {
        EmailUnverifiedDialog(
            onEvent = {},
            onDismiss = {}
        )
    }
}