package com.mdrapp.de.ui.auth.login.successResetPassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mdrapp.de.R
import com.mdrapp.de.navigation.AuthGraph
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.views.LogoTopBar
import com.mdrapp.de.ui.views.PrimaryButton

@Composable
fun SuccessResetPasswordFragment(
    navController: NavController
) {
    Column {
        LogoTopBar()
        Spacer(Modifier.height(27.dp))
        Title(title = stringResource(id = R.string.successful_password_reset_title))
        Spacer(Modifier.height(24.dp))
        Title(title = stringResource(id = R.string.successful_password_reset_description))
        Spacer(Modifier.height(22.dp))
        ImageOverlayForeground(
            backgroundPainter = painterResource(id = R.drawable.img_background_gradient),
            foregroundPainter = painterResource(id = R.drawable.img_cyclist_sideways)
        )
        PrimaryButton(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            text = stringResource(id = R.string.successful_password_reset_to_the_login)
        ) { navController.navigate(AuthGraph.Login.route) { launchSingleTop = true } }
        Spacer(Modifier.height(42.dp))
    }
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
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
        ) {
            Image(
                painter = foregroundPainter,
                contentDescription = null,
                modifier = Modifier.size(255.dp, 305.dp),
                contentScale = ContentScale.FillHeight
            )
        }
    }
    Image(
        painter = painterResource(id = R.drawable.img_cyclist_sideways_mirror),
        contentDescription = null,
        modifier = Modifier
            .size(255.dp, 45.dp)
            .align(Alignment.End), // Adjust size as needed
        contentScale = ContentScale.FillHeight
    )
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

@Preview(showBackground = true)
@Composable
private fun SuccessResetPasswordPreview() {
    MDRTheme {
        SuccessResetPasswordFragment(navController = rememberNavController())
    }
}