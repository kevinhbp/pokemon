package id.co.app.home.viewModels

import android.util.Log
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
import id.co.app.nucocore.singleton.LOG_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(private val mainRepository: MainRepository) : ViewModel() {

  private val _contentData = MutableLiveData<MutableList<DelegateAdapterItem>>()
  val contentData: LiveData<MutableList<DelegateAdapterItem>> get() = _contentData

  private val _loading = MutableLiveData(false)
  val loading: LiveData<Boolean> get() = _loading

  fun setTopSpace(topSpace: Int) {
    mainRepository.topSpace = topSpace
  }

  // --
  fun resetPage() {
    mainRepository.refreshPokemonList()
  }

  fun loadContent() {
    /* viewModelScope.launch(Dispatchers.IO) {
      mainRepository.getPokemonList().collect { result ->
        result.onLoading {
          _loading.postValue(true)
        }
        result.onFailure {
          _loading.postValue(false)
        }
        result.onSuccess {
          _loading.postValue(false)
          _contentData.postValue(it.toMutableList())
        }
      }
    } */

    mainRepository.getPokemonListBetter()
      .onEach { result ->
        Log.d(LOG_TAG, "onEach getPokemonList()")
        result.onLoading {
          _loading.postValue(true)
        }
        result.onFailure { e ->
          _loading.postValue(false)
          Log.e(LOG_TAG, e?.localizedMessage.orEmpty())
        }
        result.onSuccess {
          _loading.postValue(false)
          _contentData.postValue(it.toMutableList())
        }
      }
      .catch { e ->
        Log.e(LOG_TAG, e.localizedMessage.orEmpty())
      }
      .flowOn(Dispatchers.IO)
      .launchIn(viewModelScope)
  }
}