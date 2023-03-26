package id.co.app.nucocore.base

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import id.co.app.nucocore.extension.showToastError
import id.co.app.nucocore.extension.withDefault
import id.co.app.nucocore.singleton.LOG_TAG
import id.co.app.nucocore.singleton.ViewSingleton
import id.co.app.nucocore.utilities.Common

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {
  protected val binding: T by lazy { provideBinding() }

  @Suppress("MemberVisibilityCanBePrivate")
  protected var isToolbarShown = false
  private val isToolbarShownKey = "isToolbarShown"

  protected var loadDataCalled = false

  // --
  open fun onCompleteDraw() {
    observeViewModel()
    loadDataCalled = true
    loadData()
  }

  open fun onBeforeDrawComplete() {
    setupView()
  }

  // --
  abstract fun loadData()

  abstract fun setupView()

  abstract fun observeViewModel()

  abstract fun provideBinding(): T

  // --
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
    isToolbarShown = savedInstanceState?.getBoolean(isToolbarShownKey) ?: false
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    onBeforeDrawComplete()
    setupLayoutObserver(binding.root)
  }

  @Override
  override fun onSaveInstanceState(savedInstanceState: Bundle) {
    savedInstanceState.putBoolean(isToolbarShownKey, isToolbarShown)
    super.onSaveInstanceState(savedInstanceState)
  }

  @Suppress("unused")
  protected fun navigateTo(target: NavDirections) {
    try {
      findNavController().navigate(target)
    } catch (e: Exception) {
      Log.d(LOG_TAG, e.message.withDefault())
      requireActivity().showToastError("Destination is not found.", Toast.LENGTH_LONG)
    }
  }

  @Suppress("unused")
  protected fun navigateTo(mUri: Uri) {
    try {
      findNavController().navigate(mUri, ViewSingleton.getDefaultInstance().navOptions)
    } catch (e: Exception) {
      Log.d(LOG_TAG, e.message.withDefault())
      requireActivity().showToastError("Destination is not found.", Toast.LENGTH_LONG)
    }
  }

  // --
  private fun setupLayoutObserver(root: View) {
    root.viewTreeObserver
      .addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
          root.viewTreeObserver.removeOnGlobalLayoutListener(this)
          onCompleteDraw()
        }
      })
  }

  @Suppress("unused")
  protected fun setStatusColor(light: Boolean) {
    isToolbarShown = light
    checkToolbarStatus()
  }

  open fun checkToolbarStatus() {
    if (isToolbarShown) {
      if (!activity?.let { Common.isDarkModeOn(it) }!!) {
        Common.setStatusColorDark(requireActivity())
      }
    } else {
      Common.setStatusColorLight(requireActivity())
    }
  }
}