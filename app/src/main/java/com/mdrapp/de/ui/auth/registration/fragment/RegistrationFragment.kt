package com.mdrapp.de.ui.auth.registration.fragment

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mdrapp.de.R
import com.mdrapp.de.common.fragment.BaseFragment
import com.mdrapp.de.ui.auth.registration.RegistrationEvent
import com.mdrapp.de.ui.auth.registration.RegistrationState
import com.mdrapp.de.ui.auth.registration.RegistrationViewModel
import com.mdrapp.de.ui.auth.registration.model.LocationVM
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.util.rememberSaveableImmutableListOf
import com.mdrapp.de.ui.views.CheckBoxRow
import com.mdrapp.de.ui.views.LogoTopBar
import com.mdrapp.de.ui.views.PrimaryButton
import com.mdrapp.de.ui.views.PrimaryCheckboxText
import com.mdrapp.de.ui.views.PrimaryOutlinedPasswordTextField
import com.mdrapp.de.ui.views.PrimaryOutlinedTextField
import com.mdrapp.de.ui.views.TextSelector
import kotlinx.collections.immutable.toImmutableList

@Composable
fun RegistrationFragment(navController: NavController, vm: RegistrationViewModel) {
    BaseFragment(vm, navController) {
        val onEvent = remember { vm::onEvent }
        val state by vm.state.collectAsState()

        RegistrationContent(state, onEvent)
    }
}

@Composable
private fun RegistrationContent(state: RegistrationState, onEvent: (RegistrationEvent) -> Unit) {
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
            LogoTopBar(4.dp)
            Spacer(modifier = Modifier.height(28.dp))
            RegistrationTitle()
            RegistrationDescription()
            Spacer(modifier = Modifier.height(24.dp))
            RegistrationLocationSelector(
                title = stringResource(R.string.company),
                items = state.locationData?.locations ?: emptyList(),
                value = state.locationTitle,
                isError = state.locationError.isNotEmpty(),
                errorMessage = state.locationError,
                onEvent = onEvent
            )
            RegistrationSpacer()
            RegistrationTextField(
                label = stringResource(R.string.e_mail_address),
                value = state.email,
                isError = state.emailError.isNotEmpty(),
                errorMessage = state.emailError,
                onValueChange = { onEvent(RegistrationEvent.OnEmailChange(it)) })
            RegistrationSpacer()
            RegistrationTextField(
                label = stringResource(R.string.username),
                value = state.username,
                isError = state.usernameError.isNotEmpty(),
                errorMessage = state.usernameError,
                onValueChange = { onEvent(RegistrationEvent.OnUsernameChange(it)) })
            Spacer(modifier = Modifier.height(28.dp))
            RegistrationGenderSelector(
                title = stringResource(R.string.gender),
                value = state.gender,
                isError = state.genderError.isNotEmpty(),
                errorMessage = state.genderError,
                onEvent = onEvent
            )
            RegistrationSpacer()
            RegistrationTextField(
                label = stringResource(R.string.first_name),
                value = state.firstName,
                isError = state.firstNameError.isNotEmpty(),
                errorMessage = state.firstNameError,
                onValueChange = { onEvent(RegistrationEvent.OnFirstNameChange(it)) })
            RegistrationSpacer()
            RegistrationTextField(
                label = stringResource(R.string.last_name),
                value = state.lastName,
                isError = state.lastNameError.isNotEmpty(),
                errorMessage = state.lastNameError,
                onValueChange = { onEvent(RegistrationEvent.OnLastNameChange(it)) })
            RegistrationSpacer()
            RegistrationPasswordTextField(
                label = stringResource(id = R.string.password),
                value = state.password,
                isError = state.passwordError.isNotEmpty(),
                errorMessage = state.passwordError,
                onValueChange = { onEvent(RegistrationEvent.OnPasswordChange(it)) })
            RegistrationSpacer()
            RegistrationPasswordTextField(
                label = stringResource(R.string.passwort_confirm),
                value = state.passwordConfirmation,
                isError = state.passwordConfirmationError.isNotEmpty(),
                errorMessage = state.passwordConfirmationError,
                onValueChange = { onEvent(RegistrationEvent.OnPasswordConfirmationChange(it)) })
            Spacer(modifier = Modifier.height(28.dp))
            RegistrationCheckbox(state.terms, onEvent = onEvent)
            Spacer(modifier = Modifier.height(33.dp))
            RegistrationRow(onToLogin = { onEvent(RegistrationEvent.OnToLogin) })


        }
        RegisterButton(onClick = { onEvent(RegistrationEvent.OnSubmitRegisterForm) })
    }
}

