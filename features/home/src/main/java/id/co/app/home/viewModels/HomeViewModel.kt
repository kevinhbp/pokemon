package id.co.app.home.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.domain.entities.pokemon.Pokemon
import id.co.app.nucocore.domain.entities.pokemon.PokemonList
import id.co.app.nucocore.domain.entities.pokemon.PokemonLoadResult
import id.co.app.nucocore.domain.entities.row.SpaceModel
import id.co.app.nucocore.domain.entities.view.PokeCardModel
import id.co.app.nucocore.domain.entities.view.PokeHeaderModel
import id.co.app.nucocore.domain.repository.MainRepository
import id.co.app.nucocore.extension.onFailure
import id.co.app.nucocore.extension.onLoading
import id.co.app.nucocore.extension.onSuccess
import id.co.app.nucocore.extension.toDp
import id.co.app.nucocore.singleton.LOG_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val mainRepository: MainRepository) : ViewModel() {

  private val _contentList = ArrayList<DelegateAdapterItem>()
  private val _contentData = MutableLiveData<MutableList<DelegateAdapterItem>>()
  val contentData: LiveData<MutableList<DelegateAdapterItem>> get() = _contentData

  private val _loading = MutableLiveData(false)
  val loading: LiveData<Boolean> get() = _loading

  private val _pokemonList = MutableLiveData<ArrayList<Pokemon>>()
  val pokemonList: LiveData<ArrayList<Pokemon>> get() = _pokemonList

  private var page: Int = 1
  var topSpace: Int = 180.toDp()

  // --
  fun refresh() {
    page = 1
    loadContent()
  }

  fun loadContent() {
    val limit = 5
    val mPage = if (page < 1) 1 else page
    val offset = limit * (mPage - 1)
    viewModelScope.launch(Dispatchers.IO) {
      mainRepository.getPokemonList(offset, limit).collect { result ->
        result.onLoading {
          _loading.postValue(true)
        }
        result.onFailure {
          _loading.postValue(false)
        }
        result.onSuccess {
          mapPokemonList(mPage, it)
          page += 1
          _loading.postValue(false)
        }
      }
    }
  }

  private fun mapPokemonList(page: Int, model: PokemonLoadResult) {
    Log.e("PKMN", "LOAD POKEMON $page")
    if (page == 1) {
      _contentList.clear()
      _contentList.add(SpaceModel(topSpace))
      _contentList.add(PokeHeaderModel(model.count))
    }
    model.results.forEach {
      _contentList.add(PokeCardModel(it.id, it.name, it.types.map { e -> e.type.name }))
    }
    _contentData.postValue(_contentList.toMutableList())
  }
}