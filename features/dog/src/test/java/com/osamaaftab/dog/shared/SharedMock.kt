package com.osamaaftab.dog.shared

import com.osamaaftab.dog.model.Breeds
import com.osamaaftab.dog.model.BreedsImage

object SharedMock {

     fun getBreed() = Breeds(
        1,
        "Bulldog",
        "BreedFor",
        "BreedGroup",
        "BreedOrigin",
        "BreedTemperament",
         "ImageId",
        BreedsImage("url")
    )
}