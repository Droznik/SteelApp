package com.mdrapp.de.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mdrapp.de.R
import com.mdrapp.de.ext.noRippleClickable
import com.mdrapp.de.ui.theme.MDRTheme


@Composable
fun LogoTopBar(startPadding: Dp = 20.dp) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = startPadding, top = 23.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.img_mdr_logo),
            contentDescription = "Logo",
            modifier = Modifier.height(25.dp)
        )
    }
}

@Composable
fun ProfileTopBar(initials: String? = null, onClick: () -> Unit) {
    Box(Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.top_bar_height))
                .noRippleClickable { onClick() }
                .padding(top = 5.dp)
                .padding(horizontal = 20.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(28.dp)
                    .background(color = MDRTheme.colors.appGreen, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                initials?.let {
                    Text(
                        text = it,
                        style = MDRTheme.typography.medium,
                        fontSize = 11.sp,
                        color = MDRTheme.colors.lightText,
                    )
                }
            }
            Text(
                text = stringResource(R.string.my_profile),
                style = MDRTheme.typography.semiBold,
                fontSize = 14.sp,
                color = MDRTheme.colors.primaryText,
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 5.dp)
            )
        }
        TopBarHorizontalDivider()
    }

}

@Composable
fun TitleTopBar(
    title: String,
    count: Int? = null,
    actionText: String? = null,
    onActionClick: (() -> Unit)? = null
) {
    val resultTitle = remember(count) {
        when(count) {
            null -> title
            else -> "$title [ $count ]"
        }
    }
    val textAlign = when(actionText) {
        null -> TextAlign.Center
        else -> TextAlign.Start
    }

    Box(Modifier.fillMaxWidth()){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.top_bar_height))
                .padding(top = 10.dp)
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = resultTitle,
                style = MDRTheme.typography.semiBold,
                fontSize = 14.sp,
                color = MDRTheme.colors.primaryText,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = textAlign,
                modifier = Modifier.weight(1f)
            )
            actionText?.let {
                Spacer(modifier = Modifier.width(20.dp))
                ToolBarAction(it) { onActionClick?.invoke() }
            }
        }
        TopBarHorizontalDivider()
    }
}

@Composable
fun BackTopBar(
    title: String,
    subTitle: String? = null,
    actionText: String? = null,
    onActionClick: (() -> Unit)? = null,
    onBack: () -> Unit
) {
    val resultTitle = remember(subTitle) {
        when {
            subTitle.isNullOrBlank() -> title
            else -> "$title • $subTitle"
        }
    }

    Box(Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.top_bar_height))
                .padding(top = 10.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .noRippleClickable { onBack() }
                    .padding(start = 20.dp, end = 10.dp)
            ){
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back),
                    contentDescription = "Back",
                )
            }
            Text(
                text = resultTitle,
                style = MDRTheme.typography.semiBold,
                fontSize = 14.sp,
                color = MDRTheme.colors.primaryText,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(end = 20.dp)
                    .weight(1f)
            )
            actionText?.let {
                ToolBarAction(it) { onActionClick?.invoke() }
                Spacer(modifier = Modifier.width(20.dp))
            }
        }
        TopBarHorizontalDivider()
    }
}

@Composable
fun ToolBarAction(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        style = MDRTheme.typography.regular,
        fontSize = 14.sp,
        color = MDRTheme.colors.appBlue,
        modifier = Modifier.noRippleClickable { onClick() }
    )
}

@Composable
private fun BoxScope.TopBarHorizontalDivider() {
    HorizontalDivider(
        thickness = 1.dp,
        color = MDRTheme.colors.secondaryText,
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomStart)
    )
}


@Preview
@Composable
private fun LogoTopBarPreview() {
    MDRTheme {
        LogoTopBar()
    }
}

@Preview
@Composable
private fun ProfileTopBarPreview() {
    MDRTheme {
        ProfileTopBar("MP") {}
    }
}

@Preview
@Composable
private fun TitleTopBarPreview() {
    MDRTheme {
        TitleTopBar("mein-dienstrad.pass")
    }
}

@Preview
@Composable
private fun TitleTopBarWithActionPreview() {
    MDRTheme {
        TitleTopBar("mein-dienstrad.pass", 3, "Alle löschen")
    }
}

@Preview
@Composable
private fun BackTopBarPreview() {
    MDRTheme {
        BackTopBar("Meine Diensträder") {}
    }
}

@Preview
@Composable
private fun BackTopBarWithSubtitlePreview() {
    MDRTheme {
        BackTopBar(
            title = "Meine Diensträder",
            subTitle = "MDR2249427"
        ) {}
    }
}

@Preview
@Composable
private fun BackTopBarWithActionPreview() {
    MDRTheme {
        BackTopBar(
            title = "Meine Diensträder",
            subTitle = "MDR2249427",
            actionText = "Bearbeiten"
        ) {}
    }
}