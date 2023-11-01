package com.osamaaftab.dog.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.osamaaftab.dog.model.Breeds
import com.osamaaftab.dog.shared.SharedMock
import com.osamaaftab.dog.ui.search.BreedsSearchViewModel
import com.osamaaftab.dog.usecase.BreedsSearchUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BreedsSearchViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var breedsSearchUseCase: BreedsSearchUseCase

    @MockK
    private lateinit var breedsViewModel: BreedsSearchViewModel

    private val term = "Buldog"
    private val networkError = "Network error"

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)

        breedsViewModel = BreedsSearchViewModel(breedsSearchUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given that the user wants to search list the breeds When searching Then it should display a list`() = runBlocking {
        // Given
        val mockBreeds = listOf(SharedMock.getBreed())

        coEvery { breedsSearchUseCase.getSearchBreeds(term) } returns mockBreeds

        val breedsObserver = mockk<Observer<List<Breeds>>>(relaxed = true)
        breedsViewModel.breedsLiveData.observeForever(breedsObserver)

        // When
        breedsViewModel.getSearchBreeds(term)

        // Then

        verify {
            breedsObserver.onChanged(mockBreeds)
        }
    }

    @Test
    fun `Given that the user wants to search for breeds When searching and not finding Then it must display an information`() = runBlocking {
        // Given
        val mockBreeds = emptyList<Breeds>()

        coEvery { breedsSearchUseCase.getSearchBreeds(term) } returns mockBreeds

        val breedsObserver = mockk<Observer<Unit>>(relaxed = true)
        breedsViewModel.breedsErrorSearchLiveData.observeForever(breedsObserver)

        // When
        breedsViewModel.getSearchBreeds(term)

        // Then

        verify {
            breedsObserver.onChanged(Unit)
        }
    }


    @Test
    fun `Given that the user wants to search list the breeds When an error occurred in the search Then it must present an error to the user`() = runBlocking {
        // Given
        coEvery { breedsSearchUseCase.getSearchBreeds(term) } throws Exception(networkError)

        val errorObserver = mockk<Observer<String>>(relaxed = true)
        breedsViewModel.breedsErrorLiveData.observeForever(errorObserver)

        // When
        breedsViewModel.getSearchBreeds(term)

        // Then
        verify {
            errorObserver.onChanged(networkError)
        }
    }
}
