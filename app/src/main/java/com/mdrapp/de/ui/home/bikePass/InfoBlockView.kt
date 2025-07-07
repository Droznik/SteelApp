package com.mdrapp.de.ui.home.bikePass

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.R
import com.mdrapp.de.ext.noRippleClickable

@Composable
fun InfoBlockView(
    onCloseIconClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxHeight()) {
        Image(
            painter = painterResource(id = R.drawable.ic_close),
            contentDescription = null,
            modifier = Modifier
                .noRippleClickable { onCloseIconClick() }
                .align(Alignment.TopEnd)
                .padding(end = 20.dp, top = 35.dp)
        )
        Column(
            Modifier
                .padding(start = 40.dp, end = 40.dp, top = 65.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(10.dp))
            Title(title = stringResource(id = R.string.bike_pass_info_title))
            Spacer(Modifier.height(18.dp))
            Body(body = stringResource(id = R.string.bike_pass_info_description_1))
            Spacer(Modifier.height(5.dp))
            Body(body = stringResource(id = R.string.bike_pass_info_description_2))
            Spacer(Modifier.height(24.dp))
            Subtitle(subtitle = stringResource(id = R.string.bike_pass_info_subtitle_1))
            Spacer(Modifier.height(5.dp))
            Body(body = stringResource(id = R.string.bike_pass_info_body_1))
            Spacer(Modifier.height(24.dp))
            Subtitle(subtitle = stringResource(id = R.string.bike_pass_info_subtitle_2))
            Spacer(Modifier.height(5.dp))
            Body(body = stringResource(id = R.string.bike_pass_info_body_2))
            Spacer(Modifier.height(24.dp))
            Subtitle(subtitle = stringResource(id = R.string.bike_pass_info_subtitle_3))
            Spacer(Modifier.height(5.dp))
            Body(body = stringResource(id = R.string.bike_pass_info_body_3))
            Spacer(Modifier.height(24.dp))
            Subtitle(subtitle = stringResource(id = R.string.bike_pass_info_subtitle_4))
            Spacer(Modifier.height(5.dp))
            Body(body = stringResource(id = R.string.bike_pass_info_body_4))
            Spacer(Modifier.height(24.dp))
            Subtitle(subtitle = stringResource(id = R.string.bike_pass_info_subtitle_5))
            Spacer(Modifier.height(5.dp))
            Body(body = stringResource(id = R.string.bike_pass_info_body_5))
            Spacer(Modifier.height(5.dp))
            Body(body = stringResource(id = R.string.bike_pass_info_body_6))
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun Title(title: String) {
    Text(
        text = title,
        color = MDRTheme.colors.titleText,
        style = MDRTheme.typography.medium,
        fontSize = 20.sp,
        textAlign = TextAlign.Start
    )
}

@Composable
private fun Subtitle(subtitle: String) {
    Text(
        text = subtitle,
        color = MDRTheme.colors.titleText,
        style = MDRTheme.typography.semiBold,
        fontSize = 14.sp,
        textAlign = TextAlign.Start
    )
}

@Composable
private fun Body(body: String) {
    Text(
        text = body,
        color = MDRTheme.colors.secondaryText,
        style = MDRTheme.typography.regular,
        fontSize = 14.sp,
        textAlign = TextAlign.Start
    )
}

@Composable
@Preview(showBackground = true)
fun InfoBlockViewPreview() {
    MDRTheme {
        InfoBlockView { }
    }
}