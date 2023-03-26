object ModuleDependencies {
	object Libraries {
		private const val LIBRARY = ":libraries"
		const val CORE = "$LIBRARY:nucocore"
		const val SESSION = "$LIBRARY:session"
	}

	object Features {
		private const val FEATURES = ":features"


		const val HOME = "$FEATURES:home"
		const val PROFILE = "$FEATURES:profile"
	}

	val features = arrayListOf<String>().apply {
		add(Features.HOME)
		add(Features.PROFILE)
	}
	val libraries = arrayListOf<String>().apply {
		add(Libraries.CORE)
	}
	val session = arrayListOf<String>().apply {
		add(Libraries.SESSION)
	}
}