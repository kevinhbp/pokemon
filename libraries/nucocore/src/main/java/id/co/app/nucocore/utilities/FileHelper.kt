package id.co.app.nucocore.utilities

import android.content.Context
import android.util.Log
import id.co.app.nucocore.singleton.LOG_TAG
import java.nio.charset.Charset

object FileHelper {

  fun Context.getJsonFromAssets(filename: String): String {
    var jsonString = ""
    if (filename.isEmpty()) return jsonString
    try {
      val inputStream = this.assets.open(filename)
      val size = inputStream.available()
      val buffer = ByteArray(size)
      inputStream.read(buffer)
      inputStream.close()
      jsonString = String(buffer, Charset.forName("UTF-8"))
    } catch (e: Exception) {
      Log.e(LOG_TAG, e.localizedMessage ?: "")
    }
    return jsonString
  }
}