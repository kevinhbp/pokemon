package id.co.app.nucocore.repository

import id.co.app.nucocore.base.Result
import id.co.app.nucocore.domain.entities.Pokemon
import id.co.app.nucocore.domain.entities.PokemonResponse
import id.co.app.nucocore.domain.network.pokedex.PokedexClient
import id.co.app.nucocore.domain.persistence.PokemonDao
import id.co.app.nucocore.domain.repository.MainRepository
import id.co.app.nucocore.extension.isLoading
import id.co.app.nucocore.extension.test
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.util.concurrent.TimeoutException
import kotlin.time.ExperimentalTime

@ExperimentalTime
class MainRepositoryTest {
    private val dao = mockk<PokemonDao>(relaxed = true)
    private val client = mockk<PokedexClient>(relaxed = true)
    private val mainRepository = MainRepository(dao, client)

    @Test
    fun `Test success flow with empty dao`() = runBlockingTest {
        val listPokemon = listOf(Pokemon(0, "A", "A"))
        coEvery { dao.getPokemonList(0) } returns listOf()
        coEvery { client.fetchPokemonList(0) } returns PokemonResponse(1, "", "", listPokemon)
        val resultFlow = mainRepository.fetchPokemonList(0)
        resultFlow.test {
            assert(expectItem().isLoading)
            assert((expectItem() as Result.Success<List<Pokemon>>).value == listPokemon)
            expectComplete()
        }
    }

    @Test
    fun `Test success flow with dao`() = runBlockingTest {
        val listPokemon = listOf(Pokemon(0, "A", "A"))
        coEvery { dao.getPokemonList(0) } returns listPokemon

        val resultFlow = mainRepository.fetchPokemonList(0)

        resultFlow.test {
            assert(expectItem().isLoading)
            assert((expectItem() as Result.Success<List<Pokemon>>).value == listPokemon)
            expectComplete()
        }
    }

    @Test
    fun `Test error timeout flow`() = runBlockingTest {
        val timedOutException = TimeoutException()
        coEvery { dao.getPokemonList(0) } returns listOf()
        coEvery { client.fetchPokemonList(0) } throws timedOutException
        val resultFlow = mainRepository.fetchPokemonList(0)

        resultFlow.test {
            assert(expectItem().isLoading)
            assert((expectItem() as Result.Failure).throwable == timedOutException)
            expectComplete()
        }
    }
}