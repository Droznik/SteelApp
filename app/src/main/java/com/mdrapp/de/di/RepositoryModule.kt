package com.mdrapp.de.di

import com.mdrapp.de.data.repository.AuthRepositoryImpl
import com.mdrapp.de.data.repository.BikePassRepositoryImpl
import com.mdrapp.de.data.repository.FavouritesRepositoryImpl
import com.mdrapp.de.data.repository.MapRepositoryImpl
import com.mdrapp.de.data.repository.MyBikesRepositoryImpl
import com.mdrapp.de.data.repository.OfferRepositoryImpl
import com.mdrapp.de.data.repository.OrderRepositoryImpl
import com.mdrapp.de.data.repository.ProductRepositoryImpl
import com.mdrapp.de.data.repository.PurchaseRepositoryImpl
import com.mdrapp.de.data.repository.ServiceCasesRepositoryImpl
import com.mdrapp.de.data.repository.UserRepositoryImpl
import com.mdrapp.de.domain.repository.AuthRepository
import com.mdrapp.de.domain.repository.BikePassRepository
import com.mdrapp.de.domain.repository.FavouritesRepository
import com.mdrapp.de.domain.repository.MapRepository
import com.mdrapp.de.domain.repository.MyBikesRepository
import com.mdrapp.de.domain.repository.OfferRepository
import com.mdrapp.de.domain.repository.OrderRepository
import com.mdrapp.de.domain.repository.ProductRepository
import com.mdrapp.de.domain.repository.PurchaseRepository
import com.mdrapp.de.domain.repository.ServiceCasesRepository
import com.mdrapp.de.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun provideAuthRepository(repo: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    fun providePurchaseRepository(repo: PurchaseRepositoryImpl): PurchaseRepository

    @Binds
    @Singleton
    fun provideProductRepository(repo: ProductRepositoryImpl): ProductRepository

    @Binds
    @Singleton
    fun provideFavouritesRepository(repo: FavouritesRepositoryImpl): FavouritesRepository

    @Binds
    @Singleton
    fun provideOrderRepository(repo: OrderRepositoryImpl): OrderRepository

    @Binds
    @Singleton
    fun provideUserRepository(repo: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    fun provideBikePassRepository(repo: BikePassRepositoryImpl): BikePassRepository

    @Binds
    @Singleton
    fun provideOfferRepository(repo: OfferRepositoryImpl): OfferRepository

    @Binds
    @Singleton
    fun provideMapRepository(repo: MapRepositoryImpl): MapRepository

    @Binds
    @Singleton
    fun provideServiceCasesRepository(repo: ServiceCasesRepositoryImpl): ServiceCasesRepository

    @Binds
    @Singleton
    fun provideMyBikesRepository(repo: MyBikesRepositoryImpl): MyBikesRepository
}