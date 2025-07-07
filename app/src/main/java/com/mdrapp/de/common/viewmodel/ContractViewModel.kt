package com.mdrapp.de.common.viewmodel

abstract class ContractViewModel<S, E> : StateViewModel<S>(), EventHandler<E>