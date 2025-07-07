package com.mdrapp.de.ui.home.home.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.mdrapp.de.ui.home.home.model.MdrTab
import com.mdrapp.de.ui.theme.MDRTheme
import kotlinx.collections.immutable.ImmutableList


@Composable
fun HomeTabsRow(
    tabs: ImmutableList<MdrTab>,
    selected: Int,
    setSelected: (Int) -> Unit,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .height(52.dp),
    allCaps: Boolean = false,
    horizontal: Arrangement.Horizontal = Arrangement.SpaceBetween,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically
) {
    Row(
        modifier = modifier
            .clip(CircleShape)
            .background(color = MDRTheme.colors.bg)
            .padding(horizontal = 31.5.dp),
        horizontalArrangement = horizontal,
        verticalAlignment = verticalAlignment
    ) {
        tabs.forEachIndexed { index, tab ->
            val isSelected = selected == index
            TabItem(
                isSelected = isSelected,
                stringId = tab.title,
                allCaps = allCaps
            ) { setSelected(index) }
        }
    }
}