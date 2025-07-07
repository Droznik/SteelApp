package com.mdrapp.de.common.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class StateViewModel<S> : BaseViewModel() {

    abstract val initialState: S
    protected val _state: MutableStateFlow<S> by lazy { MutableStateFlow(initialState) }
    val state: StateFlow<S> get() = _state
}