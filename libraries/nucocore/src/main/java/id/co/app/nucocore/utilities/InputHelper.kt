package id.co.app.nucocore.utilities

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import id.co.app.nucocore.singleton.LOG_TAG
import kotlinx.coroutines.*
import java.lang.Runnable

typealias InputHelperDeclaration = InputHelper.() -> Unit

@Suppress("unused")
fun startInputHelper(declaration: InputHelperDeclaration): InputHelper {
  val default = InputHelper()
  declaration(default)
  return default
}

class InputHelper : DefaultLifecycleObserver {

  private val scope: CoroutineScope by lazy { MainScope() }
  private var job: Job? = null
  private var delayInMs = 500L
  private var lifecycleRegistered = false
  private var editText: EditText? = null
  private var flagFocusListenerInstalled = false

  private var focusListener: ((hasFocus: Boolean) -> Unit)? = null
  private var onNoDelayChangeTextListener: ((text: String) -> Unit)? = null
  private var onChangeTextListener: ((text: String) -> Unit)? = null
  private val onActionButtonPressed: (actionId: Int) -> Boolean = {
    if (it == EditorInfo.IME_ACTION_SEARCH) {
      editText?.hideKeyboard()
      editText?.clearFocus()
      job?.cancel()
      onSearchActionPressed?.invoke(getText())
    }
    if (it == EditorInfo.IME_ACTION_DONE) {
      editText?.hideKeyboard()
      editText?.clearFocus()
      job?.cancel()
      onDoneActionPressed?.invoke(getText())
    }

    (it == EditorInfo.IME_ACTION_SEARCH || it == EditorInfo.IME_ACTION_DONE)
  }
  private var onSearchActionPressed: ((text: String) -> Unit)? = null
  private var onDoneActionPressed: ((text: String) -> Unit)? = null

  // --
  private var mFormattedText: String = ""
  private var inputMaskFormatter: ((text: String) -> String?)? = null

  private fun registerLifecycle(lifecycle: Lifecycle) {
    if (lifecycleRegistered) return
    lifecycleRegistered = true
    lifecycle.addObserver(this)
  }

  private fun registerEditText(editText: EditText) {
    if (!lifecycleRegistered) return
    this.editText = editText
    editText.setOnEditorActionListener(editorActionListener)
  }

  private fun setDelay(delayInMs: Long) {
    var mDelay = if (delayInMs > 1000) 1000 else delayInMs
    mDelay = if (mDelay < 0) 0 else mDelay
    this.delayInMs = mDelay
  }

  @Suppress("unused")
  fun startListenInput(
    lifecycle: Lifecycle,
    editText: EditText,
    delayInMs: Long = 500L,
    listener: (text: String) -> Unit
  ) {
    registerLifecycle(lifecycle)
    registerEditText(editText)
    setDelay(delayInMs)
    listenFocus()
    onChangeTextListener = listener
  }

  @Suppress("UNUSED")
  fun doOnFocusChange(listener: (hasFocus: Boolean) -> Unit) {
    listenFocus()
    focusListener = listener
  }

  @Suppress("UNUSED")
  fun doOnSearchActionPressed(listener: (text: String) -> Unit) {
    onSearchActionPressed = listener
  }

  @Suppress("UNUSED")
  fun doOnDoneActionPressed(listener: (text: String) -> Unit) {
    onDoneActionPressed = listener
  }

  @Suppress("UNUSED")
  fun doOnChanged(listener: (text: String) -> Unit) {
    onNoDelayChangeTextListener = listener
  }

  @Suppress("UNUSED")
  fun setupInputMask(onChange: (text: String) -> String?) {
    inputMaskFormatter = onChange
  }

  private fun listenFocus() {
    if (flagFocusListenerInstalled) return
    flagFocusListenerInstalled = true
    if (!lifecycleRegistered) return
    val mEditText = editText ?: return
    mEditText.setOnFocusChangeListener { _, hasFocus ->
      if (hasFocus) onFocusStartListenInput() else onBlurEndListenInput()
      focusListener?.invoke(hasFocus)
    }
  }

  private fun onFocusStartListenInput() {
    start()
  }

  private fun onBlurEndListenInput() {
    if (!lifecycleRegistered) return
    val mEditText = editText ?: return
    mEditText.postDelayed(doEndListen, delayInMs + 50)
  }

  private val doEndListen = Runnable {
    end()
  }

  // --
  private fun start() {
    end()
    val mEditText = editText ?: return
    mEditText.addTextChangedListener(textWatcher)
  }

  private fun end() {
    val mEditText = editText ?: return
    mEditText.removeTextChangedListener(textWatcher)
  }

  private fun getText(): String {
    val mEditText = editText ?: return ""
    return mEditText.text.toString()
  }

  private val textWatcher = object : TextWatcher {
    override fun afterTextChanged(editable: Editable?) {
      harvestText()
      doFormatMaskText()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
      mFormattedText = s.toString()
    }

    override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }
  }

  private val editorActionListener =
    TextView.OnEditorActionListener { _, actionId, _ ->
      onActionButtonPressed.invoke(actionId)
    }

  private fun harvestText() {
    val cText = getText()
    val mText = mFormattedText
    if (cText == mText) {
      Log.d(LOG_TAG, "Do harvest text - cancel due to same text..")
      return
    }
    job?.cancel()
    onNoDelayChangeTextListener?.invoke(getText())
    if (delayInMs < 10) {
      onChangeTextListener?.invoke(getText())
      return
    }
    job = scope.launch {
      delay(delayInMs)
      onChangeTextListener?.invoke(getText())
    }
  }

  private fun doFormatMaskText() {
    val mEditText = editText ?: return
    val formatter = inputMaskFormatter ?: return
    mFormattedText = formatter.invoke(getText()) ?: return
    if (mFormattedText == getText()) return
    Log.d(LOG_TAG, "Mask Text Format -> before: ${getText()} after: $mFormattedText --> (length: ${mFormattedText.length})")
    end()
    job?.cancel()
    mEditText.setText(mFormattedText)
    try {
      job = scope.launch {
        delay(5)
        onChangeTextListener?.invoke(mFormattedText)
        mEditText.setSelection(mFormattedText.length)
      }
    } catch (e: Exception) {
      Log.e(LOG_TAG, "format masking error: ${e.message}")
      e.printStackTrace()
    }
    start()
  }
}

@Suppress("UNUSED")
fun EditText.hideKeyboard() {
  val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
  imm.hideSoftInputFromWindow(windowToken, 0)
}

@Suppress("UNUSED")
fun EditText.openKeyboard() {
  this.requestFocus()
  val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
  imm.showSoftInput(this, 0)
}