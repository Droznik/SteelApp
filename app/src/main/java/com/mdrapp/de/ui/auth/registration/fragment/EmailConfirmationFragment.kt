package com.mdrapp.de.ui.auth.registration.fragment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mdrapp.de.R
import com.mdrapp.de.common.fragment.BaseFragment
import com.mdrapp.de.ui.auth.registration.RegistrationEvent
import com.mdrapp.de.ui.auth.registration.RegistrationViewModel
import com.mdrapp.de.ui.splash.HideSystemsBars
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.views.LogoTopBar
import com.mdrapp.de.ui.views.PrimaryButton
import com.mdrapp.de.ui.views.TextLineHorizontalDivider

@Composable
fun EmailConfirmationFragment(navController: NavController, vm: RegistrationViewModel) {
    BaseFragment(vm, navController) {
        val onEvent = remember { vm::onEvent }

        EmailConfirmationContent(onEvent)
    }
}

@Composable
private fun EmailConfirmationContent(
    onEvent: (RegistrationEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            LogoTopBar(0.dp)
            Spacer(modifier = Modifier.height(28.dp))
            EmailConfirmTitle()
            Spacer(modifier = Modifier.height(16.dp))
            EmailConfirmDescription()

        }
        EmailConfirmButton(
            stringResource(R.string.check_email_verification),
            onClick = { onEvent(RegistrationEvent.OnValidateEmail) })
        Spacer(modifier = Modifier.height(22.dp))
        TextLineHorizontalDivider(
            text = stringResource(R.string.didnt_get_email),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(22.dp))
        EmailConfirmButton(
            stringResource(R.string.resend_email),
            onClick = { onEvent(RegistrationEvent.OnResendEmail) })
        Spacer(modifier = Modifier.height(42.dp))

    }
}

@Composable
private fun EmailConfirmTitle() {
    Text(
        text = stringResource(R.string.thanks_for_registering),
        style = MDRTheme.typography.semiBold,
        fontSize = 34.sp
    )
}

@Composable
private fun EmailConfirmDescription() {
    Text(
        text = stringResource(R.string.thanks_for_registering_subtext) +
                "\n" +
                stringResource(R.string.thanks_for_registering_subtext_two),
        style = MDRTheme.typography.regular,
        fontSize = 16.sp
    )
}

@Composable
private fun EmailConfirmButton(text: String, onClick: () -> Unit) {
    PrimaryButton(
        text = text, onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
    )

}

@Composable
@Preview(showBackground = true)
private fun EmailConfirmationPreview() {
    MDRTheme {
        EmailConfirmationContent(onEvent = {})
    }
}