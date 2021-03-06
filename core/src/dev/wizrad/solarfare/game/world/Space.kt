package dev.wizrad.solarfare.game.world

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import dev.wizrad.solarfare.game.renderer.support.set
import dev.wizrad.solarfare.game.world.core.NodeEntity
import dev.wizrad.solarfare.game.world.support.EntitySequence
import dev.wizrad.solarfare.generation.SpaceNode
import dev.wizrad.solarfare.support.Tag
import dev.wizrad.solarfare.support.debug

class Space(
  node:  SpaceNode,
  world: World): NodeEntity<SpaceNode>(node, null, world) {

  // MARK: Children
  lateinit var ship:    Ship
  lateinit var systems: List<SolarSystem>

  // MARK: Geometry
  val size = Vector2(0.0f, 0.0f).set(node.size)

  // MARK: Lifecycle
  init {
    debug(Tag.World, "$this created")
  }

  override fun children(sequence: EntitySequence): EntitySequence {
    return sequence.first(ship).then(systems)
  }

  override fun defineBody(node: SpaceNode): BodyDef {
    val body = super.defineBody(node)
    body.position.set(node.size * 0.5)
    return body
  }

  // MARK: Debugging
  override fun toString(): String {
    return "[space size=$size]"
  }
}
