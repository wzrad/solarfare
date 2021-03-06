package dev.wizrad.solarfare

import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.physics.box2d.Box2D
import dev.wizrad.solarfare.config.Config
import dev.wizrad.solarfare.dagger.game.DaggerGameComponent
import dev.wizrad.solarfare.dagger.game.GameComponent
import dev.wizrad.solarfare.dagger.game.GameModule
import dev.wizrad.solarfare.game.MainScreen
import dev.wizrad.solarfare.support.Tag
import dev.wizrad.solarfare.support.extensions.rand
import dev.wizrad.solarfare.support.info
import javax.inject.Inject

class SolarFare: Game() {
  // MARK: Object Graph
  private lateinit var component: GameComponent

  // MARK: Game
  override fun create() {
    Gdx.app.logLevel = Application.LOG_DEBUG

    // build component
    component = buildComponent()
    component.inject(this)

    // bootstrap box2d
    Box2D.init()

    // transition to initial screen
    setScreen(MainScreen(component))
  }

  // MARK: Injection
  @Inject
  fun inject(config: Config) {
    rand.seed = config.seed.get()
    info(Tag.General, "$this seed=${rand.seed} fixed?=${config.seed.isEnabled}")
  }

  private fun buildComponent(): GameComponent {
    return DaggerGameComponent.builder()
      .gameModule(GameModule())
      .build()
  }

  // MARK: Debugging
  override fun toString(): String {
    return "[solarfare]"
  }
}