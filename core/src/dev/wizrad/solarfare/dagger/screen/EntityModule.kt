package dev.wizrad.solarfare.dagger.screen

import dagger.Module
import dagger.Provides
import dev.wizrad.solarfare.config.Config
import dev.wizrad.solarfare.game.components.Textures
import dev.wizrad.solarfare.game.components.controls.Controls
import dev.wizrad.solarfare.game.components.session.Session
import dev.wizrad.solarfare.game.renderer.core.Camera
import dev.wizrad.solarfare.game.renderer.core.Renderer
import dev.wizrad.solarfare.game.ui.minimap.Minimap
import dev.wizrad.solarfare.game.world.EntityWorld
import dev.wizrad.solarfare.generation.SpaceNode
import dev.wizrad.solarfare.generation.core.Root

@Module
class EntityModule {
  // MARK: Simulation
  @Provides @ScreenScope
  fun world(
    root:     Root<SpaceNode>,
    session:  Session,
    controls: Controls,
    minimap:  Minimap): EntityWorld {

    return EntityWorld(root, session, controls, minimap)
  }

  // MARK: Rendering
  @Provides
  fun renderer(world: EntityWorld, camera: Camera, textures: Textures): Renderer {
    return Renderer(world, camera, textures)
  }

  @Provides
  fun camera(config: Config): Camera {
    return Camera(config)
  }
}
