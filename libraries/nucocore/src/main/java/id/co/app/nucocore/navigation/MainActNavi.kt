package id.co.app.nucocore.navigation

import id.co.app.nucocore.domain.entities.row.BlockerDefaultModel

interface MainActNavi {
  fun navigateSplashToLogin()
  fun navigateSplashToHome()
  fun navigateLoginToHome()
  fun navigateProfileToSplash()

  fun doSplashCheck()
  fun showBottomNavigation(show: Boolean = true)
  fun doCheckDataSync()
  fun showBlockerView(model: BlockerDefaultModel)
}