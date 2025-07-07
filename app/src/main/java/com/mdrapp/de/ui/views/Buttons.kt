package com.mdrapp.de.ui.views

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mdrapp.de.R
import com.mdrapp.de.ui.theme.MDRTheme


@Composable
fun PrimaryButton(
    text: String,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier.fillMaxWidth(),
    height: Dp = 52.dp,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(height),
        colors = ButtonDefaults.buttonColors(containerColor = MDRTheme.colors.appGreen),
        enabled = enabled
    ) {
        Text(
            text = text,
            style = MDRTheme.typography.semiBold,
            fontSize = 17.sp,
            color = MDRTheme.colors.lightText
        )
    }
}

@Composable
fun PrimaryContentButton(
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier.fillMaxWidth(),
    height: Dp = 52.dp,
    enabled: Boolean = true,
    content: @Composable() (RowScope.() -> Unit),
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(height),
        colors = ButtonDefaults.buttonColors(containerColor = MDRTheme.colors.appGreen),
        enabled = enabled
    ) {
        content()
    }
}

@Composable
fun PrimaryButtonTopLine(
    text: String,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier.fillMaxWidth(),
    height: Dp = 52.dp,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = MDRTheme.colors.secondaryText
        )
        Spacer(modifier = Modifier.height(11.dp))
        PrimaryButton(
            text = text,
            height = height,
            enabled = enabled,
        ) { onClick() }
    }
}

@Composable
fun SecondaryButton(
    text: String,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier.fillMaxWidth(),
    height: Dp = 52.dp,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(height),
        colors = ButtonDefaults.buttonColors(containerColor = MDRTheme.colors.primaryBackground),
        border = BorderStroke(1.dp, MDRTheme.colors.titleText),
        enabled = enabled
    ) {
        Text(
            text = text,
            style = MDRTheme.typography.semiBold,
            fontSize = 17.sp,
            color = MDRTheme.colors.titleText
        )
    }
}

@Preview
@Composable
private fun ButtonsPreview() {
    MDRTheme {
        Column {
            PrimaryContentButton(
                content = {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_plus_large),
                        contentDescription = null,
                        tint = MDRTheme.colors.lightText
                    )
                }
            ) {}
            Spacer(modifier = Modifier.height(32.dp))
            PrimaryButton(text = stringResource(id = R.string.next)) {}
            Spacer(modifier = Modifier.height(32.dp))
            SecondaryButton(text = stringResource(id = R.string.next)) {}
            Spacer(modifier = Modifier.height(32.dp))
            PrimaryButtonTopLine(text = stringResource(id = R.string.next)) {}
        }
    }
}

