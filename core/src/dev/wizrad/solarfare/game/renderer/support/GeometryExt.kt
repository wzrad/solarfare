package dev.wizrad.solarfare.game.renderer.support

import com.badlogic.gdx.math.Vector2
import dev.wizrad.solarfare.support.geometry.Point
import dev.wizrad.solarfare.support.geometry.Size

// MARK: Vector2
fun Vector2.set(size: Size): Vector2 {
  return set(size.width.toFloat(), size.height.toFloat())
}

fun Vector2.set(point: Point): Vector2 {
  return set(point.x.toFloat(), point.y.toFloat())
}
