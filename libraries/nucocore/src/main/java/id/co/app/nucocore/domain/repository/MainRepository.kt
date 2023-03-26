package id.co.app.nucocore.domain.repository

import id.co.app.nucocore.domain.entities.pokemon.PokeResult
import id.co.app.nucocore.domain.mock.main.MainMockStorage
import id.co.app.nucocore.domain.network.nuco.MainClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import id.co.app.nucocore.base.Result

@Suppress("MemberVisibilityCanBePrivate", "unused")
class MainRepository(
  private val mainClient: MainClient,
  private val mockStorage: MainMockStorage
) {

  companion object {
    private const val loadLimit = 5
  }

  private var nextPageKey = 1

  fun getCurrentPage(): Int = (nextPageKey - 1)

  private fun getOffset(): Int = loadLimit * getCurrentPage()

  fun refreshPokemonList() {
    nextPageKey = 1
    getPokemonList()
  }

  fun getPokemonList(): Flow<Result<List<PokeResult>>> = flow {
    emit(Result.Loading)
    try {
      val data = mainClient.getPokemonList(getOffset(), loadLimit)
      nextPageKey += 1
      emit(Result.Success(data))
    } catch (e: Exception) {
      emit(Result.Failure(e))
    }

  }
}