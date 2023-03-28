package id.co.app.nucocore.domain.repository

import id.co.app.nucocore.base.Result
import id.co.app.nucocore.domain.entities.pokemon.*
import id.co.app.nucocore.domain.mock.main.MainMockStorage
import id.co.app.nucocore.domain.network.nuco.MainClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Suppress("MemberVisibilityCanBePrivate", "unused")
class MainRepository(
  private val mainClient: MainClient,
  private val mockStorage: MainMockStorage
) {

  fun getPokemonList(offset: Int, limit: Int): Flow<Result<PokemonLoadResult>> = flow {
    emit(Result.Loading)
    try {
      val data = mainClient.getPokemonList(offset, limit)
      val pokemonList = ArrayList<Pokemon>()
      data.results.forEach {
        val detail = mainClient.getPokemonDetail(it.name)
        pokemonList.add(detail)
      }
      val result = PokemonLoadResult(data.count, pokemonList)
      emit(Result.Success(result))
    } catch (e: Exception) {
      emit(Result.Failure(e))
    }
  }

  fun getPokemonTypeList(): Flow<Result<List<PokeResult>>> = flow {
    emit(Result.Loading)
    try {
      val data = mainClient.getPokemonTypeList()
      emit(Result.Success(data.results))
    } catch (e: Exception) {
      emit(Result.Failure(e))
    }
  }

  fun getPokemonListWithTypeId(id: Any): Flow<Result<List<PokeResult>>> = flow {
    emit(Result.Loading)
    try {
      val data = mainClient.getPokemonWithType(id)
      val pokemonList = ArrayList<PokeResult>()
      data.pokemon.forEach {
        pokemonList.add(it.pokemon)
      }
      emit(Result.Success(pokemonList))
    } catch (e: Exception) {
      emit(Result.Failure(e))
    }
  }

  // WIP
  fun getPokemonEvolutionList(id: Any): Flow<List<PokemonEvolutionSolution>> = flow {
    val source = mainClient.getPokemonEvolutionChain(id)
    val result = ArrayList<PokemonEvolutionSolution>()
    val chains = ArrayList<PokemonEvolutionChain>()

    emit(result)
  }

  private suspend fun mapChainToSolution(chain: PokemonEvolutionChain): List<PokemonEvolutionSolution> {
    val result = ArrayList<PokemonEvolutionSolution>()
    val from = getPokemonFromChain(chain)
    chain.evolvesTo.forEach { evoChain ->
      val to = getPokemonFromChain(evoChain)
      result.add(PokemonEvolutionSolution(from, to))
    }
    return result
  }

  private suspend fun getPokemonFromChain(chain: PokemonEvolutionChain): Pokemon {
    return mainClient.getPokemonDetail(chain.species.name)
  }

}