@Composable
private fun RegistrationTitle() {
    Text(
        text = stringResource(R.string.registration_for_company_bike_users),
        style = MDRTheme.typography.title,
    )
}

@Composable
private fun RegistrationDescription() {
    Text(
        text = stringResource(R.string.register_decription),
        style = MDRTheme.typography.description,
        fontSize = 16.sp
    )
}

@Composable
private fun RegisterButton(onClick: () -> Unit) {
    PrimaryButton(
        text = stringResource(id = R.string.next), onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 42.dp, top = 16.dp)
    )

}

@Composable
private fun RegistrationTextField(
    label: String,
    value: String,
    isError: Boolean,
    errorMessage: String,
    onValueChange: (String) -> Unit
) {
    PrimaryOutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        singleLine = true,
        isError = isError,
        label = { Text(text = label) },
        value = value,
        onValueChange = onValueChange
    )
    if (isError && errorMessage.isNotBlank()) {
        ErrorText(
            errorMessage = errorMessage
        )
    }
}

@Composable
private fun RegistrationLocationSelector(
    title: String,
    items: List<LocationVM?>,
    isError: Boolean,
    errorMessage: String,
    value: String,
    onEvent: (RegistrationEvent) -> Unit
) {
    val locationMap = items.associateBy { it?.title }
    val locationList = rememberSaveableImmutableListOf(
        items.map { it?.title ?: "" }.toImmutableList()
    )
    TextSelector(
        modifier = Modifier
            .fillMaxWidth(),
        title = title,
        value = value,
        items = locationList,
        onValueSelect = { location ->
            locationMap[location]?.let { onEvent(RegistrationEvent.OnLocationChange(it)) }
        })
    if (isError && errorMessage.isNotBlank()) {
        ErrorText(
            errorMessage = errorMessage,
        )
    }
}

@Composable
private fun RegistrationGenderSelector(
    title: String,
    value: String,
    isError: Boolean,
    errorMessage: String,
    onEvent: (RegistrationEvent) -> Unit
) {
    val genderList =
        rememberSaveableImmutableListOf(stringArrayResource(R.array.gender_array).asList())
    val genderMapping = remember {
        mapOf(
            "male" to genderList[0],
            "female" to genderList[1],
            "all" to genderList[2]
        )
    }
    TextSelector(
        modifier = Modifier
            .fillMaxWidth(),
        title = title,
        onValueSelect = { selectedValue ->
            val genderKey = genderMapping.entries.find { it.value == selectedValue }?.key
            genderKey?.let { onEvent(RegistrationEvent.OnGenderChange(it)) }

        },
        value = genderMapping[value] ?: "",
        items = genderList
    )
    if (isError && errorMessage.isNotBlank()) {
        ErrorText(
            errorMessage = errorMessage,
        )
    }
}

@Composable
private fun RegistrationPasswordTextField(
    label: String,
    isError: Boolean,
    errorMessage: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    PrimaryOutlinedPasswordTextField(
        modifier = Modifier
            .fillMaxWidth(),
        label = { Text(text = label) },
        value = value,
        onValueChange = onValueChange
    )
    if (isError && errorMessage.isNotBlank()) {
        ErrorText(
            errorMessage = errorMessage
        )
    }
}

@Composable
private fun RegistrationCheckbox(isChecked: Boolean, onEvent: (RegistrationEvent) -> Unit) {
    CheckBoxRow(
        modifier = Modifier
            .fillMaxWidth(),
        checked = isChecked,
        onChecked = {
            onEvent(RegistrationEvent.OnTermsChange(it))
        }
    ) {
        PrimaryCheckboxText(text = stringResource(R.string.register_checkbox_text))
    }
}

@Composable
private fun RegistrationRow(
    onToLogin: (() -> Unit)
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = 16.dp
            ),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.have_account),
            style = MDRTheme.typography.bold,
            color = MDRTheme.colors.primaryText,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.width(6.dp))
        LinkText(stringResource(R.string.sign_in)) { onToLogin() }
    }
}

@Composable
private fun LinkText(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Text(
        text = text,
        style = MDRTheme.typography.bold,
        color = MDRTheme.colors.linkText,
        fontSize = 14.sp,
        modifier = modifier.clickable { onClick() },
    )
}

@Composable
private fun ErrorText(
    errorMessage: String,
) {
    Text(
        text = errorMessage,
        style = MDRTheme.typography.description,
        color = MDRTheme.colors.error
    )
}

@Composable
private fun RegistrationSpacer() {
    Spacer(modifier = Modifier.height(38.dp))
}

@Composable
@Preview(showBackground = true)
fun RegistrationFragmentPreview() {
    MDRTheme {
        RegistrationContent(RegistrationState(), onEvent = {})
    }
}