object ModuleDependencies {
	object Libraries {
		private const val LIBRARY = ":libraries"
		const val CORE = "$LIBRARY:nucocore"
		const val SESSION = "$LIBRARY:session"
	}

	object Features {
		private const val FEATURES = ":features"


		const val HOME = "$FEATURES:home"
		const val DETAIL = "$FEATURES:detail"
		const val TYPES = "$FEATURES:types"
	}

	val features = arrayListOf<String>().apply {
		add(Features.HOME)
		add(Features.DETAIL)
		add(Features.TYPES)
	}
	val libraries = arrayListOf<String>().apply {
		add(Libraries.CORE)
	}
	val session = arrayListOf<String>().apply {
		add(Libraries.SESSION)
	}
}