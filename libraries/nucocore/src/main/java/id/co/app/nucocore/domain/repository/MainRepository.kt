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
        val pokeDetail = mainClient.getPokemonDetail(poke.name)
        val types = arrayListOf<String>()
        pokeDetail.types.forEach { type ->
          types.add(type.type.name)
        }
        result.add(PokeCardModel(pokeDetail.id, pokeDetail.name, types))
      }
      emit(Result.Success(result))
    } catch (e: Exception) {
      emit(Result.Failure(e))
    }
  }

  fun getPokemonListBetter(): Flow<Result<List<DelegateAdapterItem>>> = flow {
    emit(Result.Loading)
    val newModels: ArrayList<DelegateAdapterItem> = arrayListOf()
    try {
      val data = mainClient.getPokemonList(getOffset(), loadLimit)
      nextPageKey += 1
      if (getCurrentPage() == 1) {
        newModels.add(SpaceModel(topSpace))
        newModels.add(PokeHeaderModel(data.count))
      }
      data.results.forEach { poke ->
        val det = mainClient.getPokemonDetail(poke.name)
        val pTypes : ArrayList<String> = arrayListOf()
        det.types.forEach { pTypes.add(it.type.name) }
        newModels.add(PokeCardModel(det.id, poke.name, pTypes))
      }
      emit(Result.Success(newModels))
    } catch (e: Exception) {
      emit(Result.Failure(e))
    }
  }
}