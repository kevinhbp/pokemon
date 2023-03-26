package id.co.app.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import id.co.app.home.viewModels.HomeViewModel
import id.co.app.nucocore.base.Result
import id.co.app.nucocore.domain.entities.Pokemon
import id.co.app.nucocore.domain.repository.MainRepository
import id.co.app.nucocore.extension.isFailure
import id.co.app.nucocore.extension.isLoading
import id.co.app.nucocore.extension.isSuccessful
import id.co.app.nucocore.extension.test
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val mainRepository = mockk<MainRepository>(relaxed = true)

    private lateinit var homeViewModel: HomeViewModel

    @Test
    fun `Test load initial page`() = runBlockingTest {
        every { mainRepository.fetchPokemonList(any()) } returns flow {
            emit(Result.Loading)
            emit(Result.Success(listOf(Pokemon(0, "", ""))))
        }
        homeViewModel = HomeViewModel(mainRepository)
        homeViewModel.pokemonListFlow.test {
            assert(expectItem().isLoading)
            assert(expectItem().isSuccessful)
        }
    }
    @Test
    fun `Test load initial page error`() = runBlocking {
        every { mainRepository.fetchPokemonList(any()) } returns flow {
            emit(Result.Loading)
            emit(Result.Failure(Throwable()))
        }
        homeViewModel = HomeViewModel(mainRepository)
        homeViewModel.pokemonListFlow.test {
            assert(expectItem().isLoading)
            assert(expectItem().isFailure)
        }
    }

    @Test
    fun `Test load more`() = runBlocking {
        every { mainRepository.fetchPokemonList(any()) } returns flow {
            emit(Result.Loading)
            emit(Result.Success(listOf(Pokemon(0, "", ""))))
        }
        homeViewModel = HomeViewModel(mainRepository)
        homeViewModel.pokemonListFlow.test {
            assert(expectItem().isLoading)
            assert(expectItem().isSuccessful)
        }

        homeViewModel.fetchPage(1)
        homeViewModel.pokemonListFlow.test {
            assert(expectItem().isLoading)
            assert(expectItem().isSuccessful)
        }
    }

    @Test
    fun `Test load more failed`() = runBlocking {
        every { mainRepository.fetchPokemonList(0) } returns flow {
            emit(Result.Loading)
            emit(Result.Success(listOf(Pokemon(0, "", ""))))
        }
        every { mainRepository.fetchPokemonList(1) } returns flow {
            emit(Result.Loading)
            emit(Result.Failure(Throwable()))
        }
        homeViewModel = HomeViewModel(mainRepository)
        homeViewModel.pokemonListFlow.test {
            assert(expectItem().isLoading)
            assert(expectItem().isSuccessful)
        }

        homeViewModel.fetchPage(1)
        homeViewModel.pokemonListFlow.test {
            assert(expectItem().isLoading)
            assert(expectItem().isFailure)
        }
    }
}