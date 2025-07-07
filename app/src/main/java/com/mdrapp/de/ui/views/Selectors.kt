package com.mdrapp.de.ui.views

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.mdrapp.de.R
import com.mdrapp.de.ui.theme.MDRTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch


@Composable
fun <T> TextSelector(
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier.fillMaxWidth(),
    title: String? = null,
    items: ImmutableList<T>,
    converter: (T) -> String = { it.toString() },
    placeholder: String = stringResource(R.string.please_choose),
    value: T?,
    searchSize: Int? = null,
    suffix: String = "",
    popupPadding: Int = 16,
    isError: Boolean = false,
    isRequired: Boolean = false,
    onValueSelect: (T) -> Unit
) {
    var showPopup by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        title?.let {
            Text(
                text = if (isRequired) "$title*" else title,
                style = MDRTheme.typography.regular,
                fontSize = 14.sp,
                color = MDRTheme.colors.secondaryText
            )
            Spacer(modifier = Modifier.height(6.dp))
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .clip(CircleShape)
                    .background(
                        color = MDRTheme.colors.primaryBackground,
                        shape = CircleShape
                    )
                    .border(
                        width = 1.dp,
                        color = if (isError) MDRTheme.colors.error else MDRTheme.colors.secondaryText,
                        shape = CircleShape
                    )
                    .clickable { if (items.isNotEmpty()) showPopup = true }
                    .padding(horizontal = 26.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextSelectorValue(value, placeholder, suffix, converter)
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    tint = MDRTheme.colors.secondaryText,
                    contentDescription = null
                )
            }
            if (showPopup) {
                SelectorPopup(onValueSelect, popupPadding, items, searchSize, suffix, converter) { showPopup = false }
            }
        }
    }
}

@Composable
private fun <T> SelectorPopup(
    onValueSelect: (T) -> Unit,
    popupPadding: Int,
    items: ImmutableList<T>,
    searchSize: Int?,
    suffix: String = "",
    converter: (T) -> String,
    onClosePopup: () -> Unit,
) {
    var searchedItems by remember { mutableStateOf(items) }
    var userInput by remember { mutableStateOf("") }
    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Popup(
        onDismissRequest = { onClosePopup() },
        alignment = Alignment.TopCenter,
        properties = PopupProperties(focusable = true)
    ) {
        val onItemClick = remember {
            { value: T ->
                onValueSelect(value)
                onClosePopup()
            }
        }
        Column (
            modifier = Modifier
                .padding(horizontal = popupPadding.dp)
                .fillMaxWidth()
                .background(
                    color = MDRTheme.colors.primaryBackground,
                    shape = RoundedCornerShape(26.dp)
                )
                .border(
                    width = 1.dp,
                    color = MDRTheme.colors.secondaryText,
                    shape = RoundedCornerShape(26.dp)
                )
                .padding(horizontal = 16.dp)
        ){
            if (searchSize != null) {
                Spacer(modifier = Modifier.height(16.dp))
                PrimaryOutlinedTextField(
                    value = userInput,
                    onValueChange = { newValue ->
                        userInput = newValue
                        searchedItems = when {
                            userInput.length >= searchSize -> items.filter { converter(it).contains(newValue, true) }.toPersistentList()
                            else -> items
                        }
                        if (!searchedItems.isEmpty()) {
                            scope.launch { lazyListState.scrollToItem(0) }
                        }
                    },
                    singleLine = true,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "search",
                            tint = MDRTheme.colors.titleText
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
            } else {
                Spacer(modifier = Modifier.height(16.dp))
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 500.dp),
                state = lazyListState,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(searchedItems) {
                    TextSelectorItem(it, suffix, converter) { value -> onItemClick(value) }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun <T> TextSelectorItem(
    item: T,
    suffix: String,
    converter: (T) -> String,
    onItemClick: (T) -> Unit
) {
    val convertedValue = converter(item)
    val resultValue = when {
        suffix.isEmpty() -> convertedValue
        else -> "$convertedValue $suffix"
    }

    Text(
        text = resultValue,
        style = MDRTheme.typography.regular,
        fontSize = 14.sp,
        color = MDRTheme.colors.secondaryText,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(item) }
            .padding(vertical = 8.dp)
    )
}

@Composable
private fun <T> RowScope.TextSelectorValue(
    value: T?,
    placeholder: String,
    suffix: String,
    converter: (T) -> String
) {
    val resultValue = when {
        value == null || ((value is String) && value.isEmpty()) -> placeholder
        suffix.isEmpty() -> converter(value)
        else -> "${converter(value)} $suffix"
    }

    Text(
        text = resultValue.takeIf { it.isNotEmpty() } ?: placeholder,
        style = MDRTheme.typography.regular,
        fontSize = 14.sp,
        color = MDRTheme.colors.secondaryText,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.Companion.weight(1f)
    )
}

@Preview
@Composable
fun TextSelectorPreview() {
    MDRTheme {
        Surface(color = MDRTheme.colors.primaryBackground) {
            TextSelector(
                title = "Anrede",
                value = "1",
                items = persistentListOf(),
                suffix = "Monate",
                isRequired = true,
                onValueSelect = {

                }
            )
        }
    }
}