package id.co.app.home.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.domain.entities.pokemon.PokeResult
import id.co.app.nucocore.domain.entities.pokemon.PokemonList
import id.co.app.nucocore.domain.entities.row.SpaceModel
import id.co.app.nucocore.domain.entities.view.PokeCardModel
import id.co.app.nucocore.domain.entities.view.PokeHeaderModel
import id.co.app.nucocore.domain.repository.MainRepository
import id.co.app.nucocore.extension.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val mainRepository: MainRepository) : ViewModel() {

  private val _contentData = MutableLiveData<MutableList<DelegateAdapterItem>>()
  val contentData: LiveData<MutableList<DelegateAdapterItem>> get() = _contentData

  private val _loading = MutableLiveData(false)
  val loading: LiveData<Boolean> get() = _loading

  var topSpace : Int = 80.toDp()

  // --
  fun loadContent() {
    viewModelScope.launch(Dispatchers.IO) {
      mainRepository.getPokemonList().collect { result ->
        result.onLoading {
          _loading.postValue(true)
        }
        result.onFailure {
          _loading.postValue(false)
        }
        result.onSuccess {
          _loading.postValue(false)
          val firstPage = mainRepository.getCurrentPage() == 1
          val newList = mapPage(it, firstPage)
          _contentData.postValue(newList.toMutableList())
        }
      }
    }
  }


  // --
  private fun mapPage(response: PokemonList, firstPage: Boolean): List<DelegateAdapterItem> {
    val result = ArrayList<DelegateAdapterItem>()
    if (firstPage) {
      result.add(SpaceModel(topSpace))
      result.add(createHeader(response.count))
    }
    response.results.forEach { poke ->
      result.add(createPokeCard(poke))
    }
    return result
  }

  private fun createHeader(count: Int): PokeHeaderModel = PokeHeaderModel(count)

  private fun createPokeCard(model: PokeResult): PokeCardModel = PokeCardModel(0, model.name, listOf())
}