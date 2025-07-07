package com.mdrapp.de.ui.views

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mdrapp.de.R
import com.mdrapp.de.ui.theme.MDRTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimaryOutlinedTextField(
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier.fillMaxWidth(),
    value: String,
    onValueChange: (String) -> Unit,
    height: Dp = 46.dp,
    containerColor: Color? = MDRTheme.colors.primaryBackground,
    keyboardActions: KeyboardActions? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    placeholder: @Composable (() -> Unit)? = null,
    label: @Composable (() -> Unit)? = null,
    singleLine: Boolean = false,
    shape: Shape = CircleShape,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
    textStyle: TextStyle = MDRTheme.typography.primaryTextField.copy(color = MDRTheme.colors.secondaryText),
    suffix: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
    isError: Boolean = false,
    readOnly: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(horizontal = 26.dp, vertical = 11.dp)
) {
    val interactionSource = remember { MutableInteractionSource() }
    val colors = primaryOutlinedTextFieldColors(containerColor = containerColor ?: Color.Transparent)
    val focusManager = LocalFocusManager.current

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.height(height),
        interactionSource = interactionSource,
        enabled = enabled,
        singleLine = singleLine,
        keyboardActions = keyboardActions ?: KeyboardActions(onDone = { focusManager.clearFocus() }),
        keyboardOptions = keyboardOptions,
        textStyle = textStyle.copy(color = if (isError) MDRTheme.colors.error else textStyle.color),
        visualTransformation = visualTransformation,
        readOnly = readOnly
    ) {
        OutlinedTextFieldDefaults.DecorationBox(
            value = value,
            innerTextField = it,
            enabled = enabled,
            singleLine = singleLine,
            visualTransformation = visualTransformation,
            interactionSource = interactionSource,
            colors = colors,
            placeholder = placeholder,
            label = label,
            trailingIcon = trailingIcon,
            suffix = suffix,
            isError = isError,
            contentPadding = contentPadding
        ) {
            OutlinedTextFieldDefaults.ContainerBox(
                enabled = enabled,
                isError = isError,
                interactionSource = interactionSource,
                colors = colors,
                shape = shape,
            )
        }
    }
}

@Composable
fun PrimaryOutlinedPasswordTextField(
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier.fillMaxWidth(),
    value: String,
    onValueChange: (String) -> Unit,
    height: Dp = 46.dp,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    placeholder: @Composable (() -> Unit)? = null,
    label: @Composable (() -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(horizontal = 26.dp, vertical = 11.dp)
) {
    val passwordVisibility = remember { mutableStateOf(false) }

    PrimaryOutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        height = height,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        placeholder = placeholder,
        label = label,
        singleLine = true,
        visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value }) {
                Icon(
                    imageVector = if (passwordVisibility.value) {
                        ImageVector.vectorResource(R.drawable.ic_visibility)
                    } else {
                        ImageVector.vectorResource(R.drawable.ic_visibility_off)
                    },
                    contentDescription = null,
                    tint = MDRTheme.colors.secondaryText
                )
            }
        },
        contentPadding = contentPadding
    )
}

@Composable
fun PrimaryTextFieldPlaceholder(
    @StringRes stringId: Int,
    modifier: Modifier = Modifier,
    isRequired: Boolean = false
) {
    Text(
        text = if (isRequired ) stringResource(stringId) + "*" else stringResource(stringId),
        modifier = modifier,
        style = MDRTheme.typography.regular,
        fontSize = 14.sp,
        textAlign = TextAlign.Center
    )
}

@Composable
fun primaryOutlinedTextFieldColors(
    containerColor: Color = MDRTheme.colors.primaryBackground,
    focusedTextColor: Color = MDRTheme.colors.secondaryText,
    unfocusedTextColor: Color = MDRTheme.colors.secondaryText,
    errorTextColor: Color = MDRTheme.colors.error,
    focusedBorderColor: Color = MDRTheme.colors.secondaryText,
    unfocusedBorderColor: Color = MDRTheme.colors.secondaryText,
    errorBorderColor: Color = MDRTheme.colors.error,
    cursorColor: Color = MDRTheme.colors.secondaryText,
    focusedPlaceholderColor: Color = MDRTheme.colors.secondaryText,
    unfocusedPlaceholderColor: Color = MDRTheme.colors.secondaryText,
    focusedLabelColor: Color = MDRTheme.colors.secondaryText,
    unfocusedLabelColor: Color = MDRTheme.colors.secondaryText,
    selectionColors: TextSelectionColors = TextSelectionColors(
        handleColor = MDRTheme.colors.secondaryText,
        backgroundColor = MDRTheme.colors.secondaryText.copy(alpha = 0.2f)
    )
) = OutlinedTextFieldDefaults.colors(
    focusedTextColor = focusedTextColor,
    unfocusedTextColor = unfocusedTextColor,
    errorTextColor = errorTextColor,
    focusedContainerColor = containerColor,
    unfocusedContainerColor = containerColor,
    errorContainerColor = containerColor,
    focusedBorderColor = focusedBorderColor,
    unfocusedBorderColor = unfocusedBorderColor,
    errorBorderColor = errorBorderColor,
    cursorColor = cursorColor,
    focusedPlaceholderColor = focusedPlaceholderColor,
    unfocusedPlaceholderColor = unfocusedPlaceholderColor,
    focusedLabelColor = focusedLabelColor,
    unfocusedLabelColor = unfocusedLabelColor,
    selectionColors = selectionColors,
    errorLabelColor = errorTextColor,
    errorPlaceholderColor = errorTextColor,
    errorSupportingTextColor = errorTextColor
)

@Preview
@Composable
private fun TextFieldsPreview() {
    MDRTheme {
        Column(Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.height(36.dp))

            PrimaryOutlinedTextField(
                value = "mail@mail.com",
                onValueChange = {},
                isError = true,
                label = { PrimaryTextFieldPlaceholder(R.string.email, isRequired = true) },
            )

            Spacer(modifier = Modifier.height(36.dp))

            PrimaryOutlinedPasswordTextField(
                value = "password",
                onValueChange = {},
                label = { PrimaryTextFieldPlaceholder(R.string.password, isRequired = true) },
            )

            Spacer(modifier = Modifier.height(36.dp))

            PrimaryOutlinedTextField(
                value = "My note",
                height = 200.dp,
                onValueChange = {},
                shape = RoundedCornerShape(10.dp),
                label = { PrimaryTextFieldPlaceholder(R.string.please_choose) },
            )
        }
    }
}