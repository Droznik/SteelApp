package com.mdrapp.de.ui.auth.registration.fragment

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
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
import com.mdrapp.de.ui.splash.HideSystemsBars
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.views.LogoTopBar
import com.mdrapp.de.ui.views.PrimaryButton
import com.mdrapp.de.ui.views.PrimaryOutlinedTextField

@Composable
fun PartnerCodeFragment(navController: NavController, vm: RegistrationViewModel) {
    BaseFragment(vm, navController) {
        val onEvent = remember { vm::onEvent }
        val state by vm.state.collectAsState()

        PartnerCodeContent(state = state, onEvent = onEvent)
    }
}

@Composable
private fun PartnerCodeContent(state: RegistrationState, onEvent: (RegistrationEvent) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            LogoTopBar()
            Spacer(modifier = Modifier.height(28.dp))
            PartnerCodeTitle()
            ImageComposable(imagePainter = painterResource(id = R.drawable.img_partnercode_bike))
            Spacer(modifier = Modifier.height(26.dp))


        }
        PartnerCodeTextField(
            value = state.partnerCode,
            isError = state.partnerCodeError,
            onValueChange = { onEvent(RegistrationEvent.OnPartnerCodeChange(it)) })
        Spacer(modifier = Modifier.height(14.dp))
        ShopCodeBottomRow(onToLogin = { onEvent(RegistrationEvent.OnToLogin) })
        NextButton(onClick = { onEvent(RegistrationEvent.SendShopCode) })
    }

}

@Composable
private fun PartnerCodeTitle() {
    Text(
        modifier = Modifier.padding(horizontal = 16.dp),
        text = stringResource(R.string.register_partner_code_title),
        style = MDRTheme.typography.title
    )
}

@Composable
private fun ColumnScope.ImageComposable(imagePainter: Painter) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
        contentAlignment = Alignment.BottomEnd
    ) {
        val boxScope = this
        val percent = boxScope.maxWidth / 100 * 20
        Image(
            painter = imagePainter,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = percent),

            )
    }
}

@Composable
private fun PartnerCodeTextField(value: String, isError: Boolean, onValueChange: (String) -> Unit) {
    PrimaryOutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        value = value,
        isError = isError,
        singleLine = true,
        onValueChange = onValueChange,
        label = { Text(text = stringResource(R.string.partner_code)) },
    )
}

@Composable
private fun ShopCodeBottomRow(
    onToLogin: (() -> Unit)
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.have_account),
            style = MDRTheme.typography.regular,
            color = MDRTheme.colors.secondaryText
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
        color = MDRTheme.colors.linkText,
        fontSize = 14.sp,
        modifier = modifier.clickable { onClick() },
    )
}

@Composable
private fun NextButton(onClick: () -> Unit = {}) {
    PrimaryButton(
        text = stringResource(id = R.string.next), onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 42.dp, top = 16.dp)
    )

}

@Composable
@Preview(showBackground = true)
private fun PartnerCodePreview() {
    MDRTheme {
        PartnerCodeContent(
            state = RegistrationState(),
            onEvent = {}
        )
    }
}