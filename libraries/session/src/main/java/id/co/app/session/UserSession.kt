package id.co.app.session

import id.co.app.nucocore.domain.entities.master.User
import kotlinx.coroutines.flow.Flow

interface UserSession {
	suspend fun saveUser(loginResponse: User)
	suspend fun deleteUser()
	fun getUser(): Flow<User?>
	fun getToken(): Flow<String>
}