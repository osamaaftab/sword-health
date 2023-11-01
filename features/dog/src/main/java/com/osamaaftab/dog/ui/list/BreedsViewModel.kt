package com.osamaaftab.dog.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osamaaftab.dog.model.Breeds
import com.osamaaftab.dog.usecase.BreedsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedsViewModel @Inject constructor(
    private val breedsUseCase: BreedsUseCase) : ViewModel() {

    private val _breedsLiveData: MutableLiveData<List<Breeds>> = MutableLiveData()
    val breedsLiveData: LiveData<List<Breeds>> get() = _breedsLiveData

    private val _breedsErrorLiveData: MutableLiveData<String> = MutableLiveData()
    val breedsErrorLiveData: LiveData<String> get() = _breedsErrorLiveData

    private var currentPage = 0

    fun loadBreeds() {
        getBreeds(currentPage)
    }

    fun loadMoreBreeds() {
        currentPage++
        getBreeds(currentPage)
    }

    private fun getBreeds(page: Int) {
        viewModelScope.launch {
            try {
                val dogs = breedsUseCase.getBreeds(page)
                _breedsLiveData.postValue(dogs.sortedBy { p->p.name })
            } catch (e: Exception) {
                val error = "Network error"
                _breedsErrorLiveData.value = error
            }
        }
    }
}