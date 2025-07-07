package com.mdrapp.de.ui.auth.login.forgotPassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mdrapp.de.R
import com.mdrapp.de.common.fragment.BaseFragment
import com.mdrapp.de.ui.auth.dialogs.EmailUnverifiedDialog
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.views.LogoTopBar
import com.mdrapp.de.ui.views.PrimaryButton
import com.mdrapp.de.ui.views.PrimaryOutlinedTextField
import com.mdrapp.de.ui.views.PrimaryTextFieldPlaceholder
import com.mdrapp.de.ui.views.TextLineHorizontalDivider

@Composable
fun ForgotPasswordFragment(navController: NavController, vm: ForgotPasswordViewModel) {
    BaseFragment(vm, navController) {
        val onEvent = remember { vm::onEvent }
        val state by vm.state.collectAsState()

        ForgotPasswordContent(onEvent = onEvent, state = state)
    }
}

@Composable
fun ForgotPasswordContent(
    state: ForgotPasswordState,
    onEvent: (ForgotPasswordEvent) -> Unit
) {
    if (state.isEmailVerifiedDialogVisible) {
        EmailUnverifiedDialog(
            onDismiss = { onEvent(ForgotPasswordEvent.OnCloseDialogBtnCLicked) },
            onEvent = { onEvent(ForgotPasswordEvent.OnVerifyEmailBtnClicked) }
        )
    }
    Column {
        LogoTopBar()
        Spacer(Modifier.height(28.dp))
        Title(title = stringResource(id = R.string.forgot_password_title))
        Spacer(Modifier.height(19.5.dp))
        Description(description = stringResource(id = R.string.forgot_password_description))
        Spacer(Modifier.height(16.5.dp))
        PrimaryOutlinedTextField(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            value = state.email,
            singleLine = true,
            onValueChange = { onEvent(ForgotPasswordEvent.OnEmailChange(it)) },
            isError = false,
            label = { PrimaryTextFieldPlaceholder(R.string.email, isRequired = true) },
        )
        Spacer(Modifier.weight(1f))
        PrimaryButton(modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(), text = stringResource(id = R.string.forgot_password_reset)) {
            onEvent(ForgotPasswordEvent.OnResetPasswordBtnClick) }
        Spacer(Modifier.height(21.dp))
        TextLineHorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp), text = stringResource(id = R.string.forgot_password_do_you_have_an_account))
        Spacer(Modifier.height(21.dp))
        PrimaryButton(modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(), text = stringResource(id = R.string.forgot_password_to_register)) {
            onEvent(ForgotPasswordEvent.OnLoginBtnClicked)
        }
        Spacer(Modifier.height(42.dp))
    }
}

@Composable
private fun Title(title: String) {
    Text(
        modifier = Modifier.padding(horizontal = 20.dp),
        text = title,
        color = MDRTheme.colors.titleText,
        style = MDRTheme.typography.title
    )
}

@Composable
private fun Description(description: String) {
    Text(
        modifier = Modifier.padding(horizontal = 20.dp),
        text = description,
        color = MDRTheme.colors.primaryText,
        style = MDRTheme.typography.description
    )
}

@Preview(showBackground = true)
@Composable
private fun LoginContentPreview() {
    MDRTheme {
        ForgotPasswordContent(ForgotPasswordState()) { }
    }
}