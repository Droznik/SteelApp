package com.mdrapp.de.data.repository

import com.mdrapp.de.data.MdrApiService
import com.mdrapp.de.data.model.remote.IdRequest
import com.mdrapp.de.data.model.remote.baseResponse.mdr.handled
import com.mdrapp.de.data.model.remote.serviceCase.toDomain
import com.mdrapp.de.domain.model.ServiceCaseDetailDM
import com.mdrapp.de.domain.model.ServiceCaseListDM
import com.mdrapp.de.domain.repository.ServiceCasesRepository
import javax.inject.Inject

class ServiceCasesRepositoryImpl @Inject constructor(
    private val api: MdrApiService
) : ServiceCasesRepository {
    override suspend fun getServiceCases(): ServiceCaseListDM =
        api.getServiceCaseList().handled().toDomain()


    override suspend fun getSingleServiceCaseInfo(serviceCaseId: Long): ServiceCaseDetailDM =
        api.getServiceCaseDetail(IdRequest(serviceCaseId)).handled().toDomain()
}
