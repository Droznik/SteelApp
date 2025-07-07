package com.mdrapp.de.ui.home.serviceCase

import com.mdrapp.de.common.viewmodel.ContractViewModel
import com.mdrapp.de.domain.repository.ServiceCasesRepository
import com.mdrapp.de.navigation.HomeNavHost
import com.mdrapp.de.navigation.MyServiceCasesGraph
import com.mdrapp.de.navigation.NavEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ServiceCasesViewModel @Inject constructor(
    private val serviceCasesRepository: ServiceCasesRepository
) : ContractViewModel<ServiceCasesState, ServiceCasesEvent>() {
    override val initialState = ServiceCasesState()

    override fun onEvent(event: ServiceCasesEvent) {
        when (event) {
            is ServiceCasesEvent.OnServiceCaseClick -> getSingleServiceCaseInfo(event.serviceCaseId.toLong())
            ServiceCasesEvent.OnBackClick -> navigate(NavEvent.Back)
            is ServiceCasesEvent.OnTabSelected -> _state.update { it.copy(selectedTab = event.tab) }
        }
    }

    init {
        getServiceCases()
    }

    private fun getServiceCases() {
        simpleLaunch {
            val serviceCases =
                withContext(Dispatchers.IO) { serviceCasesRepository.getServiceCases() }
            _state.update { it.fromServiceCaseListDM(serviceCases)}
        }
    }

    private fun getSingleServiceCaseInfo(serviceCaseId: Long) {
        simpleLaunch {
            val serviceCaseDetail =
                withContext(Dispatchers.IO) { serviceCasesRepository.getSingleServiceCaseInfo(serviceCaseId) }
            _state.update { it.fromServiceCaseDetailDM(serviceCaseDetail) }
        }
        navigate(NavEvent.To(MyServiceCasesGraph.ServiceCaseDetail.route))
    }
}