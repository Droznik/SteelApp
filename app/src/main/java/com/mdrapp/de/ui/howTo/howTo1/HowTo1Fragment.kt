package com.mdrapp.de.ui.howTo.howTo1

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mdrapp.de.R
import com.mdrapp.de.navigation.HowToGraph
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.views.LogoTopBar
import com.mdrapp.de.ui.views.PrimaryButton

@Composable
fun HowTo1Fragment(
    navController: NavController
) {
    Column {
        LogoTopBar()
        Spacer(Modifier.height(47.dp))
        Image(
            modifier = Modifier
                .padding(horizontal = 26.dp)
                .fillMaxWidth()
                .height(420.dp),
            painter = painterResource(id = R.drawable.img_how_to_1),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Spacer(Modifier.weight(1f))
        HowToTitle(stringResource(id = R.string.how_to_1_title))
        Spacer(Modifier.height(50.dp))
        PrimaryButton(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(), text = stringResource(id = R.string.next)
        ) { navController.navigate(HowToGraph.HowTo2.route) { launchSingleTop = true } }
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

@Preview(showBackground = true)
@Composable
private fun HowTo1Preview() {
    MDRTheme {
        HowTo1Fragment(navController = rememberNavController())
    }
}