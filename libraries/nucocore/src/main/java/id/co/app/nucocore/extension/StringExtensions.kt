package id.co.app.nucocore.extension

fun String?.withDefault(default: String = ""): String {
  if (this == null) return default
  if (this.isEmpty()) return default
  return this
}

fun String.makeNullIfEmpty(): String? {
  if (this.isEmpty()) return null
  return this
}

fun String?.makeEmptyIfNull(): String {
  if (this == null) return ""
  return this
}