package com.mdrapp.de.ui.home.purchase.result

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mdrapp.de.R
import com.mdrapp.de.navigation.HomeNavHost
import com.mdrapp.de.ui.home.myBikes.MyMdrBikesType
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.util.ClickHelper
import com.mdrapp.de.ui.views.PrimaryButtonTopLine
import com.mdrapp.de.ui.views.TitleTopBar


@Composable
fun PurchaseResultFragment(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TitleTopBar(stringResource(R.string.purchase_top_title))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(top = 25.dp, bottom = 13.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 4.dp)
                    .padding(bottom = 16.dp)
            ) {
                Title()
                Description()
                Spacer(modifier = Modifier.height(38.dp))
                LinkText {
                    navController.navigate(
                        HomeNavHost.MyMdrBikes.createRoute(MyMdrBikesType.ORDERS)
                    ) {
                        popUpTo(HomeNavHost.Home.route) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            }
            PrimaryButtonTopLine(stringResource(R.string.understood)) {
                ClickHelper.clickOnce { navController.navigateUp() }
            }
        }
    }
}

@Composable
private fun Title() {
    Text(
        text = stringResource(id = R.string.purchase_result_title),
        style = MDRTheme.typography.title,
        color = MDRTheme.colors.titleText,
        modifier = Modifier.padding(bottom = 12.dp)
    )
}

@Composable
private fun Description() {
    Text(
        text = buildAnnotatedString {
            append(stringResource(R.string.purchase_result_description_part1))
            append(" ")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append(stringResource(R.string.purchase_result_description_part2))
            }
            append(" ")
            append(stringResource(R.string.purchase_result_description_part3))
        },
        style = MDRTheme.typography.description,
        color = MDRTheme.colors.primaryText,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun LinkText(onClick: () -> Unit) {
    Text(
        text = stringResource(id = R.string.purchase_result_my_orders_link),
        style = MDRTheme.typography.semiBold,
        fontSize = 14.sp,
        color = MDRTheme.colors.linkText,
        modifier = Modifier.clickable { onClick() }
    )
}

@Preview
@Composable
private fun PurchaseResultPreview() {
    MDRTheme {
        PurchaseResultFragment(navController = rememberNavController())
    }
}