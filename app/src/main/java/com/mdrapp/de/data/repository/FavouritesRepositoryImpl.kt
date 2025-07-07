package com.mdrapp.de.data.repository

import com.mdrapp.de.data.MdrApiService
import com.mdrapp.de.domain.repository.FavouritesRepository
import javax.inject.Inject


class FavouritesRepositoryImpl @Inject constructor(
    private val api: MdrApiService,
) : FavouritesRepository {


}