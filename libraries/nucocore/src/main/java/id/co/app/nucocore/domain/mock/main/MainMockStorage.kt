package id.co.app.nucocore.domain.mock.main

import android.content.Context
import com.google.gson.Gson
import id.co.app.nucocore.utilities.FileHelper.getJsonFromAssets

class MainMockStorage(val context: Context) {

  private val gson: Gson by lazy { Gson() }

  private fun getResponseFile(fileName: String): String = context.getJsonFromAssets(fileName)


}