package id.co.app.home.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.domain.entities.row.SpaceModel
import id.co.app.nucocore.domain.repository.MainRepository
import id.co.app.nucocore.extension.toPx
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(mainRepository: MainRepository) : ViewModel() {

  private val _contentData = MutableLiveData<MutableList<DelegateAdapterItem>>()
  val contentData: LiveData<MutableList<DelegateAdapterItem>> get() = _contentData

  private val _loading = MutableLiveData(false)
  val loading: LiveData<Boolean> get() = _loading

  // --
  fun loadContent() {
    viewModelScope.launch(Dispatchers.IO) {
      // Show Loading
      _loading.postValue(true)

      // Hide Loading
      delay(400)
      _loading.postValue(false)
    }
  }

  // --
  private fun refreshContent() {
    val contentList = ArrayList<DelegateAdapterItem>()
    contentList.apply {
      add(SpaceModel(120.toPx()))
      add(SpaceModel(90.toPx()))
    }
    _contentData.postValue(contentList)
  }


}