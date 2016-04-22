package dev.wizrad.solarfare.game.world

import dev.wizrad.solarfare.game.world.core.Updatable
import dev.wizrad.solarfare.generation.SpaceNode
import dev.wizrad.solarfare.generation.core.Root
import javax.inject.Inject

class World @Inject constructor(
  root: Root<SpaceNode>): Updatable {

  val space: Space

  init {
    space = Space(root.bootstrap())
  }

  override fun update(delta: Float) {
    space.update(delta)
  }
}
