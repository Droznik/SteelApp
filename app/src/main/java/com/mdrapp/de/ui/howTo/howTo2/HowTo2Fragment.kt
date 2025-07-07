package com.mdrapp.de.ui.howTo.howTo2

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mdrapp.de.R
import com.mdrapp.de.common.fragment.BaseFragment
import com.mdrapp.de.ui.howTo.HowToEvent
import com.mdrapp.de.ui.howTo.HowToViewModel
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.views.LogoTopBar
import com.mdrapp.de.ui.views.PrimaryButton

@Composable
fun HowTo2Fragment(
    navController: NavController,
    vm: HowToViewModel
) {
    BaseFragment(baseViewModel = vm, navController = navController) {
        val onEvent = remember { vm::onEvent }

        HowTo2Content(onEvent)
    }

}

@Composable
fun HowTo2Content(
    onEvent: (HowToEvent) -> Unit
) {
    Column {
        LogoTopBar()
        Spacer(Modifier.height(47.dp))
        Image(
            modifier = Modifier
                .padding(horizontal = 26.dp)
                .fillMaxWidth()
                .height(420.dp),
            painter = painterResource(id = R.drawable.img_how_to_2),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Spacer(Modifier.weight(1f))
        HowToTitle(stringResource(id = R.string.how_to_2_title))
        Spacer(Modifier.height(50.dp))
        PrimaryButton(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(), text = stringResource(id = R.string.for_registration)
        ) { onEvent(HowToEvent.NavigateToLogin) }
        Spacer(Modifier.height(42.dp))
    }
}

@Composable
private fun HowToTitle(title: String) {
    Text(
        modifier = Modifier.padding(horizontal = 20.dp),
        text = title,
        color = MDRTheme.colors.titleText,
        style = MDRTheme.typography.title.copy(fontSize = 24.sp),
        textAlign = TextAlign.Center
    )
}

@Composable
@Preview(showBackground = true)
private fun HowTo2Preview() {
    MDRTheme {
        HowTo2Content() { }
    }
}