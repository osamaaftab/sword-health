package com.osamaaftab.dog.data

import com.osamaaftab.dog.data.service.IDogService
import com.osamaaftab.dog.model.Breeds
import com.osamaaftab.dog.model.BreedsImage
import javax.inject.Inject

interface DogRepository {
    suspend fun getBreeds(page: Int): List<Breeds>
    suspend fun getSearchBreeds(query: String): List<Breeds>
    suspend fun getBreedsById(id: Int): Breeds
    suspend fun getBreedsImageById(id: String): BreedsImage
}

class DogRepositoryImpl @Inject constructor(private val dogApiService: IDogService)
    :DogRepository {
    override suspend fun getBreeds(page: Int): List<Breeds> = dogApiService.getBreeds(page)
    override suspend fun getSearchBreeds(query: String): List<Breeds> = dogApiService.getSearchBreeds(query)
    override suspend fun getBreedsById(id: Int): Breeds = dogApiService.getBreedsById(id)
    override suspend fun getBreedsImageById(id: String): BreedsImage = dogApiService.getBreedsImageById(id)
}
