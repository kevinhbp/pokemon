@file:Suppress("unused")

package id.co.app.nucocore.extension

import androidx.databinding.Observable

fun <T : Observable> T.addOnPropertyChanged(callback: (T) -> Unit) =
  addOnPropertyChangedCallback(
    object : Observable.OnPropertyChangedCallback() {
      @Suppress("UNCHECKED_CAST")
      override fun onPropertyChanged(
        observable: Observable?, i: Int
      ) = callback(observable as T)
    })