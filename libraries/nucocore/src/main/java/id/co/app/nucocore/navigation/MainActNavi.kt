package id.co.app.nucocore.navigation

import id.co.app.nucocore.domain.entities.row.BlockerDefaultModel

interface MainActNavi {
  fun navigateSplashToHome()
  fun doSplashCheck()
  fun showActionBar(show: Boolean = true)
  fun showBlockerView(model: BlockerDefaultModel)
}