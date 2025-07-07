package com.mdrapp.de.ui.home.purchase.order.view

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mdrapp.de.R
import com.mdrapp.de.ui.home.purchase.order.model.EditableAccessoryVM
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.views.PrimaryOutlinedTextField
import com.mdrapp.de.ui.views.PrimaryTextFieldPlaceholder


@Composable
fun EditableAccessoryItem(
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier.fillMaxWidth(),
    item: EditableAccessoryVM,
    onItemCalcFieldChanged: () -> Unit,
    onItemRequiredFieldChanged: () -> Unit,
    onDelete: (() -> Unit)? = null
) {
    Column(
        modifier = modifier
    ) {
        PrimaryOutlinedTextField(
            value = item.title,
            singleLine = true,
            readOnly = item.readOnly,
            label = { PrimaryTextFieldPlaceholder(R.string.purchase_item_product_name, isRequired = true) },
            onValueChange = {
                item.title = it
                onItemRequiredFieldChanged()
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            PrimaryOutlinedTextField(
                value = item.uvp,
                singleLine = true,
                readOnly = item.readOnly,
                isError = item.isUvpError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { PrimaryTextFieldPlaceholder(R.string.purchase_bike_uvp, isRequired = true) },
                onValueChange = {
                    item.onUvpChange(it)
                    onItemCalcFieldChanged()
                },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(10.dp))
            PrimaryOutlinedTextField(
                value = item.price,
                singleLine = true,
                readOnly = item.readOnly,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { PrimaryTextFieldPlaceholder(R.string.purchase_item_product_brutto, isRequired = true) },
                onValueChange = {
                    item.onPriceChange(it)
                    onItemCalcFieldChanged()
                },
                modifier = Modifier.weight(1f)
            )
        }
        if (item.isUvpError) {
            Text(
                text = stringResource(R.string.min_price, item.initialUvp ?: 0.0),
                style = MDRTheme.typography.regular,
                fontSize = 10.sp,
                color = MDRTheme.colors.error,
                modifier = Modifier.padding(start = 32.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!item.readOnly){
                QuantityButton(R.drawable.ic_plus) {
                    item.plus()
                    onItemCalcFieldChanged()
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
            Box(
                modifier = Modifier
                    .size(height = 46.dp, width = 110.dp)
                    .border(1.dp, MDRTheme.colors.secondaryText, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.quantity.toString(),
                    style = MDRTheme.typography.regular,
                    fontSize = 14.sp,
                    color = MDRTheme.colors.secondaryText
                )
            }
            if (!item.readOnly) {
                Spacer(modifier = Modifier.width(10.dp))
                QuantityButton(R.drawable.ic_minus) {
                    if (item.minus()) {
                        onItemCalcFieldChanged()
                    }
                }
                if (onDelete != null) {
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = {
                            onDelete()
                            onItemCalcFieldChanged()
                        }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_delete),
                            contentDescription = null,
                            tint = MDRTheme.colors.secondaryText
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun QuantityButton(
    @DrawableRes icon: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(46.dp)
            .clip(CircleShape)
            .border(1.dp, MDRTheme.colors.secondaryText, CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(icon),
            contentDescription = null,
            tint = MDRTheme.colors.secondaryText
        )
    }
}

@Preview
@Composable
private fun EditableAccessoryItemPreview() {
    MDRTheme {
        EditableAccessoryItem(
            item = EditableAccessoryVM(id = 1, displayCategory = "Helm"),
            onItemCalcFieldChanged = {},
            onItemRequiredFieldChanged = {},
            onDelete = {}
        )
    }
}