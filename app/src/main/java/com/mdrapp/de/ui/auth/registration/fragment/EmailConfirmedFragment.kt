package com.mdrapp.de.ui.auth.registration.fragment

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
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

@Composable
fun EmailConfirmedFragment(navController: NavController, vm: RegistrationViewModel) {
    BaseFragment(vm, navController) {
        val onEvent = remember { vm::onEvent }
        val state by vm.state.collectAsState()

        EmailConfirmedContent(state, onEvent)
    }
}

@Composable
private fun EmailConfirmedContent(state: RegistrationState, onEvent: (RegistrationEvent) -> Unit) {
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
            EmailConfirmedTitle()
            Spacer(modifier = Modifier.height(24.dp))
            ImageOverlayForeground(
                backgroundPainter = painterResource(id = R.drawable.bg_email_confirmed),
                foregroundPainter = painterResource(id = R.drawable.img_email_confirmed_1)
            )

        }
        Spacer(modifier = Modifier.height(22.dp))
        EmailConfirmedButton(
            stringResource(R.string.next),
            onClick = { onEvent(RegistrationEvent.OnNextToFinalStep) })
        Spacer(modifier = Modifier.height(42.dp))
    }
}

@Composable
private fun EmailConfirmedTitle() {
    Text(
        modifier = Modifier.padding(horizontal = 16.dp),
        text = stringResource(R.string.email_confirmed),
        style = MDRTheme.typography.semiBold,
        fontSize = 34.sp
    )
}

@Composable
private fun ColumnScope.ImageOverlayForeground(
    backgroundPainter: Painter,
    foregroundPainter: Painter
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
    ) {
        Image(
            painter = backgroundPainter,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Image(
            painter = foregroundPainter,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillHeight
        )
    }
}

@Composable
private fun EmailConfirmedButton(text: String, onClick: () -> Unit) {
    PrimaryButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        text = text,
        onClick = onClick
    )
}

@Composable
@Preview(showBackground = true)
fun EmailConfirmedPreview() {
    MDRTheme {
        EmailConfirmedContent(RegistrationState()) {}
    }
}