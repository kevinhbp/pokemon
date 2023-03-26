package id.co.app.nucocore.domain.entities.row

import id.co.app.nucocore.base.BaseModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.R
import java.io.Serializable

data class DefaultMenuItem(val mId: Int, val title: String, val target: String)
  : Serializable, DelegateAdapterItem, BaseModel() {
  var startDrawable: Int? = null
  var endDrawable: Int? = null

  override val id: Any get() = mId

  override fun equals(other: BaseModel): Boolean = (other is DefaultMenuItem && other.mId == mId && other.title == title)

  override fun content(): Any = "Menu with id $mId and title $title"

  override fun id(): Any = mId

  fun isGoneStartDrawable(): Boolean = (startDrawable == null)

  fun isGoneEndDrawable(): Boolean = (endDrawable == null)

  fun withEndArrow(): DefaultMenuItem = this.apply { endDrawable = R.drawable.ic_arrow_next }
}