package com.mdrapp.de.ui.home.myBikes.detail

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import com.mdrapp.de.R
import com.mdrapp.de.common.viewmodel.ContractViewModel
import com.mdrapp.de.domain.repository.MyBikesRepository
import com.mdrapp.de.navigation.HomeNavHost
import com.mdrapp.de.navigation.MY_MDR_BIKES_NUMBER_ARG
import com.mdrapp.de.navigation.MY_MDR_BIKES_TYPE_ARG
import com.mdrapp.de.navigation.MY_MDR_BIKE_ID_ARG
import com.mdrapp.de.navigation.NavEvent
import com.mdrapp.de.ui.home.myBikes.MyMdrBikesType
import com.mdrapp.de.ui.util.FileHelper
import com.mdrapp.de.ui.util.UIText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MyMdrBikesDetailViewModel @Inject constructor(
    private val myBikesRepo: MyBikesRepository,
    private val fileHelper: FileHelper,
    savedStateHandle: SavedStateHandle
) : ContractViewModel<MyMdrBikesDetailState, MyMdrBikesDetailEvent>() {

    private val type: MyMdrBikesType = MyMdrBikesType.valueOf(savedStateHandle[MY_MDR_BIKES_TYPE_ARG]!!)
    private val id: Long = savedStateHandle.get<Long>(MY_MDR_BIKE_ID_ARG)!!
    private val number: String = savedStateHandle[MY_MDR_BIKES_NUMBER_ARG]!!

    override val initialState = MyMdrBikesDetailState(type, number)

    init {
        simpleLaunch { loadOrderInfo() }
    }

    override fun onEvent(event: MyMdrBikesDetailEvent) {
        when(event) {
            MyMdrBikesDetailEvent.Back -> navigate(NavEvent.Back)
            is MyMdrBikesDetailEvent.OnCommonBlockClick -> onCommonBlockClick(event.isExpanded)
            is MyMdrBikesDetailEvent.OnDocumentBlockClick -> onDocumentBlockClick(event.isExpanded)
            is MyMdrBikesDetailEvent.OnStatusBlockClick -> onStatusBlockClick(event.isExpanded)
            is MyMdrBikesDetailEvent.GetDocumentUrl -> getDocumentUrl(event.id, event.onResult)
            is MyMdrBikesDetailEvent.UploadDocument -> uploadDocument(event.uri)
            MyMdrBikesDetailEvent.ShowFAQ -> navigate(NavEvent.To(HomeNavHost.Faq.route))
        }
    }

    private suspend fun loadOrderInfo() {
        val orderInfo = withContext(Dispatchers.IO) { myBikesRepo.getMdrOrderDetail(id) }

        _state.update { it.fromMdrOrderDetailDM(orderInfo) }
    }

    private fun onCommonBlockClick(isExpanded: Boolean) {
        _state.update { it.copy(
            isCommonBlockExpanded = !isExpanded,
            isDocumentBlockExpanded = false,
            isStatusBlockExpanded = false
        ) }
    }

    private fun onDocumentBlockClick(isExpanded: Boolean) {
        _state.update { it.copy(
            isCommonBlockExpanded = false,
            isDocumentBlockExpanded = !isExpanded,
            isStatusBlockExpanded = false
        ) }
    }

    private fun onStatusBlockClick(isExpanded: Boolean) {
        _state.update { it.copy(
            isCommonBlockExpanded = false,
            isDocumentBlockExpanded = false,
            isStatusBlockExpanded = !isExpanded
        ) }
    }

    private fun getDocumentUrl(id: Long, onResult: (String) -> Unit) = simpleLaunch {
        val url = withContext(Dispatchers.IO) { myBikesRepo.getMdrOrderFileUrl(id) }

        if ( url.isNotEmpty() ) {
            onResult(url)
        }
    }

    private fun uploadDocument(uri: Uri) = simpleLaunch {
        val base64 = withContext(Dispatchers.Default){ fileHelper.getPDFBase64(uri) }
        val name = fileHelper.getOriginalFileName(uri)
        val mime = "application/pdf"

        withContext(Dispatchers.IO) { myBikesRepo.uploadDocument(id, name, mime, base64) }
        showMessage(UIText.StringResource(R.string.file_uploaded_successfully))
        loadOrderInfo()
    }
}
