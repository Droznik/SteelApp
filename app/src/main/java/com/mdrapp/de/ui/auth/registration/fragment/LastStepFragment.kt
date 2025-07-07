package com.mdrapp.de.ui.auth.registration.fragment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mdrapp.de.R
import com.mdrapp.de.common.fragment.BaseFragment
import com.mdrapp.de.ui.auth.registration.RegistrationSecondStepEvent
import com.mdrapp.de.ui.auth.registration.RegistrationSecondStepState
import com.mdrapp.de.ui.auth.registration.RegistrationViewModel
import com.mdrapp.de.ui.splash.HideSystemsBars
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.views.HorizontalTextBlock
import com.mdrapp.de.ui.views.LogoTopBar
import com.mdrapp.de.ui.views.PrimaryButton
import com.mdrapp.de.ui.views.PrimaryOutlinedTextField
import com.mdrapp.de.ui.views.TextBlockDivider

@Composable
fun LastStepFragment(navController: NavController, vm: RegistrationViewModel) {
    BaseFragment(vm, navController) {
        val onEvent = remember { vm::onSecondStepEvent }
        val state by vm.secondStepState.collectAsState()
        LaunchedEffect(Unit) {
            vm.getFinalStep()
        }
        LastStepContent(state = state, onEvent = onEvent)
    }
}

@Composable
private fun LastStepContent(
    state: RegistrationSecondStepState,
    onEvent: (RegistrationSecondStepEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        ) {
            LogoTopBar(0.dp)
            Spacer(modifier = Modifier.height(28.dp))
            LastStepTitle()
            LastStepSpacer()
            LastStepDescription()
            LastStepSpacer()
            HorizontalTextBlock(
                title = stringResource(R.string.final_step_company),
                value = state.company?.title ?: ""
            )
            TextBlockDivider()
            HorizontalTextBlock(
                stringResource(R.string.firm_address),
                value = state.company?.address ?: ""
            )
            Spacer(modifier = Modifier.height(26.dp))
            Text(
                text = stringResource(R.string.final_step_my_info),
                fontSize = 19.sp,
                style = MDRTheme.typography.bold
            )
            Spacer(modifier = Modifier.height(26.dp))
            LastStepTextField(
                stringResource(R.string.address),
                state.address,
                isError = state.addressError.isNotEmpty(),
                errorMessage = state.addressError,
                onValueChange = { onEvent(RegistrationSecondStepEvent.OnAddressChange(it)) })
            LastStepTextField(
                stringResource(R.string.postcode),
                state.postCode,
                isError = state.postCodeError.isNotEmpty(),
                errorMessage = state.postCodeError,
                keyboardType = KeyboardType.NumberPassword,
                onValueChange = { onEvent(RegistrationSecondStepEvent.OnPostCodeChange(it)) })
            LastStepTextField(
                stringResource(R.string.city),
                state.city,
                isError = state.cityError.isNotEmpty(),
                errorMessage = state.cityError,
                onValueChange = { onEvent(RegistrationSecondStepEvent.OnCityChange(it)) })
            LastStepTextField(
                stringResource(R.string.telephone),
                state.phone,
                isError = state.phoneError.isNotEmpty(),
                errorMessage = state.phoneError,
                keyboardType = KeyboardType.NumberPassword,
                onValueChange = { onEvent(RegistrationSecondStepEvent.OnPhoneChange(it)) })
            LastStepTextField(
                stringResource(R.string.personal_nr),
                state.perNo,
                isError = state.perNoError.isNotEmpty(),
                errorMessage = state.perNoError,
                onValueChange = { onEvent(RegistrationSecondStepEvent.OnPersonalNumberChange(it)) })
        }
        LastStepButton(onClick = { onEvent(RegistrationSecondStepEvent.OnConfirmFinalStep) })
    }
}

@Composable
private fun LastStepTitle() {
    Text(
        style = MDRTheme.typography.bold,
        text = stringResource(R.string.final_step_title),
        fontSize = 34.sp
    )
}

@Composable
private fun LastStepDescription() {
    Text(
        text = stringResource(R.string.final_step_description),
        fontSize = 16.sp
    )
}

@Composable
private fun LastStepSpacer() {
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun LastStepTextField(
    label: String,
    value: String,
    isError: Boolean,
    errorMessage: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    PrimaryOutlinedTextField(
        placeholder = { Text(text = label) },
        singleLine = true,
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth(),
        label = { Text(text = label) },
        value = value,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        onValueChange = onValueChange
    )

    if (isError && errorMessage.isNotBlank()) {
        Text(
            modifier = Modifier.padding(start = 4.dp, top = 4.dp),
            text = errorMessage,
            style = MDRTheme.typography.description,
            color = MDRTheme.colors.error
        )
    }
}

@Composable
private fun LastStepButton(onClick: () -> Unit = {}) {
    PrimaryButton(
        text = stringResource(R.string.next), onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 42.dp)
    )
}

@Composable
@Preview(showBackground = true)
fun LastStepFragmentPreview() {
    MDRTheme {
        LastStepContent(state = RegistrationSecondStepState(), onEvent = {})
    }
}