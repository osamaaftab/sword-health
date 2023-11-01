package com.osamaaftab.dog.usecase

import com.osamaaftab.dog.data.DogRepository
import com.osamaaftab.dog.model.Breeds
import javax.inject.Inject

class BreedsUseCase @Inject constructor(private val dogRepository: DogRepository) {

    suspend fun getBreeds(page: Int): List<Breeds> {
        return dogRepository.getBreeds(page)
    }
}