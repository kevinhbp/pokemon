package id.co.app.nucocore.domain.entities.view

import id.co.app.nucocore.base.BaseModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import java.io.Serializable

data class ActionButtonModel(val modelId: Any, val label: String, val deepLink: String) : Serializable, DelegateAdapterItem, BaseModel() {

  override val id: Any
    get() = modelId

  override fun equals(other: BaseModel): Boolean {
    return other is ActionButtonModel && other.modelId == modelId
  }

  override fun content(): Any {
    return label
  }

  override fun id(): Any {
    return modelId
  }
}