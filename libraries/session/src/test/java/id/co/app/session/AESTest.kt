package com.forestry.plantation.usersession

import com.forestry.plantation.warehouse.usersession.security.AESUtils
import org.junit.Test
class AESTest {
	@Test
	fun `test encrypt and decrypt key`(){
		val key = "knowledge town"
		var encrypt = AESUtils.encrypt(key)
		var decrypt = AESUtils.decrypt(encrypt)

		assert(encrypt != key)
		assert(decrypt == key)

		encrypt = AESUtils.encrypt(key)
		decrypt = AESUtils.decrypt(encrypt)

		assert(encrypt != key)
		assert(decrypt == key)
	}
}