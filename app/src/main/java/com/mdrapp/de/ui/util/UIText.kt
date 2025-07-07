package com.mdrapp.de.ui.util

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UIText {
    data class DynamicString(val value: String): UIText()
    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any
    ): UIText()

    fun asString(context: Context) = when(this) {
        is DynamicString -> value
        is StringResource -> context.getString(resId, *args)
    }

    @Composable
    fun asString() = when(this) {
        is DynamicString -> value
        is StringResource -> stringResource(resId, *args)
    }
}
