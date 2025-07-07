package com.mdrapp.de.ui.home.serviceCase

import android.text.Spanned
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.core.text.HtmlCompat
import com.mdrapp.de.domain.model.ServiceCaseDetailDM
import com.mdrapp.de.domain.model.ServiceCaseListDM
import com.mdrapp.de.ui.home.serviceCase.model.BicycleVM
import com.mdrapp.de.ui.home.serviceCase.model.InsuranceRateVM
import com.mdrapp.de.ui.home.serviceCase.model.ServiceCaseDetailVM
import com.mdrapp.de.ui.home.serviceCase.model.ServiceCaseListVM
import com.mdrapp.de.ui.home.serviceCase.model.ServiceCasePackageVM
import com.mdrapp.de.ui.home.serviceCase.model.toVM

data class ServiceCasesState(
    val selectedTab: Int = 0,
    val serviceCases: ServiceCaseListVM = ServiceCaseListVM(),
    val serviceCaseDetail: ServiceCaseDetailVM = ServiceCaseDetailVM()
)

sealed interface ServiceCasesEvent {
    data class OnServiceCaseClick(val serviceCaseId: Int) : ServiceCasesEvent
    data class OnTabSelected(val tab: Int) : ServiceCasesEvent
    data object OnBackClick : ServiceCasesEvent
}

fun ServiceCasesState.fromServiceCaseDetailDM(detail: ServiceCaseDetailDM) = this.copy(
    serviceCaseDetail = ServiceCaseDetailVM(
        id = detail.id,
        title = detail.title,
        status = detail.status,
        servicePackage = ServiceCasePackageVM(
            id = detail.servicePackage.id,
            hintText = HtmlCompat.fromHtml(detail.servicePackage.hintText, HtmlCompat.FROM_HTML_MODE_LEGACY),
            orderDate = detail.servicePackage.orderDate,
            fromDate = detail.servicePackage.fromDate,
            toDate = detail.servicePackage.toDate,
            serviceType = detail.servicePackage.serviceType,
        ),
        insuranceRate = InsuranceRateVM(
            id = detail.insuranceRate.id,
            rate = detail.insuranceRate.rate,
            hintText = HtmlCompat.fromHtml(detail.insuranceRate.hintText, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
        ),
        bicycle = BicycleVM(
            id = detail.bicycle.id,
            manufacturer = detail.bicycle.manufacturer,
            model = detail.bicycle.model,
            color = detail.bicycle.color,
            frameNumber = detail.bicycle.frameNumber,
        )
    )
)

fun ServiceCasesState.fromServiceCaseListDM(list: ServiceCaseListDM) = this.copy(
    serviceCases = ServiceCaseListVM(
        count = list.count,
        activeCases = list.activeCases.map { it.toVM() },
        inactiveCases = list.inactiveCases.map { it.toVM() }
    )
)

fun spannedToAnnotatedString(spanned: Spanned,color: Color): AnnotatedString {
    val builder = AnnotatedString.Builder()
    val spans = spanned.getSpans(0, spanned.length, Any::class.java)

    var currentIndex = 0
    for (span in spans) {
        val start = spanned.getSpanStart(span)
        val end = spanned.getSpanEnd(span)
        val spanText = spanned.substring(start, end)

        if (start > currentIndex) {
            builder.append(spanned.substring(currentIndex, start))
        }

        when (span) {
            is android.text.style.URLSpan -> {
                val link = span.url
                val startIdx = builder.length
                builder.append(spanText)
                val endIdx = builder.length
                builder.addStyle(
                    style = SpanStyle(
                        color = color,
                        textDecoration = TextDecoration.Underline
                    ), start = startIdx, end = endIdx
                )
                builder.addStringAnnotation(
                    tag = "URL",
                    annotation = link,
                    start = startIdx,
                    end = endIdx
                )
            }
            else -> {
                builder.append(spanText)
            }
        }
        currentIndex = end
    }

    if (currentIndex < spanned.length) {
        builder.append(spanned.substring(currentIndex))
    }

    return builder.toAnnotatedString()
}