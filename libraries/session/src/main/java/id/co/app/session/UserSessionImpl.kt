package id.co.app.session

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.squareup.moshi.Moshi
import id.co.app.nucocore.domain.entities.master.User
import id.co.app.session.security.AESUtils
import kotlinx.coroutines.flow.map

class UserSessionImpl(
  private val context: Context,
  private val moshi: Moshi
) : UserSession {
  private val adapter by lazy { moshi.adapter(User::class.java) }
  private val Context.dataStore by preferencesDataStore("app_preferences")

  override suspend fun saveUser(loginResponse: User) {
    context.dataStore.edit { prefs -> prefs[KEY_USER] = AESUtils.encrypt(adapter.toJson(loginResponse)) }
  }

  override suspend fun deleteUser() {
    context.dataStore.edit { prefs -> prefs.remove(KEY_USER) }
  }

  @Suppress("BlockingMethodInNonBlockingContext")
  override fun getUser() = context.dataStore.data.map {
    val userString = it[KEY_USER] ?: return@map null
    adapter.fromJson(AESUtils.decrypt(userString))
  }

  override fun getToken() = getUser().map { "Bearer " + it?.token }

  companion object {
    private const val KEY_USER_STRING = "key_user"
    val KEY_USER = stringPreferencesKey(AESUtils.encrypt(KEY_USER_STRING))
  }
}