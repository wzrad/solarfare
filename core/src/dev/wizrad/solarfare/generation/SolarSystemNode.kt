package dev.wizrad.solarfare.generation

import dev.wizrad.solarfare.config.Config
import dev.wizrad.solarfare.generation.clustering.Cluster
import dev.wizrad.solarfare.generation.clustering.ClusteringStrategy
import dev.wizrad.solarfare.generation.core.Node
import dev.wizrad.solarfare.generation.core.Spec
import dev.wizrad.solarfare.support.geometry.Point
import javax.inject.Inject
import javax.inject.Provider

class SolarSystemNode @Inject constructor(
  config: Config,
  private val stars:   Provider<StarNode>,
  private val planets: Provider<PlanetNode>,
  strategy: ClusteringStrategy): Node("system") {

  // MARK: Properties
  private val model   = config.solarSystem
  private val cluster = Cluster(strategy)

  /** The unit position of this node, relative to its parent */
  lateinit var center: Point
  /** The unit radius of the solar system */
  var radius: Double = 0.0

  // MARK: Lifecycle
  override fun willGenerate() {
    super.willGenerate()
    radius = model.radius.sample()
  }

  private fun generated(star: StarNode) {
    cluster.add(star)
  }

  private fun generated(planet: PlanetNode) {
    cluster.add(planet)
  }

  override fun didGenerate() {
    super.didGenerate()
    cluster.resolve()
  }

  // MARK: Spec
  override fun spec(): Spec.Builder {
    val spec = super.spec()

    spec.child { stars.get() }
      .require(1)
      .afterGenerate { generated(it) }

    spec.child { planets.get() }
      .weight(80).decay { it * 5 }
      .afterGenerate { generated(it) }

    return spec
  }
}
