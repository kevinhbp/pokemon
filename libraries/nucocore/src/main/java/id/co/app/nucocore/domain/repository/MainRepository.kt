package id.co.app.nucocore.domain.repository

import id.co.app.nucocore.domain.entities.pokemon.PokeResult
import id.co.app.nucocore.domain.mock.main.MainMockStorage
import id.co.app.nucocore.domain.network.nuco.MainClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import id.co.app.nucocore.base.Result
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.domain.entities.pokemon.Pokemon
import id.co.app.nucocore.domain.entities.pokemon.PokemonList
import id.co.app.nucocore.domain.entities.row.SpaceModel
import id.co.app.nucocore.domain.entities.view.PokeCardModel
import id.co.app.nucocore.domain.entities.view.PokeHeaderModel
import id.co.app.nucocore.extension.toDp

@Suppress("MemberVisibilityCanBePrivate", "unused")
class MainRepository(
  private val mainClient: MainClient,
  private val mockStorage: MainMockStorage
) {

  companion object {
    private const val loadLimit = 5
  }

  var topSpace = 80.toDp()

  private var nextPageKey = 1

  fun getCurrentPage(): Int = (nextPageKey - 1)

  private fun getOffset(): Int = loadLimit * getCurrentPage()

  fun refreshPokemonList() {
    nextPageKey = 1
  }

  fun getPokemonList(): Flow<Result<List<DelegateAdapterItem>>> = flow {
    emit(Result.Loading)
    try {
      val result: ArrayList<DelegateAdapterItem> = arrayListOf()
      val data = mainClient.getPokemonList(getOffset(), loadLimit)
      nextPageKey += 1
      if (getCurrentPage() == 1) {
        result.add(SpaceModel(topSpace))
        result.add(PokeHeaderModel(data.count))
      }
      data.results.forEach { poke ->
        val types = arrayListOf<String>()
        result.add(PokeCardModel(0, poke.name, types))
      }
      emit(Result.Success(result))
    } catch (e: Exception) {
      emit(Result.Failure(e))
    }
  }

  fun getPokemonDetail(key: String): Flow<Result<Pokemon>> = flow {
    emit(Result.Loading)
    try {
      val result = mainClient.getPokemonDetail(key)
      emit(Result.Success(result))
    } catch (e: Exception) {
      emit(Result.Failure(e))
    }
  }

}