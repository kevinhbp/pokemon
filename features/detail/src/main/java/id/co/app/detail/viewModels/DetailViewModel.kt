package id.co.app.detail.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.session.UserSession

class DetailViewModel(
  private val userSession: UserSession,
) : ViewModel() {

  private val _contentData = MutableLiveData<MutableList<DelegateAdapterItem>>()
  val contentLiveData: LiveData<MutableList<DelegateAdapterItem>> get() = _contentData

  fun loadData() {

  }
}