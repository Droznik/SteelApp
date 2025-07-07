package com.mdrapp.de.domain.repository

import com.mdrapp.de.domain.model.ServiceCaseDetailDM
import com.mdrapp.de.domain.model.ServiceCaseListDM

interface ServiceCasesRepository {
    suspend fun getServiceCases(): ServiceCaseListDM
    suspend fun getSingleServiceCaseInfo(serviceCaseId: Long): ServiceCaseDetailDM
}