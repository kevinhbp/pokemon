package id.co.app.nucocore.deeplink

object InternalDeepLink {
  private const val HOST = "nuco://"

  const val SPLASH = HOST + "splash"
  const val HOME = HOST + "home"
  const val DETAIL = HOST + "detail"
  const val TYPES = HOST + "types"

  const val RE_CONNECT = HOST + "reconnect"
  const val EXIT = HOST + "exit"
}