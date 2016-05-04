package dev.wizrad.solarfare.dagger.screen

import dagger.Module
import dagger.Provides
import dev.wizrad.solarfare.config.Config
import dev.wizrad.solarfare.game.renderer.core.Camera
import dev.wizrad.solarfare.game.renderer.core.Renderer
import dev.wizrad.solarfare.game.ui.MainStage
import dev.wizrad.solarfare.game.ui.Minimap
import dev.wizrad.solarfare.game.world.World
import dev.wizrad.solarfare.game.world.core.NodeEntityFactory
import dev.wizrad.solarfare.generation.SpaceNode
import dev.wizrad.solarfare.generation.core.Root

@Module
class ScreenModule {
  @Provides @ScreenScope
  fun world(root: Root<SpaceNode>, factory: NodeEntityFactory): World {
    return World(root, factory)
  }

  @Provides @ScreenScope
  fun minimap(): Minimap {
    return Minimap()
  }

  @Provides
  fun factory(minimap: Minimap): NodeEntityFactory {
    return NodeEntityFactory(minimap)
  }

  @Provides
  fun stage(minimap: Minimap): MainStage {
    return MainStage(minimap)
  }

  @Provides
  fun renderer(world: World, camera: Camera): Renderer {
    return Renderer(world, camera)
  }

  @Provides
  fun camera(config: Config): Camera {
    return Camera(config)
  }
}
