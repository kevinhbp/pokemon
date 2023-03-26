package id.co.app.nucocore.domain.entities.row

import androidx.annotation.DrawableRes
import id.co.app.nucocore.base.BaseModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.domain.entities.view.ActionButtonModel
import java.io.Serializable

@Suppress("MemberVisibilityCanBePrivate")
data class DefaultEmptyStateModel(
  val errCode: Any,
  @DrawableRes val illustrationResId: Int,
  val textTitle: String,
  val textMessage: String,
  val buttonPrimary: ActionButtonModel? = null,
  val buttonSecondary: ActionButtonModel? = null,
) : Serializable, DelegateAdapterItem, BaseModel() {

  fun getLabelButtonPrimary() = buttonPrimary?.label ?: ""

  fun isGoneButtonPrimary() = getLabelButtonPrimary().isEmpty()

  fun getLabelButtonSecondary() = buttonSecondary?.label ?: ""

  fun isGoneButtonSecondary() = getLabelButtonSecondary().isEmpty()

  fun isGoneIllustration() = illustrationResId == 0

  //--
  override val id: Any
    get() = "empty"

  override fun equals(other: BaseModel): Boolean {
    return other is DefaultEmptyStateModel
  }

  override fun content(): Any {
    return errCode
  }

  override fun id(): Any {
    return "empty"
  }
}