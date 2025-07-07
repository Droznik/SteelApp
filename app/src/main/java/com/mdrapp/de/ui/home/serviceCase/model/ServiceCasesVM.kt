package com.mdrapp.de.ui.home.serviceCase.model

import android.text.Spanned
import android.text.SpannedString
import com.mdrapp.de.domain.model.ServiceCaseDM
import com.mdrapp.de.domain.model.ServiceOrderDM
import kotlinx.collections.immutable.persistentListOf

data class ServiceCaseListVM(
    val count: Int = 0,
    val activeCases: List<ServiceOrderVM> = emptyList(),
    val inactiveCases: List<ServiceOrderVM> = emptyList()
)

data class ServiceCaseVM(
    val id: Int = 0,
    val serviceType: String = "",
    val status: String = "",
    val timePeriod: String = ""
)

data class ServiceOrderVM(
    val orderId: Int = 0,
    val count: Int = 0,
    val orderNumber: String = "",
    val serviceCases: List<ServiceCaseVM> = persistentListOf()
)

data class ServiceCaseDetailVM(
    val id: Int = 0,
    val title: String = "",
    val status: String = "",
    val servicePackage: ServiceCasePackageVM = ServiceCasePackageVM(),
    val insuranceRate: InsuranceRateVM = InsuranceRateVM(),
    val bicycle: BicycleVM = BicycleVM()
)

data class ServiceCasePackageVM(
    val id: Int = 0,
    val hintText: Spanned = SpannedString(""),
    val orderDate: String = "",
    val fromDate: String = "",
    val toDate: String = "",
    val serviceType: String = ""
)

data class InsuranceRateVM(
    val id: Int = 0,
    val rate: String = "",
    val hintText: String = ""
)

data class BicycleVM(
    val id: Int = 0,
    val manufacturer: String = "",
    val model: String = "",
    val color: String = "",
    val frameNumber: String = ""
)

fun ServiceCaseDM.toVM(): ServiceCaseVM = ServiceCaseVM(
    id = id,
    serviceType = serviceType,
    status = status,
    timePeriod = timePeriod
)

fun ServiceOrderDM.toVM(): ServiceOrderVM = ServiceOrderVM(
    orderId = orderId,
    count = count,
    orderNumber = orderNumber,
    serviceCases = serviceCases.map { it.toVM() }
)
