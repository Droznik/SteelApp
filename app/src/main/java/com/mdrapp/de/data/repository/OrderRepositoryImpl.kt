package com.mdrapp.de.data.repository

import com.mdrapp.de.data.MdrApiService
import com.mdrapp.de.domain.repository.OrderRepository
import javax.inject.Inject


class OrderRepositoryImpl @Inject constructor(
    private val api: MdrApiService
) : OrderRepository {


}