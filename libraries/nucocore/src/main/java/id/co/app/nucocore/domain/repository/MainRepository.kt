package id.co.app.nucocore.domain.repository

import id.co.app.nucocore.base.Result
import id.co.app.nucocore.domain.entities.pokemon.Pokemon
import id.co.app.nucocore.domain.entities.pokemon.PokemonList
import id.co.app.nucocore.domain.entities.pokemon.PokemonLoadResult
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

}