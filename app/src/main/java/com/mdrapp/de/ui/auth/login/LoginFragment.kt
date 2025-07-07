package com.mdrapp.de.ui.auth.login

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mdrapp.de.R
import com.mdrapp.de.common.fragment.BaseFragment
import com.mdrapp.de.ext.noRippleClickable
import com.mdrapp.de.ui.auth.dialogs.EmailUnverifiedDialog
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.theme.interFontFamily
import com.mdrapp.de.ui.views.LogoTopBar
import com.mdrapp.de.ui.views.PrimaryButton
import com.mdrapp.de.ui.views.PrimaryOutlinedPasswordTextField
import com.mdrapp.de.ui.views.PrimaryOutlinedTextField
import com.mdrapp.de.ui.views.PrimaryTextFieldPlaceholder
import com.mdrapp.de.ui.views.TextLineHorizontalDivider

@Composable
fun LoginFragment(navController: NavController, vm: LoginViewModel) {
    BaseFragment(vm, navController) {
        val onEvent = remember { vm::onEvent }
        val state by vm.state.collectAsState()

        LoginContent(onEvent = onEvent, state = state)
    }
}

@Composable
fun LoginContent(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit
) {
    Column {
        if (state.isEmailVerifiedDialogVisible) {
            EmailUnverifiedDialog(
                onDismiss = { onEvent(LoginEvent.OnCloseDialogBtnCLicked) },
                onEvent = { onEvent(LoginEvent.OnVerifyEmailBtnClicked) }
            )
        }
        LogoTopBar()
        Spacer(Modifier.height(27.dp))
        Title(title = stringResource(id = R.string.login_title))
        Spacer(Modifier.height(17.dp))
        Description(description = stringResource(id = R.string.login_description))
        Spacer(Modifier.height(22.dp))
        PrimaryOutlinedTextField(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            value = state.email,
            onValueChange = { onEvent(LoginEvent.OnEmailChange(it)) },
            isError = false,
            singleLine = true,
            label = { PrimaryTextFieldPlaceholder(R.string.email, isRequired = true) },
        )
        Spacer(Modifier.height(15.dp))
        PrimaryOutlinedPasswordTextField(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            value = state.password,
            onValueChange = { onEvent(LoginEvent.OnPasswordChange(it)) },
            label = { PrimaryTextFieldPlaceholder(R.string.password, isRequired = true) },
        )
        Spacer(Modifier.weight(1f))
        ForgotButtonText(
            textField = stringResource(id = R.string.login_forgot_password)
        ) { onEvent(LoginEvent.OnForgotPasswordBtnClicked) }
        Spacer(Modifier.height(33.dp))
        PrimaryButton(modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(), text = stringResource(id = R.string.login_btn_text)) { onEvent(LoginEvent.OnLoginBtnClicked) }
        Spacer(Modifier.height(21.dp))
        TextLineHorizontalDivider(text = stringResource(id = R.string.or), modifier = Modifier.padding(horizontal = 20.dp))
        Spacer(Modifier.height(21.dp))
        PrimaryButton(modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(), text = stringResource(id = R.string.login_register_btn_text)) { onEvent(LoginEvent.OnRegistrationBtnClicked) }
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

@Composable
private fun ForgotButtonText(textField: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = textField,
            color = MDRTheme.colors.linkText,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            fontFamily = interFontFamily,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .noRippleClickable { onClick() }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginContentPreview() {
    MDRTheme {
        LoginContent(LoginState()) { }
    }
}