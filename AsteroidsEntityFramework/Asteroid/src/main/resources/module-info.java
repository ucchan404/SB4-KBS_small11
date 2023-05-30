import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commonasteroid.IAsteroidSplitter;

module Asteroid {
    requires Common;
    requires CommonAsteroid;
    requires gdx;
    requires spring.context;
    provides IGamePluginService with dk.sdu.mmmi.cbse.asteroidsystem.AsteroidPlugin;
    provides IEntityProcessingService with dk.sdu.mmmi.cbse.asteroidsystem.AsteroidControlSystem;
    provides IAsteroidSplitter with dk.sdu.mmmi.cbse.asteroidsystem.AsteroidSplitterImpl;

}