package com.mdrapp.de.ui.home.support

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mdrapp.de.R
import com.mdrapp.de.ext.sendEmail
import com.mdrapp.de.navigation.HomeNavHost
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.theme.interFontFamily
import com.mdrapp.de.ui.views.PrimaryButton
import com.mdrapp.de.ui.views.TitleTopBar

@Composable
fun SupportFragment(
    navController: NavController
) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {
        TitleTopBar(title = stringResource(id = R.string.support_toolbar_title))
        Spacer(Modifier.height(25.dp))
        Row(modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()) {
            Title(stringResource(id = R.string.support_title))
            Spacer(Modifier.weight(1f))
            Image(painter = painterResource(id = R.drawable.img_heart), contentDescription = null)
        }
        Spacer(Modifier.height(25.dp))
        Column(
            modifier = Modifier
                .padding(horizontal = 28.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ContactText()
            Spacer(Modifier.height(14.dp))
            EmailText(context)
        }
        Spacer(Modifier.weight(1f))
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            SupportPrimaryButton(stringResource(id = R.string.support_bike_advice)) {  }
            SupportPrimaryButton(stringResource(id = R.string.support_repair)) { context.sendEmail("customercare@baronmobil.com") }
            SupportPrimaryButton(stringResource(id = R.string.support_report_theft)) { context.sendEmail("customercare@baronmobil.com") }
            SupportPrimaryButton(stringResource(id = R.string.support_faq)) { navController.navigate(HomeNavHost.Faq.route) { launchSingleTop = true } }
        }
        Spacer(Modifier.height(3.dp))
    }
}

@Composable
private fun Title(title: String) {
    Text(
        text = title,
        style = MDRTheme.typography.title,
        color = MDRTheme.colors.titleText
    )
}

@Composable
private fun ContactText() {
    val supportContact = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontFamily = interFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = MDRTheme.colors.secondaryText
            )
        ) {
            append(stringResource(id = R.string.support_contact_we_are_available))
        }
        withStyle(
            style = SpanStyle(
                fontFamily = interFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = MDRTheme.colors.secondaryText
            )
        ) {
            append(stringResource(id = R.string.support_contact_time))
        }
        withStyle(
            style = SpanStyle(
                fontFamily = interFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = MDRTheme.colors.secondaryText
            )
        ) {
            append(stringResource(id = R.string.support_contact_under) + " ")
        }
        withStyle(
            style = SpanStyle(
                fontFamily = interFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = MDRTheme.colors.secondaryText
            )
        ) {
            append(stringResource(id = R.string.support_contact_number))
        }
    }

    Text(
        textAlign = TextAlign.Center,
        lineHeight = 17.sp,
        text = supportContact
    )
}

@Composable
private fun EmailText(context: Context) {
    val supportContact = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontFamily = interFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = MDRTheme.colors.secondaryText
            )
        ) {
            append(stringResource(id = R.string.support_contact_by_email))
        }
        withStyle(
            style = SpanStyle(
                fontFamily = interFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = MDRTheme.colors.linkText
            )
        ) {
            append(stringResource(id = R.string.support_email))
        }
    }

    ClickableText(
        style = TextStyle(
            textAlign = TextAlign.Center
        ),
        text = supportContact,
        onClick = {
            context.sendEmail("customercare@baronmobil.com")
        }
    )
}

@Composable
private fun SupportPrimaryButton(buttonText: String, onClick: () -> Unit) {
    PrimaryButton(
        text = buttonText, onClick = { onClick() },
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
private fun SupportPreview() {
    MDRTheme {
        SupportFragment(navController = rememberNavController())
    }
}