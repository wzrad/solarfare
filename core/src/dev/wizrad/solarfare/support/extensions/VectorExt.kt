package dev.wizrad.solarfare.support.extensions

import com.badlogic.gdx.math.Vector2

fun Vector2(width: Int, height: Int): Vector2 {
  return Vector2(width.toFloat(), height.toFloat())
}

fun Vector2.minv(): Vector2 {
  x = 1.0f / x
  y = 1.0f / y
  return this
}

fun Vector2.mabs(): Vector2 {
  x = Math.abs(x)
  y = Math.abs(y)
  return this
}

fun Vector2.mreflect(x: Float? = null, y: Float? = null): Vector2 {
  if(x != null) {
    this.x = x - this.x
  }

  if(y != null) {
    this.y = y - this.y
  }

  return this
}
