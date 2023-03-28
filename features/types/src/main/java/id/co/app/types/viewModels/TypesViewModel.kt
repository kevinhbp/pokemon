package id.co.app.types.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.domain.entities.pokemon.Pokemon
import id.co.app.nucocore.domain.entities.row.DefaultEmptyStateModel
import id.co.app.nucocore.singleton.DataSingleton
import id.co.app.session.UserSession
import id.co.app.nucocore.R.drawable
import id.co.app.nucocore.R.dimen
import id.co.app.nucocore.R.string
import id.co.app.nucocore.base.adapterdelegate.adapter.model.LoadingModel
import id.co.app.nucocore.deeplink.InternalDeepLink
import id.co.app.nucocore.domain.entities.pokemon.PokeResult
import id.co.app.nucocore.domain.entities.pokemon.PokemonEvolution
import id.co.app.nucocore.domain.entities.pokemon.PokemonLoadResult
import id.co.app.nucocore.domain.entities.row.SpaceModel
import id.co.app.nucocore.domain.entities.view.*
import id.co.app.nucocore.domain.repository.MainRepository
import id.co.app.nucocore.extension.onFailure
import id.co.app.nucocore.extension.onLoading
import id.co.app.nucocore.extension.onSuccess
import id.co.app.nucocore.extension.toDp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TypesViewModel(
  private val userSession: UserSession,
  private val mainRepository: MainRepository,
) : ViewModel() {

  private val _contentData = MutableLiveData<MutableList<DelegateAdapterItem>>()
  val contentData: LiveData<MutableList<DelegateAdapterItem>> get() = _contentData

  private val _loading = MutableLiveData(false)
  val loading: LiveData<Boolean> get() = _loading

  private var mSelectedType = "normal"
  private val _selectedType = MutableLiveData(mSelectedType)
  val selectedType: LiveData<String> get() = _selectedType

  private var getStringResource: ((id: Int) -> String)? = null
  private var getDimenResource: ((id: Int) -> Int)? = null

  private val typeData: ArrayList<PokeResult> = arrayListOf()

  private var loadResult: PokemonLoadResult? = null

  fun start(stringCallback: (id: Int) -> String, dimenCallback: (id: Int) -> Int) {
    getStringResource = stringCallback
    getDimenResource = dimenCallback

    setupView()
    fetchTypeData()
    // fetchPokemonByType()
  }

  fun setSelectedType(newType: String) {
    mSelectedType = newType
    _selectedType.postValue(newType)
    loadResult = null
    // fetchPokemonByType()
    setupView()
  }

  private fun setupView() {
    if (typeData.isEmpty()) {
      // TODO SHOW EMPTY LIST
      return
    }
    val content = ArrayList<DelegateAdapterItem>()

    val topSpace = getDimenResource?.invoke(dimen.action_bar_height) ?: 80.toDp()
    content.add(SpaceModel(topSpace))

    val typeList = typeData.map { e -> e.name }
    content.add(PokeTypeButtonModel(1, typeList))

    content.add(PokeTypeHeaderModel(mSelectedType))

    loadResult?.results?.forEach {
      content.add(PokeCardModel.fromPokemon(it))
    }

    _contentData.postValue(content)
  }

  private fun fetchTypeData() {
    viewModelScope.launch(Dispatchers.IO) {
      mainRepository.getPokemonTypeList().collect { result ->
        result.onLoading {
          _loading.postValue(true)
        }
        result.onFailure {
          _loading.postValue(false)
        }
        result.onSuccess {
          _loading.postValue(false)
          typeData.clear()
          typeData.addAll(it)
          setupView()
        }
      }
    }
  }

  private fun fetchPokemonByType() {
    viewModelScope.launch(Dispatchers.IO) {
      mainRepository.getPokemonListWithTypeId(mSelectedType).collect { result ->
        result.onLoading {
          _loading.postValue(true)
        }
        result.onFailure {
          _loading.postValue(false)
        }
        result.onSuccess {
          _loading.postValue(false)

          setupView()
        }
      }
    }
  }

}