package id.co.app.nucocore.repository

import id.co.app.nucocore.base.Result
import id.co.app.nucocore.domain.entities.pokemon.*
import id.co.app.nucocore.domain.mock.main.MainMockStorage
import id.co.app.nucocore.domain.network.nuco.MainClient
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
    private val client = mockk<MainClient>(relaxed = true)
    private val mockResponse = mockk<MainMockStorage>(relaxed = true)
    private val mainRepository = MainRepository(client, dao, mockResponse)

    @Test
    fun `Test success flow with empty dao`() = runBlockingTest {
        val sprite = PokeSprite("","","","", "", "", "", "")
        val pokeResult = PokeResult("A","")
        val pokemon = Pokemon(1, "A", listOf(), 1.0, 1.0, sprite, listOf(), listOf(), 1, pokeResult, "")
        val listOfPokemon = listOf(pokemon)
        val listOfPokeResult = listOf(pokeResult)
        val pokeLoadResult = PokemonLoadResult(1, listOfPokemon)
        coEvery { dao.getPokemonList() } returns listOf()
        coEvery { client.getPokemonDetail("A") } returns pokemon
        coEvery { client.getPokemonList(0, 1) } returns PokemonList(1, listOfPokeResult, "", "")
        val resultFlow = mainRepository.getPokemonList(0, 1)
        resultFlow.test {
            assert(expectItem().isLoading)
            assert((expectItem() as Result.Success<PokemonLoadResult>).value == pokeLoadResult)
            expectComplete()
        }
    }

    @Test
    fun `Test success flow with dao`() = runBlockingTest {
        val sprite = PokeSprite("","","","", "", "", "", "")
        val pokeResult = PokeResult("A","")
        val pokemon = Pokemon(1, "A", listOf(), 1.0, 1.0, sprite, listOf(), listOf(), 1, pokeResult, "")
        val listOfPokemon = listOf(pokemon)
        val listOfPokeResult = listOf(pokeResult)
        val pokeLoadResult = PokemonLoadResult(1, listOfPokemon)
        coEvery { dao.getPokemonList() } returns listOfPokemon
        coEvery { client.getPokemonDetail("A") } returns pokemon
        coEvery { client.getPokemonList(0, 1) } returns PokemonList(1, listOfPokeResult, "", "")
        val resultFlow = mainRepository.getPokemonList(0, 1)
        resultFlow.test {
            assert(expectItem().isLoading)
            assert((expectItem() as Result.Success<PokemonLoadResult>).value == pokeLoadResult)
            expectComplete()
        }
    }

    @Test
    fun `Test error timeout flow`() = runBlockingTest {
        val timedOutException = TimeoutException()
        coEvery { dao.getPokemonList() } returns listOf()
        coEvery { client.getPokemonList(0, 1) } throws timedOutException
        val resultFlow = mainRepository.getPokemonList(0, 1)

        resultFlow.test {
            assert(expectItem().isLoading)
            assert((expectItem() as Result.Failure).throwable == timedOutException)
            expectComplete()
        }
    }
}