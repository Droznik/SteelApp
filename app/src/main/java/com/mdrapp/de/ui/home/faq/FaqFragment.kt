package com.mdrapp.de.ui.home.faq

import android.text.TextUtils.indexOf
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mdrapp.de.R
import com.mdrapp.de.ext.browse
import com.mdrapp.de.ext.sendEmail
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.views.PrimaryButton
import com.mdrapp.de.ui.views.TitleTopBar


@Composable
fun FaqFragment() {
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        TitleTopBar(title = "FAQ")
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ){
            TitleText(text = stringResource(R.string.faq_general_questions_title))
            ClickableImageBanner(onClick = {
                context.browse("https://www.mein-dienstrad.de/faq/")
            })
            Spacer(modifier = Modifier.height(25.dp))
            ExpandableText(
                text = stringResource(R.string.faq_about_title),
                expandedText = stringResource(R.string.faq_about_text)
            )
            ExpandableText(
                text = stringResource(R.string.faq_benefit_title),
                expandedText = stringResource(R.string.faq_benefit_text)
            )
            ExpandableText(
                text = stringResource(R.string.faq_advantages_title),
                expandedText = stringResource(R.string.faq_advantages_text)
            )
            AnnotatedExpandableText(
                text = stringResource(R.string.faq_employer_title),
                clickableWord = "hier",
                expandedText = stringResource(R.string.faq_employer_text),
                onClickableWord = { context.browse("https://www.mein-dienstrad.de/tarifbindung/") })
            Spacer(modifier = Modifier.height(36.dp))
        }
        PrimaryButton(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 13.dp)
                .fillMaxWidth(),
            text = stringResource(R.string.faq_contact_us)
        ) {
            context.sendEmail("customercare@baronmobil.com")
        }
    }
}

@Composable
private fun TitleText(text: String) {
    Text(
        text = text,
        style = MDRTheme.typography.medium,
        fontSize = 32.sp,
        modifier = Modifier
            .padding(start = 16.dp, top = 25.dp, end = 16.dp, bottom = 8.dp)
    )
}

@Composable
private fun ClickableImageBanner(onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(232.dp)
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(id = R.drawable.faq_banner),
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 24.dp, start = 40.dp)
        ) {
            Text(
                text = stringResource(R.string.faq_no_answer_found),
                style = MDRTheme.typography.regular,
                color = MDRTheme.colors.lightText,
                fontSize = 20.sp
            )
            Text(
                text = stringResource(R.string.faq_continue_to_faq),
                style = MDRTheme.typography.bold,
                color = MDRTheme.colors.lightText,
                fontSize = 20.sp
            )
        }
        ArrowBox()
    }

}

@Composable
private fun BoxScope.ArrowBox() {
    Box(
        modifier = Modifier
            .padding(end = 38.dp, bottom = 24.dp)
            .size(49.dp)
            .align(Alignment.BottomEnd)
            .border(2.dp, MDRTheme.colors.lightText, shape = RoundedCornerShape(8.dp))
            .background(Color.Transparent.copy(alpha = 0.4f), shape = RoundedCornerShape(8.dp))
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Default.ArrowForward,
            contentDescription = null,
            tint = MDRTheme.colors.lightText,
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.Center)
        )
    }
}


@Composable
private fun ExpandableText(
    text: String,
    modifier: Modifier = Modifier,
    expandedText: String = ""
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = { expanded = !expanded })
            .padding(vertical = 15.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                style = MDRTheme.typography.semiBold,
                text = text,
                fontSize = 18.sp,
                maxLines = if (expanded) Int.MAX_VALUE else 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            )
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.AutoMirrored.Default.KeyboardArrowRight,
                contentDescription = "Expand/Collapse Icon",
                modifier = Modifier
                    .padding(end = 16.dp)
            )
        }
        if (expanded) {
            Text(
                style = MDRTheme.typography.regular,
                color = MDRTheme.colors.secondaryText,
                fontSize = 14.sp,
                text = expandedText,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
private fun AnnotatedExpandableText(
    text: String,
    clickableWord: String,
    onClickableWord: () -> Unit,
    modifier: Modifier = Modifier,
    expandedText: String = ""
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = { expanded = !expanded })
            .padding(vertical = 15.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                style = MDRTheme.typography.semiBold,
                text = text,
                fontSize = 18.sp,
                maxLines = if (expanded) Int.MAX_VALUE else 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            )
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.AutoMirrored.Default.KeyboardArrowRight,
                contentDescription = "Expand/Collapse Icon",
                modifier = Modifier
                    .padding(end = 16.dp)
            )
        }
        if (expanded) {
            val annotatedExpandedText = buildAnnotatedString {
                append(expandedText)
                clickableWord.let { word ->
                    val startIndex = indexOf(expandedText, word, 0)
                    val endIndex = startIndex + word.length
                    addStringAnnotation(
                        tag = "ExpandedClickable",
                        start = startIndex,
                        end = endIndex,
                        annotation = ""
                    )

                    addStyle(
                        style = SpanStyle(color = MDRTheme.colors.appBlue),
                        start = startIndex,
                        end = endIndex
                    )
                }
            }

            ClickableText(
                text = annotatedExpandedText,
                style = MDRTheme.typography.regular.copy(color = MDRTheme.colors.secondaryText),
                onClick = { offset ->
                    annotatedExpandedText.getStringAnnotations(
                        tag = "ExpandedClickable",
                        start = offset,
                        end = offset
                    ).firstOrNull()?.let {
                        onClickableWord()
                    }
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .wrapContentHeight()
            )
        }
    }
}

@Preview
@Composable
private fun FaqFragmentPreview() {
    MDRTheme {
        FaqFragment()
    }
}
