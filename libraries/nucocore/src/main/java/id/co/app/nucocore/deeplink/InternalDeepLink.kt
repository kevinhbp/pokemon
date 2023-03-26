package id.co.app.nucocore.deeplink

object InternalDeepLink {
  private const val HOST = "nuco://"
  const val HOME_DETAIL = HOST + "home-detail?detailsName=%s"

  const val HOME = HOST + "home"
  const val PND = HOST + "pnd"
  const val NA = HOST + "na"
  const val SO = HOST + "so"
  const val DP = HOST + "dp"
  const val PROFILE = HOST + "profile"

  const val DATA_SYNC = HOST + "data-sync?autoSync=%s"
  const val NOTIFICATIONS = HOST + "notifications"
  const val SETTINGS = HOST + "settings"

  const val PRIVACY_POLICY = HOST + "privacy-policy"
  const val TERMS_AND_CONDITIONS = HOST + "terms-and-conditions"

  const val RE_CONNECT = HOST + "reconnect"
  const val EXIT = HOST + "exit"
  const val LOGOUT = HOST + "logout"
  const val CREATE_ASSESSMENT = HOST + "create-assessment"
  const val LOAD_DEMO = HOST + "load-demo"
}