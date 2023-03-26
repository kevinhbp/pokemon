package id.co.app.nucocore.domain.interceptor

import id.co.app.nucocore.domain.entities.master.Header
import id.co.app.nucocore.singleton.HeaderSingleton
import okhttp3.Interceptor
import okhttp3.Response

class HttpRequestInterceptor : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val originalRequest = chain.request()
    val originalMethod = originalRequest.method
    val originalBody = originalRequest.body
    val mBuilder = originalRequest.newBuilder()

    val header: Header? = HeaderSingleton.getDefaultInstance().header
    if (header != null) {
      mBuilder
        .header("Authorization", header.token)
        .header("App-Name", header.appName)
        .header("App-Version-Name", header.appVersionName)
        .header("App-Version-Code", header.appVersionCode)
        .header("Device-Id", header.deviceId)
    }

    val newRequest = mBuilder.method(originalMethod, originalBody).build()
    return chain.proceed(newRequest)
  }
}