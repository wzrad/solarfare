package dev.wizrad.solarfare.game.world.core

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import dev.wizrad.solarfare.game.core.Entity
import dev.wizrad.solarfare.game.ui.minimap.Mappable
import dev.wizrad.solarfare.game.ui.minimap.Minimap
import dev.wizrad.solarfare.game.ui.minimap.MinimapNode
import dev.wizrad.solarfare.game.world.World
import dev.wizrad.solarfare.generation.core.Node

abstract class NodeEntity<N: Node>(
  node:   N,
  parent: Entity?,
  val world: World): Entity(parent), Mappable {

  // MARK: Physics
  val body: Body

  // MARK: Entity
  override val name:   String = node.name
  override val center: Vector2 get() = body.position

  // MARK: Minimap
  private var minimapNode: MinimapNode? = null

  // MARK: Lifecycle
  init {
    body = world.physics.createBody(defineBody(node))
    createFixtures(node)
  }

  override fun destroy() {
    // stop tracking this node on the map when it's destroyed (for now)
    minimapNode?.destroy()
    minimapNode = null

    super.destroy()
  }

  // MARK: Physics
  open protected fun defineBody(node: N): BodyDef {
    return BodyDef()
  }

  open protected fun createFixtures(node: N) {
  }

  // MARK: Minimap
  protected fun trackOn(minimap: Minimap) {
    val node = minimap.track(this)
    configure(node)
    minimapNode = node
  }

  open protected fun configure(node: MinimapNode) {
  }
}
