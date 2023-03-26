package id.co.app.nucocore.components.blocker

import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.app.nucocore.R
import id.co.app.nucocore.domain.entities.row.BlockerDefaultModel

@Suppress("UNUSED_PARAMETER")
class BlockerViewModel : ViewModel() {

  // --
  val imageIllustration = ObservableInt(R.drawable.illustration_connection_failure)

  val textTitle = ObservableField("")

  val textMessage = ObservableField("")

  val textButtonPrimary = ObservableField("")

  val textButtonSecondary = ObservableField("")

  val flagGoneImageIllustration = ObservableBoolean(false)

  val flagGoneButtonPrimary = ObservableBoolean(false)

  val flagGoneButtonSecondary = ObservableBoolean(false)

  val flagGoneLayout = ObservableBoolean(true)

  private var targetPrimary: String = ""

  private var targetSecondary: String = ""

  // --
  private val _target = MutableLiveData("")
  val targetLiveData: LiveData<String> get() = _target

  // --
  fun showWithModel(model: BlockerDefaultModel) {
    imageIllustration.set(model.imageResId)

    textTitle.set(model.textTitle)

    textMessage.set(model.textMessage)

    textButtonPrimary.set(model.buttonPrimary?.label.orEmpty())

    textButtonSecondary.set(model.buttonSecondary?.label.orEmpty())

    flagGoneImageIllustration.set(model.imageResId < 1)

    flagGoneButtonPrimary.set(model.buttonPrimary?.label.isNullOrEmpty())

    flagGoneButtonSecondary.set(model.buttonSecondary?.label.isNullOrEmpty())

    targetPrimary = model.buttonPrimary?.deepLink.orEmpty()

    targetSecondary = model.buttonSecondary?.deepLink.orEmpty()

    flagGoneLayout.set(model.textTitle.isEmpty())
  }

  fun onClickButtonPrimary(view: View?) {
    _target.value = targetPrimary
    flagGoneLayout.set(true)
  }

  fun onClickButtonSecondary(view: View?) {
    _target.value = targetSecondary
    flagGoneLayout.set(true)
  }

}