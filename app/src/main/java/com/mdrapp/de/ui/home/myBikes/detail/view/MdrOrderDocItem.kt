package com.mdrapp.de.ui.home.myBikes.detail.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mdrapp.de.R
import com.mdrapp.de.ui.home.myBikes.detail.MdrOrderDocVM
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.views.TextBlockDivider


@Composable
fun MdrOrderDocItem(
    doc: MdrOrderDocVM,
    index: Int,
    size: Int,
    onDocClick: (Long) -> Unit
) {
    val isLast = index >= (size - 1)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = doc.allowedToOpen) { onDocClick(doc.id) }
    ) {
        Text(
            text = doc.name,
            style = MDRTheme.typography.medium,
            fontSize = 16.sp,
            color = when {
                doc.employerNote != null -> MDRTheme.colors.error
                !doc.allowedToOpen -> MDRTheme.colors.secondaryText
                else -> MDRTheme.colors.titleText
            },
            modifier = Modifier.fillMaxWidth()
        )
       doc.employerNote?.let {
            Spacer(modifier = Modifier.height(38.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.my_mdr_bikes_doc_error_title),
                    style = MDRTheme.typography.medium,
                    fontSize = 16.sp,
                    color = MDRTheme.colors.secondaryText,
                )
                Spacer(modifier = Modifier.width(32.dp))
                Text(
                    text = it,
                    style = MDRTheme.typography.medium,
                    fontSize = 16.sp,
                    color = MDRTheme.colors.error,
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        if (!isLast) TextBlockDivider()
    }
}

@Preview
@Composable
private fun MdrOrderDocItemPreview() {
    MDRTheme {
        MdrOrderDocItem(
            doc = MdrOrderDocVM(
                id = 1,
                name = "ÜberlassungsvertragDienstrad_RAGmbH_02122022.pdf",
                allowedToOpen = true,
                employerNote = "Antragsstelle hat seinen Antrag zurückgezogen",
            ),
            index = 1,
            size = 3
        ) {}
    }
}