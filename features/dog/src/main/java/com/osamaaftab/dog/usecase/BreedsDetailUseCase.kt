package com.osamaaftab.dog.usecase

import com.osamaaftab.dog.data.DogRepository
import com.osamaaftab.dog.model.Breeds
import javax.inject.Inject

class BreedsDetailUseCase @Inject constructor(private val dogRepository: DogRepository) {

    suspend fun getBreedsById(id: Int): Breeds {
        return dogRepository.getBreedsById(id)
    }
}