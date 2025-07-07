package com.mdrapp.de.common.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.mdrapp.de.navigation.NavEvent
import com.mdrapp.de.ui.util.UIText
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    var loading by mutableStateOf(false)
    val error = Channel<Throwable>()
    val message = Channel<UIText>()
    val navigate = Channel<NavEvent>()

    val baseErrorHandler = CoroutineExceptionHandler { _, throwable ->
        setError(throwable)
    }

    fun showMessage(msg: UIText) {
        viewModelScope.launch {
            message.send(msg)
        }
    }

    fun setError(e: Throwable) {
        viewModelScope.launch {
            error.send(e)
        }
        e.printStackTrace()
        Firebase.crashlytics.recordException(e)
    }

    fun navigate(navEvent: NavEvent) {
        viewModelScope.launch {
            navigate.send(navEvent)
        }
    }

    inline fun simpleLaunch(
        errorHandler: CoroutineExceptionHandler = baseErrorHandler,
        crossinline block: suspend () -> Unit
    ) = viewModelScope.launch(errorHandler) {
        loading = true
        block()
    }.also { it.invokeOnCompletion { loading = false } }
}