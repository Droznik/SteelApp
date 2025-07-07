package com.mdrapp.de.ui.home.purchase.variant

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mdrapp.de.R
import com.mdrapp.de.navigation.HomeNavHost
import com.mdrapp.de.navigation.PurchaseGraph
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.views.PrimaryButton
import com.mdrapp.de.ui.views.TextLineHorizontalDivider
import com.mdrapp.de.ui.views.TitleTopBar


@Composable
fun PurchaseVariantFragment(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TitleTopBar(stringResource(R.string.purchase_top_title))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp)
                .padding(top = 25.dp, bottom = 13.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 4.dp)
                    .padding(bottom = 16.dp)
            ) {
                Title()
//                Description(
//                    stringResource(R.string.purchase_variant_offer_desc1),
//                    stringResource(R.string.purchase_variant_offer_desc2)
//                )
//                Spacer(modifier = Modifier.height(14.dp))
//                Description(
//                    stringResource(R.string.purchase_variant_new_desc1),
//                    stringResource(R.string.purchase_variant_new_desc2)
//                )
            }
            PrimaryButton(stringResource(R.string.purchase_variant_offer_btn)) {
                navController.navigate(HomeNavHost.MyOffers.route) {
                    popUpTo(HomeNavHost.Home.route) { inclusive = false }
                    launchSingleTop = true
                }
            }
            TextLineHorizontalDivider(
                text = stringResource(R.string.or),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp, vertical = 22.dp)
            )
            PrimaryButton(stringResource(R.string.purchase_variant_new_btn)) {
                navController.navigate(PurchaseGraph.Order.route) {
                    launchSingleTop = true
                }
            }
        }
    }
}

@Composable
private fun Title() {
    Text(
        text = stringResource(R.string.purchase_variant_title),
        style = MDRTheme.typography.title,
        color = MDRTheme.colors.titleText,
        modifier = Modifier.padding(bottom = 12.dp)
    )
}

@Composable
private fun Description(boldText: String, normalText: String) {
    Text(
        text = buildAnnotatedString {
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append(boldText) }
            append(" ")
            withStyle(SpanStyle(fontWeight = FontWeight.Normal)) { append(normalText) }
        },
        style = MDRTheme.typography.description,
        color = MDRTheme.colors.primaryText,
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
fun PurchaseVariantPreview() {
    MDRTheme {
        PurchaseVariantFragment(navController = rememberNavController())
    }
}