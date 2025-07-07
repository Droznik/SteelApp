package com.mdrapp.de.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mdrapp.de.R
import com.mdrapp.de.ext.noRippleClickable
import com.mdrapp.de.navigation.HomeNavHost
import com.mdrapp.de.ui.theme.MDRTheme


@Composable
fun BottomMenu(navController: NavController, openMenu: () -> Unit) {
    val navigateToHome = remember {
        {
            navController.navigate(HomeNavHost.Home.route) {
                popUpTo(HomeNavHost.Home.route) { inclusive = true }
            }
        }
    }
    val navigateToBikePass = remember {
        {
            navController.navigate(HomeNavHost.BikePass.route) {
                popUpTo(HomeNavHost.Home.route) { inclusive = false }
            }
        }
    }

    Box(
        modifier = Modifier
            .height(72.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 65.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_menu_qr),
                contentDescription = null,
                tint = MDRTheme.colors.primaryText,
                modifier = Modifier
                    .size(38.dp)
                    .noRippleClickable { navigateToBikePass() }
                    .padding(horizontal = 7.dp)
            )
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_menu_home),
                contentDescription = null,
                tint = MDRTheme.colors.primaryText,
                modifier = Modifier
                    .height(38.dp)
                    .noRippleClickable { navigateToHome() }
            )
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_menu),
                contentDescription = null,
                tint = MDRTheme.colors.primaryText,
                modifier = Modifier
                    .size(38.dp)
                    .noRippleClickable { openMenu() }
                    .padding(horizontal = 7.dp)
            )
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = MDRTheme.colors.secondaryText,
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun BottomMenuPreview() {
    MDRTheme {
        BottomMenu(navController = rememberNavController()) {}
    }
}