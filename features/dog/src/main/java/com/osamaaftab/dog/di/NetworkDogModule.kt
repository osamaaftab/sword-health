package com.osamaaftab.dog.di

import com.osamaaftab.dog.data.DogRepository
import com.osamaaftab.dog.data.DogRepositoryImpl
import com.osamaaftab.dog.data.service.IDogService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideDogApiService(retrofit: Retrofit): IDogService {
        return retrofit.create(IDogService::class.java)
    }
    @Provides
    fun provideDogRepository(iDogService: IDogService): DogRepository {
        return DogRepositoryImpl(iDogService)
    }
}
