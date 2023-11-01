package com.osamaaftab.dog.usecase

import com.osamaaftab.dog.data.DogRepository
import com.osamaaftab.dog.model.BreedsImage
import javax.inject.Inject

class BreedsImageUseCase @Inject constructor(private val dogRepository: DogRepository) {

    suspend fun getBreedsImageById(id: String): BreedsImage {
        return dogRepository.getBreedsImageById(id)
    }
}