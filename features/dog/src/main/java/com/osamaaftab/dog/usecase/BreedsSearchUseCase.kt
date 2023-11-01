package com.osamaaftab.dog.usecase

import com.osamaaftab.dog.data.DogRepository
import com.osamaaftab.dog.model.Breeds
import javax.inject.Inject

class BreedsSearchUseCase @Inject constructor(private val dogRepository: DogRepository) {

    suspend fun getSearchBreeds(term: String): List<Breeds> {
        return dogRepository.getSearchBreeds(term)
    }
}