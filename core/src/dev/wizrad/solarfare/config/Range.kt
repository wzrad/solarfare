package dev.wizrad.solarfare.config

import dev.wizrad.solarfare.support.extensions.between
import dev.wizrad.solarfare.support.extensions.rand

class Range {
  var min: Double = 0.0
  var max: Double = 0.0

  //
  // Sampling
  fun sample(): Double {
    return rand().between(min, max)
  }
}
