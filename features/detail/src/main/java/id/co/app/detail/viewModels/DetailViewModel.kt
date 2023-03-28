package id.co.app.detail.viewModels

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
import id.co.app.nucocore.domain.entities.pokemon.PokemonEvolution
import id.co.app.nucocore.domain.entities.row.SpaceModel
import id.co.app.nucocore.domain.entities.view.ActionButtonModel
import id.co.app.nucocore.domain.entities.view.PokeInfo1Model
import id.co.app.nucocore.domain.entities.view.PokeInfo2Model
import id.co.app.nucocore.domain.entities.view.PokeInfo3Model
import id.co.app.nucocore.domain.repository.MainRepository
import id.co.app.nucocore.extension.onFailure
import id.co.app.nucocore.extension.onSuccess
import id.co.app.nucocore.extension.toDp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailViewModel(
  private val userSession: UserSession,
  private val mainRepository: MainRepository,
) : ViewModel() {

  private val _contentData = MutableLiveData<MutableList<DelegateAdapterItem>>()
  val contentData: LiveData<MutableList<DelegateAdapterItem>> get() = _contentData

  private var getStringResource: ((id: Int) -> String)? = null
  private var getDimenResource: ((id: Int) -> Int)? = null

  fun start(stringCallback: (id: Int) -> String, dimenCallback: (id: Int) -> Int) {
    getStringResource = stringCallback
    getDimenResource = dimenCallback
    val pokemon = DataSingleton.getDefaultInstance().selectedPokemon
    generateContent(pokemon)
  }

  private fun generateContent(pokemon: Pokemon?) {
    if (pokemon == null) {
      loadEmptyData()
      return
    }
    loadPokemon(pokemon)
  }

  private fun loadEmptyData() {
    val content = ArrayList<DelegateAdapterItem>()
    val errCode = "pokemon-empty"
    val illustration = drawable.illustration_data_sync_required
    val title =
      getStringResource?.invoke(string.pokemon_not_selected_title) ?: "Pokemon is not selected"
    val message = getStringResource?.invoke(string.pokemon_not_selected_message)
      ?: "Select pokemon first to show this page"
    val buttonLabel = getStringResource?.invoke(string.dismiss) ?: "Dismiss"
    val button = ActionButtonModel(1, buttonLabel, InternalDeepLink.EXIT)
    content.add(DefaultEmptyStateModel(errCode, illustration, title, message, button))
    _contentData.postValue(content)
  }

  private fun loadPokemon(data: Pokemon) {
    val content = ArrayList<DelegateAdapterItem>()

    content.add(SpaceModel(getDimenResource?.invoke(dimen.action_bar_height) ?: 50.toDp()))
    content.add(SpaceModel(getDimenResource?.invoke(dimen.space_x10) ?: 40.toDp()))
    content.add(SpaceModel(getDimenResource?.invoke(dimen.space_x4) ?: 40.toDp()))

    content.add(PokeInfo1Model.fromPokemon(data))
    content.add(PokeInfo2Model.fromPokemon(data))

    /*if (evolutionData == null) {
      content.add(LoadingModel(LoadingModel.Type.HORIZONTAL_SHIMMER))
    } else if (evolutionData!!.isNotEmpty()) {
      content.add(PokeInfo3Model(data.id, evolutionData!!.toList()))
    }*/

    _contentData.postValue(content)

  }


}