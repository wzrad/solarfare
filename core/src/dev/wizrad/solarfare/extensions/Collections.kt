package dev.wizrad.solarfare.extensions

inline fun <E, O> Iterable<out E>.findMapped(transform: (E) -> O): O? {
  for(element in this) {
    val result = transform(element)
    if(result != null) {
      return result
    }
  }

  return null
}

inline fun <E, O> Array<out E>.findMapped(transform: (E) -> O): O? {
  for(element in this) {
    val result = transform(element)
    if(result != null) {
      return result
    }
  }

  return null
}
