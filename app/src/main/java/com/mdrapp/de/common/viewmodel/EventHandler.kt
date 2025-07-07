package com.mdrapp.de.common.viewmodel

interface EventHandler<T> {
    fun onEvent(event: T)
}